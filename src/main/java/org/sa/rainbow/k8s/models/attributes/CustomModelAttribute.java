package org.sa.rainbow.k8s.models.attributes;

import static org.sa.rainbow.k8s.models.attributes.ModelAttributeType.*;

public class CustomModelAttribute extends SimpleModelAttribute {

  private final String endpoint;

  public CustomModelAttribute(String name, String endpoint) {
    super(name, CUSTOM);
    this.endpoint = endpoint;
  }

  public String endpoint() {
    return endpoint;
  }
}
