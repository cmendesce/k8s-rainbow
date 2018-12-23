package org.sa.rainbow.k8s;

import java.util.List;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class MetricValueList {

  private String kind;
  private String apiVersion;
  private List<MetricItem> items;

  public MetricValueList(String kind, String apiVersion, List<MetricItem> items) {
    this.kind = kind;
    this.apiVersion = apiVersion;
    this.items = items;
  }

  public String getKind() {
    return kind;
  }

  public String getApiVersion() {
    return apiVersion;
  }

  public List<MetricItem> getItems() {
    return items;
  }




}
