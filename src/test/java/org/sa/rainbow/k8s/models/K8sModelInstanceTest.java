package org.sa.rainbow.k8s.models;

import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class K8sModelInstanceTest {


  private FileInputStream inputStream;

  @Before
  public void before() throws FileNotFoundException {
    inputStream = new FileInputStream("/Users/carlosmendes/unifor/experiments/k8s-rainbow/src/main/resources/default/k8s-model.yaml");
  }

  @Test
  public void when_has_1_deployment_and_all_types_of_attributes_then_return_model_description() throws IOException {
    K8sModelInstance modelInstance = new K8sModelInstance("k8s", inputStream);
    modelInstance.readFile();
  }

}
