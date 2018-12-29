package org.sa.rainbow.k8s.models.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sa.rainbow.core.error.RainbowException;
import org.sa.rainbow.k8s.models.K8sModelInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static java.lang.String.format;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class UpdateModelCommand {

  private static final Logger logger = LoggerFactory.getLogger(UpdateModelCommand.class);
  private final K8sModelInstance modelInstance;
  private final String deploymentName;
  private final ObjectMapper objectMapper;
  private final String params;

  public UpdateModelCommand(K8sModelInstance modelInstance, String deploymentName, String params) {
    this.modelInstance = modelInstance;
    this.deploymentName = deploymentName;
    this.params = params;
    this.objectMapper = new ObjectMapper();
  }

  protected void execute() throws RainbowException {
    try {
      var infoTree = objectMapper.readTree(params);
      var deployment = modelInstance.getModelInstance()
              .findDeployment(deploymentName)
              .orElseThrow(() -> new RainbowException(format("Deployment [%s] not found.", deploymentName)));

      while (infoTree.fieldNames().hasNext()) {
        var name = infoTree.fieldNames().next();
        deployment.setAttributeValue(name, infoTree.get(name));
      }
    } catch (IOException e) {
      var message = format("Cannot execute the command. %s", e.getMessage());
      logger.error(message);
      throw new RainbowException(message, e);
    }
  }
}
