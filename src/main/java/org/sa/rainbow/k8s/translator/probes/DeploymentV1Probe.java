package org.sa.rainbow.k8s.translator.probes;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.models.V1Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;

import static io.kubernetes.client.util.Config.defaultClient;

public class DeploymentV1Probe extends K8sAbstractProbe {

  private static final Logger logger = LoggerFactory.getLogger(K8sProbe.class);
  private final AppsV1Api appsV1Api;
  private final String namespace;
  private final String deploymentName;

  public DeploymentV1Probe(String id, long sleepTime, String[] args) throws IOException {
    super(id, sleepTime, args, defaultClient());
    appsV1Api = new AppsV1Api(apiClient());
    namespace = args[0];
    deploymentName = args[1];
  }

  @Override
  protected void collect() throws ApiException, JsonProcessingException {
    var deployment = getDeployment();
    var data = new HashMap<String, Object>();
    data.put("replicas", deployment.getStatus().getReadyReplicas());
    data.put("labels", deployment.getMetadata().getLabels());
    reportData(data);
  }

  protected V1Deployment getDeployment() throws ApiException {
    return appsV1Api.readNamespacedDeployment(deploymentName, namespace, null, false, false);
  }
}
