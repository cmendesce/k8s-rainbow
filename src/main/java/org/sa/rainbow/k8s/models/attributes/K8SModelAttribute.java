package org.sa.rainbow.k8s.models.attributes;

public class K8SModelAttribute implements ModelAttribute {

  private final String name;
  private final String resource;
  private final String path;

  public K8SModelAttribute(String name, String resource, String path) {
    this.name = name;
    this.resource = resource;
    this.path = path;
  }

  public String resource() {
    return resource;
  }

  public String path() {
    return path;
  }

  @Override
  public String name() {
    return name;
  }
}
