package org.sa.rainbow.k8s.models.command;

import org.sa.rainbow.core.error.RainbowModelException;
import org.sa.rainbow.core.models.IModelInstance;
import org.sa.rainbow.core.models.commands.AbstractSaveModelCmd;
import org.sa.rainbow.core.models.commands.IRainbowModelOperation;
import org.sa.rainbow.core.models.commands.ModelCommandFactory;
import org.sa.rainbow.k8s.models.K8sModelInstance;

public class K8sModelCommandFactory extends ModelCommandFactory {

  public K8sModelCommandFactory(IModelInstance model) {
    super(K8sModelInstance.class, model);
  }

  @Override
  public AbstractSaveModelCmd saveCommand(String location) throws RainbowModelException {
    return null;
  }

  @Override
  protected void fillInCommandMap() {
    m_commandMap.put(SetDeploymentInfoCommand.NAME.toLowerCase(), SetDeploymentInfoCommand.class);
  }

  @Override
  public IRainbowModelOperation generateCommand(String commandName, String... args) throws RainbowModelException {
    return super.generateCommand(commandName, args);
  }
}
