package org.sa.rainbow.k8s.translator.gauges;

import org.sa.rainbow.core.error.RainbowException;
import org.sa.rainbow.core.gauges.AbstractGaugeWithProbes;
import org.sa.rainbow.core.models.commands.IRainbowOperation;
import org.sa.rainbow.core.util.TypedAttribute;
import org.sa.rainbow.core.util.TypedAttributeWithValue;

import java.util.List;
import java.util.Map;

public class DeploymentV1Gauge extends AbstractGaugeWithProbes {

  protected DeploymentV1Gauge(String threadName, String id, long beaconPeriod, TypedAttribute gaugeDesc, TypedAttribute modelDesc, List<TypedAttributeWithValue> setupParams, Map<String, IRainbowOperation> mappings) throws RainbowException {
    super(threadName, id, beaconPeriod, gaugeDesc, modelDesc, setupParams, mappings);
  }

}
