package org.sa.rainbow.k8s.models.command;

import org.sa.rainbow.core.error.RainbowException;
import org.sa.rainbow.core.event.IRainbowMessage;
import org.sa.rainbow.core.models.commands.AbstractRainbowModelOperation;
import org.sa.rainbow.core.ports.IRainbowMessageFactory;
import org.sa.rainbow.k8s.models.K8sDescription;
import org.sa.rainbow.k8s.models.K8sModelInstance;

import java.util.List;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class SetDeploymentCpuCommand extends AbstractRainbowModelOperation<String, K8sDescription> {

  private K8sModelInstance modelInstance;
  private String deploymentName;
  private String deploymentInfo;

  public SetDeploymentCpuCommand(K8sModelInstance model, String target, String deploymentInfo) {
    super("setDeploymentCpu", model, target, deploymentInfo);
    this.modelInstance = model;
    this.deploymentName = target;
    this.deploymentInfo = deploymentInfo;
  }

  @Override
  protected List<? extends IRainbowMessage> getGeneratedEvents(IRainbowMessageFactory iRainbowMessageFactory) {
    return List.of();
  }

  @Override
  protected void subExecute() throws RainbowException {
    new UpdateModelCommand(modelInstance, deploymentName, deploymentInfo).execute();
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
