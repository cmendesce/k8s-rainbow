package org.sa.rainbow.k8s.core;

import org.sa.rainbow.core.adaptation.AdaptationTree;
import org.sa.rainbow.core.adaptation.DefaultAdaptationExecutorVisitor;
import org.sa.rainbow.core.models.ModelReference;
import org.sa.rainbow.k8s.adaptation.history.ExecutionHistoryCommandFactory;
import org.sa.rainbow.k8s.adaptation.history.ExecutionHistoryData;
import org.sa.rainbow.k8s.adaptation.history.ExecutionHistoryModelInstance;

import java.util.concurrent.CountDownLatch;

/**
 * This class specializes an adaptation visitor to evaluate adaptations.
 *
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class StrategyExecutionVisitor extends DefaultAdaptationExecutorVisitor<Strategy> {

  private StrategyExecutor m_executor;
  private ModelReference m_modelRef;
  private ExecutionHistoryCommandFactory m_historyFactory;

  public StrategyExecutionVisitor(
      StrategyExecutor executor,
      ModelReference modelRef,
      ExecutionHistoryCommandFactory factory,
      AdaptationTree<Strategy> adt,
      ThreadGroup tg,
      CountDownLatch done) {
    super(adt, tg, modelRef + " Visitor", done, executor.getReportingPort());
    m_executor = executor;
    m_modelRef = modelRef;
    m_historyFactory = factory;
  }

  /**
   * Evaluate the stitchState strSttategy. This evaluation should store the success or otherwise in
   * a model of the strategy, and should mark it executed on the strategy model.
   */
  @Override
  protected boolean evaluate(Strategy adaptation) {
    // Clone the adaptation strategy so that we can run them in parallel
    try {
      synchronized (adaptation) {
        if (adaptation.isExecuting()) {

          if (adaptation.isExecuting()) {
            m_executor
                .getReportingPort()
                .error(m_executor.getComponentType(), "Could not execute " + adaptation.getName());
            return false;
          }
        }
        adaptation.markExecuting(true);
        adaptation.setExecutor(m_executor);
      }
      m_executor.log("Executing Strategy " + adaptation.getName() + "...");
      Strategy.Outcome o = null;

      m_executor
          .getHistoryModelUSPort()
          .updateModel(
              m_historyFactory.strategyExecutionStateCommand(
                  adaptation.getQualifiedName(),
                  ExecutionHistoryModelInstance.STRATEGY,
                  ExecutionHistoryData.ExecutionStateT.STARTED,
                  null));
      o = (Strategy.Outcome) adaptation.evaluate(null);
      m_executor.log(" - Outcome(" + adaptation.getName() + "): " + o);
      m_executor
          .getHistoryModelUSPort()
          .updateModel(
              m_historyFactory.strategyExecutionStateCommand(
                  adaptation.getQualifiedName(),
                  ExecutionHistoryModelInstance.STRATEGY,
                  ExecutionHistoryData.ExecutionStateT.FINISHED,
                  o.toString()));
      adaptation.setOutcome(o);
      return o == Strategy.Outcome.SUCCESS;
    } finally {
      adaptation.markExecuting(false);
    }
  }

  /** Return a new instance to manage the execution of an adaptation in a new thread. */
  @Override
  protected DefaultAdaptationExecutorVisitor spawnNewExecutorForTree(
      AdaptationTree adt, ThreadGroup g, CountDownLatch doneSignal) {
    return new StrategyExecutionVisitor(
        m_executor, m_modelRef, m_historyFactory, adt, g, doneSignal);
  }
}
