package org.sa.rainbow.k8s.models;

import org.sa.rainbow.k8s.models.component.Deployment;

import java.util.*;

import static java.util.Optional.ofNullable;

public class K8sDescription {

  public static String MODEL_TYPE = "K8s";

  private Map<String, Deployment> deployments;
  private Application application;

  public K8sDescription() {
    deployments = new HashMap<>();
  }

  public void setApplication(Application application) {
    this.application = application;
  }

  public Application application() {
    return application;
  }

  public void addDeployment(Deployment deployment) {
    deployments.put(deployment.getName(), deployment);
  }

  public Optional<Deployment> findDeployment(String name) {
    return ofNullable(deployments.get(name));
  }

  public Collection<Deployment> deployments() {
    return Collections.unmodifiableCollection(deployments.values());
  }
}
