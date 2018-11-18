package org.sa.rainbow.k8s.adaptation;

import org.sa.rainbow.core.AbstractRainbowRunnable;
import org.sa.rainbow.core.RainbowComponentT;
import org.sa.rainbow.core.adaptation.AdaptationTree;
import org.sa.rainbow.core.adaptation.IAdaptationManager;
import org.sa.rainbow.core.adaptation.IEvaluable;
import org.sa.rainbow.core.error.RainbowConnectionException;
import org.sa.rainbow.core.models.ModelReference;
import org.sa.rainbow.core.ports.IModelChangeBusSubscriberPort;
import org.sa.rainbow.core.ports.IModelsManagerPort;
import org.sa.rainbow.core.ports.RainbowPortFactory;
import org.sa.rainbow.k8s.models.K8sDescription;
import org.sa.rainbow.k8s.models.K8sModelInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.sa.rainbow.core.RainbowComponentT.ADAPTATION_MANAGER;

/**
 * Represents the default adaptation manager for k8s models
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class DefaultAdaptationManager extends AbstractRainbowRunnable implements
        IAdaptationManager<IEvaluable> {

  private final Logger logger = LoggerFactory.getLogger(DefaultAdaptationManager.class);
  private IModelChangeBusSubscriberPort modelChangePort;
  private boolean enabled;
  private IModelsManagerPort modelsManagerPort = null;

  private K8sModelInstance k8sModelInstance;

  public DefaultAdaptationManager() throws RainbowConnectionException {
    super("K8s-Rainbow Adaptation Manager");
    initConnectors();
  }

  private void initConnectors() throws RainbowConnectionException {
    modelChangePort = RainbowPortFactory.createModelChangeBusSubscriptionPort();
    modelsManagerPort = RainbowPortFactory.createModelsManagerRequirerPort ();
  }

  @Override
  protected void runAction() {
    if (k8sModelInstance != null) {
    }
  }


  @Override
  public void setModelToManage(ModelReference model) {
    k8sModelInstance = (K8sModelInstance) modelsManagerPort.<K8sDescription>getModelInstance(model);
  }

  @Override
  public void markStrategyExecuted(AdaptationTree<IEvaluable> adaptationTree) {

  }

  @Override
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public RainbowComponentT getComponentType() {
    return ADAPTATION_MANAGER;
  }

  @Override
  protected void log(String s) {
    logger.info(s);
  }

  @Override
  public void dispose() {
    modelChangePort.dispose();
  }
}
