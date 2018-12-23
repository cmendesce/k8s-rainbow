package org.sa.rainbow.k8s.models.attributes;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class SimpleModelAttribute implements ModelAttribute {

  private final String name;
  private final ModelAttributeType type;
  private Object value;

  public SimpleModelAttribute(String name, ModelAttributeType type) {
    this.name = name;
    this.type = type;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public Object value() {
    return value;
  }

  @Override
  public void setValue(Object value) {
    this.value = value;
  }

  @Override
  public ModelAttributeType type() {
    return type;
  }
}
