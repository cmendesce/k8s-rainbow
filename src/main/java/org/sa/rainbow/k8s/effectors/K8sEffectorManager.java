package org.sa.rainbow.k8s.effectors;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.models.V1Scale;
import io.kubernetes.client.models.V1ScaleSpec;
import org.sa.rainbow.core.models.commands.IRainbowOperation;
import org.sa.rainbow.translator.effectors.EffectorManager;
import org.sa.rainbow.translator.effectors.IEffectorExecutionPort;
import org.sa.rainbow.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.sa.rainbow.k8s.ApiClientFactory.defaultClient;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class K8sEffectorManager extends EffectorManager {

  private Logger logger = LoggerFactory.getLogger(K8sEffectorManager.class);

  private final ApiClient apiClient;

  private final CoreV1Api coreV1Api;
  private final AppsV1Api appsV1Api;

  public K8sEffectorManager() throws IOException {
    super("K8s Global Effector Manager");
    this.apiClient = defaultClient();
    coreV1Api = new CoreV1Api(apiClient);
    appsV1Api = new AppsV1Api((apiClient));
  }

  @Override
  public OperationResult publishOperation(IRainbowOperation operation) {
    var outcome = executeEffector("addReplica", "localhost", new String[] {});
    var result = new OperationResult();
    result.result = Result.SUCCESS;
    return result;
  }


  @Override
  public IEffectorExecutionPort.Outcome executeEffector(String effName, String target, String[] args) {



    KubernetesClient client = new DefaultKubernetesClient();
    var dp = client.apps().deployments().inNamespace("default").withName("podinfo").get();
    client.apps().deployments().inNamespace("default").withName("podinfo").scale(dp.getStatus().getReadyReplicas() + 3, true);

    return IEffectorExecutionPort.Outcome.SUCCESS;
  }
}
