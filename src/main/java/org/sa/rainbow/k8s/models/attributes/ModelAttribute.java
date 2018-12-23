package org.sa.rainbow.k8s.models.attributes;

public interface ModelAttribute {

  String name();

  Object value();

  void setValue(Object value);

  ModelAttributeType type();
}
