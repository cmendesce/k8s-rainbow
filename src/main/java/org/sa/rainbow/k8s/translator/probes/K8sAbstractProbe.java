package org.sa.rainbow.k8s.translator.probes;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.kubernetes.client.ApiClient;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Base probe to get values from k8s API
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public abstract class K8sAbstractProbe extends AbstractRunnableJsonProbe {

  private static final Logger logger = getLogger(K8sAbstractProbe.class);
  private final ApiClient apiClient;
  private final KubernetesClient kubernetesClient;

  public K8sAbstractProbe(String id, String type, long sleepTime, String[] args, ApiClient apiClient) {
    super(id, type, sleepTime, args);
    this.apiClient = apiClient;
    kubernetesClient = new DefaultKubernetesClient();
  }

  KubernetesClient kubernetesClient() {
    return kubernetesClient;
  }

  ApiClient apiClient() {
    return apiClient;
  }
}
