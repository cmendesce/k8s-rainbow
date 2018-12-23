package org.sa.rainbow.k8s;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class MetricItem {
  private String metricName;
  private String timestamp;
  private String value;
  private DescribedObject describedObject;

  public MetricItem(String metricName, String timestamp, String value, DescribedObject describedObject) {
    this.metricName = metricName;
    this.timestamp = timestamp;
    this.value = value;
    this.describedObject = describedObject;
  }

  public String getMetricName() {
    return metricName;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public String getValue() {
    return value;
  }

  public DescribedObject getDescribedObject() {
    return describedObject;
  }
}
