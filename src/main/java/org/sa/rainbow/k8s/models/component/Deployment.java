package org.sa.rainbow.k8s.models.component;

import org.sa.rainbow.k8s.models.attributes.ModelAttribute;
import org.sa.rainbow.k8s.models.attributes.SimpleModelAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.sa.rainbow.k8s.models.attributes.ModelAttributeType.BUILT_IN;

public class Deployment implements K8sComponent {

  private static Logger logger = LoggerFactory.getLogger(Deployment.class);
  private final String name;
  private final String namespace;
  private String selector;

  /**
   * A convenient structure to make queries easy [Name, Attribute]
   */
  private Map<String, ModelAttribute> attributes;

  public Deployment(String name, String namespace, String selector) {
    this.name = name;
    this.namespace = namespace;
    this.selector = selector;
    this.attributes = new HashMap<>();
    this.addAttribute(new SimpleModelAttribute("availableReplicas", BUILT_IN));
    this.addAttribute(new SimpleModelAttribute("currentReplicas", BUILT_IN));
    this.addAttribute(new SimpleModelAttribute("desiredReplicas", BUILT_IN));
  }

  public Collection<ModelAttribute> attributes() {
    return attributes.values();
  }

  public Optional<ModelAttribute> findAttribute(String name) {
    return ofNullable(attributes.get(name));
  }

  public void setAttributeValue(String name, Object value) {
    findAttribute(name).ifPresent(a -> a.setValue(value));
  }

  public void addAttribute(ModelAttribute attribute) {
    if (attribute != null) {
      attributes.put(attribute.name(), attribute);
    } else {
      logger.debug("Attribute of deployment {}.{} is null", getNamespace(), getName());
    }
  }

  public String getName() {
    return name;
  }

  public String getNamespace() {
    return namespace;
  }

  public String getSelector() {
    return selector;
  }
}
