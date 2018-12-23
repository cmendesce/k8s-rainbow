package org.sa.rainbow.k8s;

import io.fabric8.kubernetes.api.model.NamespaceList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class Fabric8 {

  public static void main(String[] args) {
    KubernetesClient client = new DefaultKubernetesClient();
    NamespaceList myNs = client.namespaces().list();




    client.apps().deployments().inNamespace("default").withName("podinfo").scale(5, true);

            //.withName("podinfo-65fc48f955").scale(5);
  }
}
