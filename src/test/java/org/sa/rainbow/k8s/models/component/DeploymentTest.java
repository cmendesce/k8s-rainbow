package org.sa.rainbow.k8s.models.component;

import org.junit.Test;
import org.sa.rainbow.k8s.models.attributes.CpuModelAttribute;
import org.sa.rainbow.k8s.models.attributes.K8SModelAttribute;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

public class DeploymentTest {

  @Test
  public void when_add_attr_cpu_then_create_new_CpuModelAttribute() {
    var attrs = new HashMap<String, String>();
    attrs.put("type", "cpu");
    Deployment deployment = new Deployment("testing");
    deployment.addAttributes("computing", attrs);

    assertThat(deployment.attributes()).hasSize(1);
    assertThat(deployment.attributes()).extractingResultOf("name").contains("computing");
    assertThat(deployment.attributes().get(0).getClass()).isEqualTo(CpuModelAttribute.class);
  }

  @Test
  public void when_add_attr_custom_then_get_resource_and_path_properties() {
    var attrs = new HashMap<String, String>();
    attrs.put("type", "k8s");
    attrs.put("resource", "/apis/custom.metrics.k8s.io/v1beta1/namespaces/monitoring/pods/*/fs_usage_bytes");
    attrs.put("path", "$['items'][0]['value']");

    Deployment deployment = new Deployment("testing");
    deployment.addAttributes("fs_usage_bytes", attrs);

    assertThat(deployment.attributes()).hasSize(1);
    var property = (K8SModelAttribute) deployment.attributes().get(0);
    assertThat(property.path()).isEqualTo("$['items'][0]['value']");
    assertThat(property.resource()).isEqualTo("/apis/custom.metrics.k8s.io/v1beta1/namespaces/monitoring/pods/*/fs_usage_bytes");
  }
}
