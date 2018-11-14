package org.sa.rainbow.k8s.translator.gauges;

import org.json.JSONObject;
import org.sa.rainbow.core.error.RainbowException;
import org.sa.rainbow.core.gauges.AbstractGaugeWithProbes;
import org.sa.rainbow.core.models.commands.IRainbowOperation;
import org.sa.rainbow.core.util.TypedAttribute;
import org.sa.rainbow.core.util.TypedAttributeWithValue;
import org.sa.rainbow.translator.probes.IProbeIdentifier;

import java.util.*;

public class PrometheusGauge extends AbstractGaugeWithProbes {

  public static final String NAME = "G - Prometheus Gauge";
  private final Queue<JSONObject> messages;

  public PrometheusGauge(String id, long beaconPeriod, TypedAttribute gaugeDesc, TypedAttribute modelDesc,
                         List<TypedAttributeWithValue> setupParams, Map<String, IRainbowOperation> mappings)
    throws RainbowException {
    super(NAME, id, beaconPeriod, gaugeDesc, modelDesc, setupParams, mappings);
    messages = new LinkedList<>();
  }

  @Override
  protected void runAction() {
    int maxUpdates = MAX_UPDATES_PER_SLEEP;
    while (messages.size() > 0 && maxUpdates-- > 0) {
      JSONObject item = messages.poll();
      String metric = item.getString("metric");
      String value = item.getString("value");

      IRainbowOperation cmd = getCommand("load");
      Map<String, String> pMap = new HashMap<>();
      pMap.put(cmd.getParameters ()[0], value);
      issueCommand(cmd, pMap);
    }
    super.runAction();
  }

  @Override
  public void reportFromProbe(IProbeIdentifier probe, String data) {
    JSONObject item = new JSONObject(data);
    String metric = item.getString("metric");
    String value = item.getString("value");

    IRainbowOperation cmd = getCommand("load");
    Map<String, String> pMap = new HashMap<>();
    pMap.put(cmd.getParameters ()[0], value);
    issueCommand(cmd, pMap);
  }
}
