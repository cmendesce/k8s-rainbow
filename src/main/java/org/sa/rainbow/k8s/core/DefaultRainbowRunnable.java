package org.sa.rainbow.k8s.core;

import org.sa.rainbow.core.AbstractRainbowRunnable;
import org.sa.rainbow.core.RainbowComponentT;
import org.sa.rainbow.core.ports.IRainbowReportingPort;

/** @author Carlos Mendes (cmendesce@gmail.com) */
public abstract class DefaultRainbowRunnable extends AbstractRainbowRunnable {

  private final RainbowComponentT componentType;

  public DefaultRainbowRunnable(String name, RainbowComponentT componentType) {
    super(name);
    this.componentType = componentType;
  }

  public IRainbowReportingPort getReportingPort() {
    return m_reportingPort;
  }

  @Override
  protected void log(String text) {
    m_reportingPort.info(getComponentType(), text);
  }

  @Override
  public RainbowComponentT getComponentType() {
    return componentType;
  }
}
