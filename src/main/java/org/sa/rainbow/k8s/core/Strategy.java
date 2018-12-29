package org.sa.rainbow.k8s.core;

import org.sa.rainbow.core.adaptation.IEvaluable;

/** @author Carlos Mendes (cmendesce@gmail.com) */
public class Strategy implements IEvaluable {

  private Outcome outcome;

  @Override
  public Object evaluate(Object[] objects) {
    return null;
  }

  @Override
  public long estimateAvgTimeCost() {
    return 0;
  }

  public boolean isExecuting() {
    return false;
  }

  public void markExecuting(boolean executing) {}

  public void setExecutor(StrategyExecutor executor) {}

  public String getName() {
    return "";
  }

  public void setOutcome(Outcome outcome) {
    this.outcome = outcome;
  }

  public String getQualifiedName() {
    return String.format("%s", getName());
  }

  /** Declares the states of results in which Strategy might be during evaluation */
  public enum Outcome {
    UNKNOWN,
    SUCCESS,
    FAILURE,
    STATUSQUO
  }
}
