package org.sa.rainbow.k8s.models.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Streams;
import org.sa.rainbow.core.error.RainbowException;
import org.sa.rainbow.core.event.IRainbowMessage;
import org.sa.rainbow.core.models.commands.AbstractRainbowModelOperation;
import org.sa.rainbow.core.ports.IRainbowMessageFactory;
import org.sa.rainbow.k8s.models.K8sDescription;
import org.sa.rainbow.k8s.models.K8sModelInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class SetDeploymentInfoCommand extends AbstractRainbowModelOperation<String, K8sDescription> {

  private static final Logger logger = LoggerFactory.getLogger(SetDeploymentInfoCommand.class);
  public static final String NAME = "setDeploymentInfo";

  private K8sModelInstance modelInstance;
  private String deploymentName;
  private String deploymentInfo;
  private ObjectMapper objectMapper;

  public SetDeploymentInfoCommand(K8sModelInstance model, String target, String deploymentInfo) {
    super(NAME, model, target, deploymentInfo);
    this.modelInstance = model;
    this.deploymentName = target;
    this.deploymentInfo = deploymentInfo;
    objectMapper = new ObjectMapper();
  }

  @Override
  protected List<? extends IRainbowMessage> getGeneratedEvents(IRainbowMessageFactory iRainbowMessageFactory) {
    return new ArrayList<>();
  }

  @Override
  protected void subExecute() throws RainbowException {
    try {
      var infoTree = objectMapper.readTree(deploymentInfo);
      var deployment = modelInstance.getModelInstance()
              .findDeployment(deploymentName)
              .orElseThrow(() -> new RainbowException(format("Deployment [%s] not found.", deploymentName)));

      while (infoTree.fieldNames().hasNext()) {
        var name = infoTree.fieldNames().next();
        deployment.setAttributeValue(name, infoTree.get(name));
      }
    } catch (IOException e) {
      var message = format("Cannot execute the %s command. %s", NAME, e.getMessage());
      logger.error(message);
      throw new RainbowException(message, e);
    }
  }

  @Override
  protected void subRedo() throws RainbowException {
  }

  @Override
  protected void subUndo() throws RainbowException {
  }

  @Override
  protected boolean checkModelValidForCommand(K8sDescription k8sDescription) {
    return true;
  }

  @Override
  public String getResult() throws IllegalStateException {
    return deploymentInfo;
  }
}
