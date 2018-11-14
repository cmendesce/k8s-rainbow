package org.sa.rainbow.k8s.translator.gauges;

import org.sa.rainbow.core.gauges.GaugeDescription;
import org.sa.rainbow.core.gauges.IGaugeLoader;

public class GaugeLoader implements IGaugeLoader {
  @Override
  public GaugeDescription load() {
    GaugeDescription description = new GaugeDescription();

    return description;
  }
}
