package org.sa.rainbow.k8s.models.attributes;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public enum ResourceType {

  CPU,
  MEMORY,
  DISK,
  NETWORK;

  public static ResourceType from(String name) {
    try {
      return ResourceType.valueOf(name.toUpperCase());
    } catch (Exception ex) {
      return null;
    }
  }
}
