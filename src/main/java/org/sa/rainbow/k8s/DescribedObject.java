package org.sa.rainbow.k8s;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class DescribedObject {
  private String kind;
  private String namespace;
  private String name;
  private String apiVersion;

  public DescribedObject(String kind, String namespace, String name, String apiVersion) {
    this.kind = kind;
    this.namespace = namespace;
    this.name = name;
    this.apiVersion = apiVersion;
  }

  public String getKind() {
    return kind;
  }

  public String getNamespace() {
    return namespace;
  }

  public String getName() {
    return name;
  }

  public String getApiVersion() {
    return apiVersion;
  }
}
