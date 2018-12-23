package org.sa.rainbow.k8s.models;

import org.sa.rainbow.k8s.models.attributes.ModelAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class Application {

  private static Logger logger = LoggerFactory.getLogger(Application.class);
  private Map<String, ModelAttribute> attributes;

  public Application() {
    attributes = new HashMap<>();
  }

  public void addAttribute(ModelAttribute attribute) {
    if (attribute != null) {
      attributes.put(attribute.name(), attribute);
    } else {
      logger.debug("Attribute named {} already exists", attribute.name());
    }
  }

  public Optional<ModelAttribute> findAttribute(String name) {
    return ofNullable(attributes.get(name));
  }
}
