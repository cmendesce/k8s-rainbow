package org.sa.rainbow.k8s.translator.probes;

import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsV1beta2Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.apis.ExtensionsApi;
import io.kubernetes.client.models.V1beta2Deployment;
import org.sa.rainbow.translator.probes.AbstractRunnableProbe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static io.kubernetes.client.util.Config.defaultClient;

public class K8sProbe extends AbstractRunnableProbe {

  private static final Logger logger = LoggerFactory.getLogger(K8sProbe.class);
  private static final String PROBE_TYPE = "loadCpu";
  private final CoreV1Api coreV1Api;
  private final String resource;
  private final String path;

  public K8sProbe(String id, long sleepTime, String[] args) throws IOException, ApiException {
    super (id, PROBE_TYPE, Kind.JAVA, sleepTime);
    coreV1Api = new CoreV1Api(defaultClient());
    this.resource = args[0];
    this.path = args[1];
    AppsV1beta2Api api = new AppsV1beta2Api(defaultClient());

    V1beta2Deployment deployment = api.readNamespacedDeployment("", "", "", true, true);
    ExtensionsApi api1 = new ExtensionsApi(defaultClient());

  }

  @Override
  public void run() {
    Thread currentThread = Thread.currentThread();
    while (thread() == currentThread && isActive()) {
      logger.info("Resource %s || Path %s ", resource, path);
      sleep();
    }
  }

  private void sleep() {
    try {
      Thread.sleep(sleepTime());
    } catch (InterruptedException e) {
      // intentional ignore
    }
  }
}
