package org.sa.rainbow.k8s.models.component;

import org.junit.Test;
import org.sa.rainbow.k8s.models.attributes.ResourceModelAttribute;
import org.sa.rainbow.k8s.models.attributes.CustomModelAttribute;
import org.sa.rainbow.k8s.models.attributes.ModelAttribute;
import org.sa.rainbow.k8s.models.attributes.ResourceType;

import static org.assertj.core.api.Assertions.*;
import static org.sa.rainbow.k8s.models.attributes.ResourceType.*;

public class DeploymentTest {

  @Test
  public void when_add_attr_cpu_then_create_new_CpuModelAttribute() {
    Deployment deployment = new Deployment("testing", "default", "app=testing");
    ModelAttribute attribute = new ResourceModelAttribute(CPU);
    deployment.addAttribute(attribute);

    assertThat(deployment.attributes()).hasSize(1);
    assertThat(deployment.attributes()).extractingResultOf("name").contains("cpu");
    assertThat(deployment.attributes().iterator().next().getClass()).isEqualTo(ResourceModelAttribute.class);
  }

  @Test
  public void when_add_attr_custom_then_get_resource_and_path_properties() {
    ModelAttribute attribute = new CustomModelAttribute(
            "fs_usage_bytes",
            "/apis/custom.metrics.k8s.io/v1beta1/namespaces/monitoring/pods/*/fs_usage_bytes");
    Deployment deployment = new Deployment("testing", "default", "app=testing");
    deployment.addAttribute(attribute);
    assertThat(deployment.attributes()).hasSize(1);
    var property = (CustomModelAttribute) deployment.attributes().iterator().next();
    assertThat(property.endpoint()).isEqualTo("/apis/custom.metrics.k8s.io/v1beta1/namespaces/monitoring/pods/*/fs_usage_bytes");
  }
}
