package org.sa.rainbow.k8s.core;

import org.sa.rainbow.core.Rainbow;
import org.sa.rainbow.core.adaptation.AdaptationTree;
import org.sa.rainbow.core.adaptation.IAdaptationExecutor;
import org.sa.rainbow.core.adaptation.IAdaptationManager;
import org.sa.rainbow.core.models.IModelInstance;
import org.sa.rainbow.core.models.ModelReference;
import org.sa.rainbow.core.ports.IModelDSBusPublisherPort;
import org.sa.rainbow.core.ports.IModelUSBusPort;
import org.sa.rainbow.core.ports.IRainbowAdaptationDequeuePort;
import org.sa.rainbow.core.ports.RainbowPortFactory;
import org.sa.rainbow.k8s.adaptation.history.ExecutionHistoryModelInstance;
import org.sa.rainbow.k8s.models.K8sDescription;
import org.sa.rainbow.k8s.models.K8sModelInstance;

import java.util.concurrent.CountDownLatch;

import static org.sa.rainbow.core.RainbowComponentT.EXECUTOR;

/** @author Carlos Mendes (cmendesce@gmail.com) */
public class StrategyExecutor extends DefaultRainbowRunnable
    implements IAdaptationExecutor<Strategy> {

  private ModelReference m_modelRef;

  private K8sModelInstance m_model;
  private IRainbowAdaptationDequeuePort<Strategy> m_adapationDQPort;
  private IModelUSBusPort m_modelUSBusPort;
  private IModelDSBusPublisherPort m_modelDSPort;
  private ThreadGroup m_executionThreadGroup;

  private ExecutionHistoryModelInstance m_historyModel;

  public StrategyExecutor() {
    super("Strategy Executor", EXECUTOR);
  }

  @Override
  protected void runAction() {
    if (!m_adapationDQPort.isEmpty()) {
      AdaptationTree<Strategy> at = m_adapationDQPort.dequeue();
      log("Dequeued an adaptation");

      final CountDownLatch done = new CountDownLatch(1);
      StrategyExecutionVisitor stitchVisitor =
          new StrategyExecutionVisitor(
              this,
              this.m_modelRef,
              m_historyModel.getCommandFactory(),
              at,
              m_executionThreadGroup,
              done);
      stitchVisitor.start();

      try {
        done.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      if (!Rainbow.instance().shouldTerminate()) {
        final IAdaptationManager<Strategy> adaptationManager =
            Rainbow.instance()
                .getRainbowMaster()
                .adaptationManagerForModel(this.m_modelRef.toString());
        if (adaptationManager != null) {
          adaptationManager.markStrategyExecuted(at);
        }
      }
    }
  }

  @Override
  public void setModelToManage(ModelReference model) {
    m_modelRef = model;
    IModelInstance<K8sDescription> mi =
        Rainbow.instance().getRainbowMaster().modelsManager().getModelInstance(model);
    m_adapationDQPort = RainbowPortFactory.createAdaptationDequeuePort(model);
    m_model = (K8sModelInstance) mi;
    if (m_model == null) {
      m_reportingPort.error(
          getComponentType(),
          "Referring to unknown model " + model.getModelName() + ":" + model.getModelType());
    }
    m_executionThreadGroup = new ThreadGroup(m_modelRef.toString() + " ThreadGroup");
  }

  @Override
  public IModelDSBusPublisherPort getOperationPublishingPort() {
    return null;
  }

  @Override
  public void dispose() {
    m_modelDSPort.dispose();
    m_reportingPort.dispose();
    if (m_adapationDQPort != null) {
      m_adapationDQPort.dispose();
    }
  }

  public IModelUSBusPort getHistoryModelUSPort() {
    return m_modelUSBusPort;
  }

  public void setHistoryModelUSPort(IModelUSBusPort modelUSPort) {
    this.m_modelUSBusPort = modelUSPort;
  }
}
