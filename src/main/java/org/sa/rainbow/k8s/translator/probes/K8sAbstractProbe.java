package org.sa.rainbow.k8s.translator.probes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import org.sa.rainbow.translator.probes.AbstractRunnableProbe;
import org.slf4j.Logger;

import java.util.Map;

import static org.slf4j.LoggerFactory.*;

public abstract class K8sAbstractProbe extends AbstractRunnableProbe {

  private static final Logger logger = getLogger(K8sAbstractProbe.class);
  private final ApiClient apiClient;
  private final ObjectMapper mapper;

  public K8sAbstractProbe(String id, long sleepTime, String[] args, ApiClient apiClient) {
    super(id, "k8s-probe", Kind.JAVA, sleepTime);
    this.apiClient = apiClient;
    mapper = new ObjectMapper();
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

  protected void reportData(Map<String, Object> data) {
    try {
      reportData(mapper.writeValueAsString(data));
    } catch (JsonProcessingException e) {
      logger.error("Can't send data from probe to gauge. Reason: %s", e.getMessage(), e);
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
