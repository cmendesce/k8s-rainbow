package org.sa.rainbow.k8s.models.command;

import org.sa.rainbow.core.error.RainbowModelException;
import org.sa.rainbow.core.models.IModelInstance;
import org.sa.rainbow.core.models.commands.AbstractRainbowModelOperation;
import org.sa.rainbow.core.models.commands.AbstractSaveModelCmd;
import org.sa.rainbow.core.models.commands.IRainbowModelOperation;
import org.sa.rainbow.core.models.commands.ModelCommandFactory;
import org.sa.rainbow.k8s.models.K8sModelInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class K8sModelCommandFactory extends ModelCommandFactory {

  private Logger logger = LoggerFactory.getLogger(K8sModelCommandFactory.class);
  private K8sModelInstance modelInstance;

  public K8sModelCommandFactory(IModelInstance model) {
    super(K8sModelInstance.class, model);
    modelInstance = (K8sModelInstance) model;
  }

  @Override
  public AbstractSaveModelCmd saveCommand(String location) throws RainbowModelException {
    return null;
  }

  @Override
  protected void fillInCommandMap() {
//    m_commandMap.put(SetDeploymentInfoCommand.NAME.toLowerCase(), SetDeploymentInfoCommand.class);
  }

  @Override
  public IRainbowModelOperation generateCommand(String commandName, String... args) throws RainbowModelException {
    if (commandName.startsWith("setDeployment")) {
      logger.debug("Creating command for {} command", commandName);
      return new SetDeploymentInfoCommand(modelInstance, args[0], args[1]);
    }

    return super.generateCommand(commandName, args);
  }
}
