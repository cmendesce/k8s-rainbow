package org.sa.rainbow.k8s.models;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class K8sModelInstanceTest {

  private FileInputStream inputStream;

  @Before
  public void before() throws FileNotFoundException {
    inputStream = new FileInputStream(getClass().getClassLoader().getResource("k8s-model-test-parser.yaml").getFile());
  }

  @Test
  public void when_instantiate_then_parse_the_file() throws IOException {
    var modelInstance = new K8sModelInstance("k8s", inputStream);
    assertThat(modelInstance.getModelInstance().deployments()).hasSize(1);
    var deployment = modelInstance.getModelInstance().deployments().iterator().next();
    assertThat(deployment.getName()).isEqualTo("podinfo");
    assertThat(deployment.getNamespace()).isEqualTo("default");
    assertThat(deployment.getSelector()).isEqualTo("app=podinfo");
  }

}
