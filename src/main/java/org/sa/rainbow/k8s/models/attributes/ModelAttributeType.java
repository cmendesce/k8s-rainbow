package org.sa.rainbow.k8s.models.attributes;

public enum  ModelAttributeType {
  BUILT_IN,
  CUSTOM,
  RESOURCE;

  public static ModelAttributeType from(String key) {
    try {
      return ModelAttributeType.valueOf(key.toUpperCase());
    } catch (Exception ex) {
      return null;
    }
  }
}
