package org.sa.rainbow.k8s.translator.probes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sa.rainbow.translator.probes.AbstractRunnableProbe;
import org.slf4j.Logger;

import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * A probe that produces its data in json format
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public abstract class AbstractRunnableJsonProbe  extends AbstractRunnableProbe {

  private static final Logger logger = getLogger(K8sAbstractProbe.class);
  private final ObjectMapper mapper;

  public AbstractRunnableJsonProbe(String id, String type, long sleepTime, String[] args) {
    super(id, type, Kind.JAVA, sleepTime);
    mapper = new ObjectMapper();
  }

  @Override
  public void run() {
    Thread currentThread = Thread.currentThread();
    while (thread() == currentThread && isActive()) {
      var data = collect();
      reportData(data);
      sleep();
    }
  }

  protected ObjectMapper getMapper() {
    return mapper;
  }

  /**
   * Reports the data in json format.
   * @param data
   */
  protected void reportData(Map<String, Object> data) {
    try {
      reportData(getMapper().writeValueAsString(data));
    } catch (JsonProcessingException e) {
      logger.error("Probe [%s] can't send data. Reason: %s", this.name(), e.getMessage(), e);
    }
  }

  /**
   * Collects the data to be reported
   * @return The data collected
   */
  protected abstract Map<String, Object> collect();

  private void sleep() {
    try {
      Thread.sleep(sleepTime());
    } catch (InterruptedException e) {
      // intentional ignore
    }
  }
}
