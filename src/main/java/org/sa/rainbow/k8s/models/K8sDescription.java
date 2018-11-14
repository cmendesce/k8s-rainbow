package org.sa.rainbow.k8s.models;

import org.sa.rainbow.k8s.models.component.Deployment;

import java.util.ArrayList;
import java.util.List;

public class K8sDescription {

  public List<Deployment> deployments;

  public K8sDescription() {
    deployments = new ArrayList<>();
  }

  public boolean addDeployment(Deployment deployment) {
    return deployments.add(deployment);
  }
}
