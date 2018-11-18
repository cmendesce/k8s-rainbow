package org.sa.rainbow.k8s.translator.gauges;

import org.sa.rainbow.core.error.RainbowException;
import org.sa.rainbow.core.models.commands.IRainbowOperation;
import org.sa.rainbow.core.util.TypedAttribute;
import org.sa.rainbow.core.util.TypedAttributeWithValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract each attribute as a command offered for the k8s model
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class DeploymentV1Gauge extends AbstractJsonGaugeWithProbes {

  public static final String NAME = "G - Deployment V1 Gauge";

  public DeploymentV1Gauge(String id, long beaconPeriod, TypedAttribute gaugeDesc, TypedAttribute modelDesc,
                         List<TypedAttributeWithValue> setupParams, Map<String, IRainbowOperation> mappings)
          throws RainbowException {
    super(NAME, id, beaconPeriod, gaugeDesc, modelDesc, setupParams, mappings);
  }

  @Override
  protected void runAction() {
    int maxUpdates = MAX_UPDATES_PER_SLEEP;
    while (messages().size() > 0 && maxUpdates-- > 0) {
      var item = messages().poll();

      var cmd = getCommand("setDeploymentInfo");
      Map<String, String> params = new HashMap<>();
      params.put(cmd.getParameters()[0], item.toString());
      issueCommand(cmd, params);
    }
    super.runAction();
  }
}