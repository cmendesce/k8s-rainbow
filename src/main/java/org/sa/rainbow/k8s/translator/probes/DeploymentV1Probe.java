package org.sa.rainbow.k8s.translator.probes;

import com.google.common.collect.ImmutableMap;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.models.V1Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static io.kubernetes.client.util.Config.defaultClient;
import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * A probe to collect data from a deployment and report as json
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class DeploymentV1Probe extends K8sAbstractProbe {

  private static final Logger logger = LoggerFactory.getLogger(DeploymentV1Probe.class);
  private final AppsV1Api appsV1Api;
  private final String namespace;
  private final String deploymentName;

  public DeploymentV1Probe(String id, long sleepTime, String[] args) throws IOException {
    super(id, "deployment-probe", sleepTime, args, defaultClient());
    appsV1Api = new AppsV1Api(apiClient());
    namespace = args[0];
    deploymentName = args[1];
  }

  @Override
  protected Map<String, Object> collect() {
    var deployment = getDeployment();
    if (deployment.isPresent()) {
      var d = deployment.get();
      return ImmutableMap.of(
              "replicas", d.getStatus().getReadyReplicas(),
              "labels", d.getMetadata().getLabels());
    }

    return ImmutableMap.of();
  }

  protected Optional<V1Deployment> getDeployment() {
    try {
      return of(appsV1Api.readNamespacedDeployment(deploymentName, namespace, null, false, false));
    } catch (ApiException ex) {
      return empty();
    }
  }
}
