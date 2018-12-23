package org.sa.rainbow.k8s.models.parser;

import org.sa.rainbow.core.error.RainbowException;
import org.sa.rainbow.k8s.YamlParser;
import org.sa.rainbow.k8s.models.Application;
import org.sa.rainbow.k8s.models.K8sDescription;
import org.sa.rainbow.k8s.models.attributes.*;
import org.sa.rainbow.k8s.models.component.Deployment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

/**
 * @author Carlos Mendes (cmendesce@gmail.com)
 */
public class ModelParser {

  private final YamlParser yamlParser;

  public ModelParser() {
    this.yamlParser = new YamlParser();
  }

  public K8sDescription parse(InputStream stream) throws IOException {
    var description = new K8sDescription();
    var root = yamlParser.readValue(stream);

    parseDeployment(root).forEach(description::addDeployment);
    description.setApplication(parseApplication(root));

    return description;
  }

  Application parseApplication(Map<String, Map> root) {
    var properties = root.get("application");
    var attributes = (List<Map<String, String>>) properties.getOrDefault("attributes", emptyList());
    var app = new Application();
    attributes.stream().map(this::parseModelAttribute).forEach(app::addAttribute);
    return app;
  }

  Stream<Deployment> parseDeployment(Map<String, Map> root) {
    var deployments = (ArrayList<Map>) root.get("deployments");
    return deployments.stream().map(this::eachDeploymentParse);
  }

  Deployment eachDeploymentParse(Map<String, Object> properties) {
    var name = tryGet(properties,"name").toString();
    var namespace = tryGet(properties,"namespace").toString();
    var selector = tryGet(properties, "selector").toString();
    var deployment = new Deployment(name, namespace, selector);

    if (properties.containsKey("attributes")) {
      var value = properties.getOrDefault("attributes", List.of());
      var attributes = (List<Map<String, String>>) value;
      attributes.stream().map(this::parseModelAttribute).forEach(deployment::addAttribute);
    }

    return deployment;
  }

  ModelAttribute parseModelAttribute(Map<String, String> attribute) {
    var name = tryGet(attribute,"name");
    var type = ModelAttributeType.from(tryGet(attribute, "type"));
    ModelAttribute attr = null;

    if (type == ModelAttributeType.CUSTOM) {
      var endpoint = tryGet(attribute, "endpoint");
      attr = new CustomModelAttribute(name, endpoint);
    } if (type == ModelAttributeType.RESOURCE) {
      var resourceType = ResourceType.from(name);
      if (resourceType == null) {
        // TODO throw Exception
      }
      attr = new ResourceModelAttribute(resourceType);
    }
    return attr;
  }

  static <T> T tryGet(Map<String, T> source, String key) {
    var value = source.get(key);
    if (value == null) {
      throw new IllegalArgumentException("Property " + key + " not found");
    }
    return value;
  }
}
