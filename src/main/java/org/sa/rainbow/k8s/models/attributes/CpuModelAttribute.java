package org.sa.rainbow.k8s.models.attributes;

public class CpuModelAttribute implements ModelAttribute {
  private final String name;

  public CpuModelAttribute(String name) {
    this.name = name;
  }

  @Override
  public String name() {
    return name;
  }
}
