package org.sa.rainbow.k8s.models.component;

import org.sa.rainbow.k8s.models.attributes.CpuModelAttribute;
import org.sa.rainbow.k8s.models.attributes.K8SModelAttribute;
import org.sa.rainbow.k8s.models.attributes.ModelAttribute;
import org.sa.rainbow.k8s.models.attributes.ModelAttributeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Deployment implements K8sComponent {

  private static Logger logger = LoggerFactory.getLogger(Deployment.class);
  private String name;
  private Integer desiredReplicas;
  private Integer currentReplicas;
  private Integer availableReplicas;
  private Map<String, String> labels;

  private List<ModelAttribute> attributes;

  public List<ModelAttribute> attributes() {
    return attributes;
  }

  public Deployment(String name) {
    this.name = name;
    this.attributes = new ArrayList<>();
  }

  public boolean addAttributes(String name, Map<String, String> values) {
    var type = ModelAttributeType.from(values.get("type"));
    ModelAttribute attr = null;

    if (type == ModelAttributeType.K8S) {
      var resource = values.get("resource");
      var path = values.get("path");
      attr = new K8SModelAttribute(name,resource, path);
    } if (type == ModelAttributeType.CPU) {
      attr = new CpuModelAttribute(name);
    }
    if (attr != null) {
      return attributes.add(attr);
    } else {
      logger.warn("Impossible to construct the attribute names %s. ", name);
      return false;
    }
  }

  public Deployment availableReplicas(Integer availableReplicas) {
    this.availableReplicas = availableReplicas;
    return this;
  }

  public Deployment currentReplicas(Integer currentReplicas) {
    this.currentReplicas = currentReplicas;
    return this;
  }

  public Deployment labels(Map<String, String> labels) {
    this.labels = labels;
    return this;
  }

  public Deployment desiredReplicas(Integer desiredReplicas) {
    this.desiredReplicas = desiredReplicas;
    return this;
  }

  public Integer getAvailableReplicas() {
    return availableReplicas;
  }

  public Integer getCurrentReplicas() {
    return currentReplicas;
  }

  public Integer getDesiredReplicas() {
    return desiredReplicas;
  }

  public String getName() {
    return name;
  }

  public Map<String, String> getLabels() {
    return labels;
  }
}
