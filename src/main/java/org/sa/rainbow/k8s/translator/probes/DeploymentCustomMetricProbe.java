package org.sa.rainbow.k8s.translator.probes;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import com.squareup.okhttp.Request;
import io.kubernetes.client.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.collect.Streams.stream;
import static java.lang.String.format;
import static java.lang.String.join;
import static org.sa.rainbow.k8s.ApiClientFactory.defaultClient;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class DeploymentCustomMetricProbe extends K8sAbstractProbe {
  private static final Logger logger = LoggerFactory.getLogger(DeploymentCustomMetricProbe.class);
  private final String metricName;
  private final String metricAlias;
  private final String namespace;
  private final String deploymentName;
  private final String selector;

  public DeploymentCustomMetricProbe(String id, long sleepTime, String[] args) {
    super(id, "deployment-" + args[4] + "-probe", sleepTime, args, defaultClient());
    namespace = args[0];
    deploymentName = args[1];
    selector = args[2];
    metricName = args[3];
    metricAlias = args[4];
    logger.debug("Created probe or metric {} for deployments.{}.{} with selector {}", metricAlias, namespace, deploymentName, selector);
  }

  @Override
  protected Map<String, Object> collect() {
    try {
      var request = buildGetRequest(buildUrl(selector));
      var response = apiClient().getHttpClient().newCall(request).execute();

      if (response.isSuccessful()) {
        var elements = getMapper().readTree(response.body().string()).get("items").elements();
        var avg = stream(elements).mapToDouble(this::getValue).average();

        if (avg.isPresent()) {
          return ImmutableMap.of(
                  metricAlias, avg.getAsDouble(),
                  "namespace", namespace,
                  "name", deploymentName
          );
        }
      }
    } catch (IOException e) {
      logger.info("An error occurred when try to collect data from {}.{} deployment. {}", namespace, deploymentName, e.getMessage());
    }

    return ImmutableMap.of();
  }

  Double getValue(JsonNode node) {
    return Double.parseDouble(node.get("value").asText().replace("m", ""));
  }

  String buildUrl(String... selectors) {
    String urlTemplate = "/apis/custom.metrics.k8s.io/v1beta1/namespaces/%s/pods/*/%s?selector=%s";
    return format(urlTemplate, namespace, metricName, join("&", selectors));
  }

  Request buildGetRequest(String path) {
    try {
      return apiClient().buildRequest(path, "GET", new ArrayList<>(), new ArrayList<>(), null, new HashMap<>(), new HashMap<>(), new String[]{}, null);
    } catch (ApiException e) {
      e.printStackTrace();
      return null;
    }
  }
}
