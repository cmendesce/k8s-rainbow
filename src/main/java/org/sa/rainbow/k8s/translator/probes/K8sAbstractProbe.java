package org.sa.rainbow.k8s.translator.probes;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import org.sa.rainbow.translator.probes.AbstractRunnableProbe;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.*;

public abstract class K8sAbstractProbe extends AbstractRunnableProbe {

  private static final Logger logger = getLogger(K8sAbstractProbe.class);
  private final ApiClient apiClient;

  public K8sAbstractProbe(String id, long sleepTime, String[] args, ApiClient apiClient) {
    super(id, "k8s-probe", Kind.JAVA, sleepTime);
    this.apiClient = apiClient;
  }

  protected ApiClient apiClient() {
    return apiClient;
  }

  @Override
  public void run() {
    Thread currentThread = Thread.currentThread();
    while (thread() == currentThread && isActive()) {
      try {
        this.collect();
        sleep();
      } catch (ApiException ex) {
        logger.warn("cant get data from ....");
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }
  }

  protected abstract void collect() throws ApiException, JsonProcessingException;

  private void sleep() {
    try {
      Thread.sleep(sleepTime());
    } catch (InterruptedException e) {
      // intentional ignore
    }
  }
}
