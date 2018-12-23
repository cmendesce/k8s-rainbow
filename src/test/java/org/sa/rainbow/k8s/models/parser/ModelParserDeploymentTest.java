package org.sa.rainbow.k8s.models.parser;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.sa.rainbow.k8s.models.attributes.CustomModelAttribute;
import org.sa.rainbow.k8s.models.attributes.ModelAttributeType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.sa.rainbow.k8s.models.attributes.ModelAttributeType.*;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class ModelParserDeploymentTest {

  private ModelParser modelParser;

  @Before
  public void setup() {
    modelParser = new ModelParser();
  }

  @Test
  public void when_parse_simple_then_create_object_successfully() {
    var deploymentInfo = simple();
    var deployment = modelParser.eachDeploymentParse(deploymentInfo);
    assertThat(deployment.getName()).isEqualTo("podname");
    assertThat(deployment.getNamespace()).isEqualTo("default");
  }

  @Test
  public void when_parse_with_cpu_attribute_then_create_object_successfully() {
    var deploymentInfo = simple();
    var attribute = ImmutableMap.of("name", "cpu", "type", "resource");
    deploymentInfo.put("attributes", asList(attribute));

    var deployment = modelParser.eachDeploymentParse(deploymentInfo);
    assertThat(deployment.attributes()).hasSize(1);
    assertThat(deployment.attributes().iterator().next().type().equals(RESOURCE));
  }

  @Test
  public void when_parse_with_k8s_attribute_then_create_object_successfully() {
    var attribute = ImmutableMap.of(
            "name", "responseTime",
            "type", "custom",
            "endpoint", "/apis/custom.metrics.k8s.io/v1beta1/namespaces/monitoring/pods/*/fs_usage_bytes");
    var deploymentInfo = simple();
    deploymentInfo.put("attributes", asList(attribute));
    var deployment = modelParser.eachDeploymentParse(deploymentInfo);
    var attr = (CustomModelAttribute) deployment.attributes().iterator().next();

    assertThat(deployment.attributes()).hasSize(1);
    assertThat(attr.type()).isEqualTo(CUSTOM);
    assertThat(attr.name()).isEqualTo("responseTime");
    assertThat(attr.endpoint()).isEqualTo("/apis/custom.metrics.k8s.io/v1beta1/namespaces/monitoring/pods/*/fs_usage_bytes");
  }

  static List<Map<String, String>> asList(Map<String, String>... attributes) {
    var list = new ArrayList<Map<String, String>>();
    stream(attributes).forEach(list::add);
    return list;
  }

  static Map<String, Object> simple() {
    var map = new HashMap<String, Object>();
    map.put("name", "podname");
    map.put("namespace", "default");
    map.put("selector", "app=podname");
    return map;
  }
}
