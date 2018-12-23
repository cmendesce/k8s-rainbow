package org.sa.rainbow.k8s.translator.probes;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class DeploymentCpuProbeTest {

  private DeploymentCustomMetricProbe deploymentCpuProbe;

  @Before
  public void setup() {
    System.setProperty("rainbow.config", "/Users/carlosmendes/unifor/experiments/k8s-rainbow/src/main/resources");
    System.setProperty("rainbow.target", "default");
    deploymentCpuProbe = new DeploymentCustomMetricProbe("id", 2000, new String[]{});
  }

  @Test
  public void when_buildUrl_with_1_selector_then_include_query_string() {
    var actual = deploymentCpuProbe.buildUrl("default","app=podinfo");
    assertThat(actual).endsWith("selector=app=podinfo");
  }

  @Test
  public void when_buildUrl_with_2_selector_then_include_query_string() {
    var actual = deploymentCpuProbe.buildUrl("default", "app=podinfo&app1=podinfo_db");
    assertThat(actual).endsWith("selector=app=podinfo&app1=podinfo_db");
  }
}
