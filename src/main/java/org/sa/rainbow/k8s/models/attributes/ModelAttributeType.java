package org.sa.rainbow.k8s.models.attributes;

public enum  ModelAttributeType {
  CPU,
  K8S, MEMORY;

  public static ModelAttributeType from(String key) {
    try {
      return ModelAttributeType.valueOf(key.toUpperCase());
    } catch (Exception ex) {
      return null;
    }
  }
}
