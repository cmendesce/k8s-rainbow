package org.sa.rainbow.k8s.translator.probes;

import org.sa.rainbow.k8s.PrometheusClient;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.sa.rainbow.translator.probes.AbstractRunnableProbe;
import org.sa.rainbow.util.Util;

public class PrometheusProbe extends AbstractRunnableProbe {

  private static final String PROBE_TYPE = "loadCpu";
  private final PrometheusClient prometheusClient;
  private final String promql;

  public PrometheusProbe(String id, long sleepTime, String[] args) {
    super (id, PROBE_TYPE, Kind.JAVA, sleepTime);
    LOGGER = Logger.getLogger(PrometheusProbe.class);

    prometheusClient = new PrometheusClient(args[0]);
    promql = args[1];
  }

  public void run() {
    Thread currentThread = Thread.currentThread();
    while (thread() == currentThread && isActive()) {
      sleep();
      JSONObject result = prometheusClient.query(promql);
      JSONObject firstMetric = result.getJSONObject("data").getJSONArray("result").getJSONObject(0);
      JSONObject report = new JSONObject();
      report.put("metric", firstMetric.getJSONObject("metric").get("__name__"));
      report.put("value", firstMetric.getJSONArray("value").get(1));
      reportData(report.toString());
      Util.dataLogger().info(report.toString());
    }
  }

  private void sleep() {
    try {
      Thread.sleep(sleepTime());
    } catch (InterruptedException e) {
      // intentional ignore
    }
  }
}
