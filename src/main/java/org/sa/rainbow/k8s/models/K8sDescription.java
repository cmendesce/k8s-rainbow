package org.sa.rainbow.k8s.models;

import org.sa.rainbow.k8s.models.component.Deployment;

import java.util.*;

import static java.util.Optional.*;

public class K8sDescription {

  public Map<String, Deployment> deployments;

  public K8sDescription() {
    deployments = new HashMap<>();
  }

  public void addDeployment(Deployment deployment) {
    deployments.put(deployment.getName(), deployment);
  }

  public Optional<Deployment> findDeployment(String name) {
    return ofNullable(deployments.get(name));
  }
}
