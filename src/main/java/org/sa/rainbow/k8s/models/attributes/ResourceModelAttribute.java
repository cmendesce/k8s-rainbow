package org.sa.rainbow.k8s.models.attributes;

import static org.sa.rainbow.k8s.models.attributes.ModelAttributeType.RESOURCE;

public class ResourceModelAttribute extends SimpleModelAttribute {

  private final ResourceType type;

  public ResourceModelAttribute(ResourceType type) {
    super(type.name(), RESOURCE);
    this.type = type;
  }

  public ResourceType getType() {
    return type;
  }
}
