package org.sa.rainbow.k8s.translator.probes;

import org.sa.rainbow.core.models.ProbeDescription;
import org.sa.rainbow.k8s.YamlReader;
import org.sa.rainbow.k8s.models.component.Deployment;
import org.sa.rainbow.translator.probes.IProbe;
import org.sa.rainbow.translator.probes.IProbeLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.lang.String.*;
import static org.sa.rainbow.k8s.models.attributes.ModelAttributeType.*;

public class ProbeLoader implements IProbeLoader {

  private Logger logger = LoggerFactory.getLogger(ProbeLoader.class);
  private final YamlReader yamlReader;
  private final ProbeDescription description;

  public ProbeLoader() {
    yamlReader = new YamlReader();
    description = new ProbeDescription();
  }

  public void readFile() throws IOException {
    InputStream fileInput = new FileInputStream("/Users/carlosmendes/unifor/experiments/k8s-rainbow/src/main/resources/default/k8s-model.yaml");
    Map<String, Map> root = yamlReader.readValue(fileInput);

    Map<String, Map> deployments = root.get("deployments");

    for (String deploymentName : deployments.keySet()) {
      description.probes.add(createProbeForDeployment(deploymentName, "default"));


//      Map<String, Map> deployment = deployments.get(deploymentName);
//      Map<String, Map> attributes = deployment.get("attributes");
//
//
//      for (String attrName : attributes.keySet()) {
//        var props = attributes.get(attrName);
//        var type = from(props.get("type").toString());
//        if (type == K8S) {
//          // resource, path
//          var resource = props.get("resource").toString();
//          var path = props.get("path").toString();
//          description.probes.add(createProbe(deploymentName, attrName, resource, path));
//        } else if (type == CPU) {
//          // criar probe de cpu
//        } else if (type == MEMORY) {
//          // criar probe de memory
//        } else {
//
//        }
//      }
      // cada attributo é uma probe
    }
  }

  ProbeDescription.ProbeAttributes createProbe(String parent, String name, String... params) {
    var probe = new ProbeDescription.ProbeAttributes();
    probe.name = format("k8s-%s-%s", parent, name);
    probe.setLocation ("127.0.0.1");
    probe.alias = probe.name;
    probe.setKindName("java");
    probe.kind = IProbe.Kind.JAVA;
    probe.putInfo("class", K8sProbe.class.getCanonicalName());
    probe.putInfo("period", "5000");
    probe.putArray("args", params);
    return probe;
  }

  ProbeDescription.ProbeAttributes createProbeForDeployment(String name, String namespace) {
    var probe = new ProbeDescription.ProbeAttributes();
    probe.name = format("k8s-deployment-%s.%s", namespace, name);
    probe.setLocation ("127.0.0.1");
    probe.alias = probe.name;
    probe.setKindName("java");
    probe.kind = IProbe.Kind.JAVA;
    probe.putInfo("class", DeploymentV1Probe.class.getCanonicalName());
    probe.putInfo("period", "5000");
    probe.putArray("args", new String[] {namespace, name});
    return probe;
  }

  @Override
  public ProbeDescription load() {
    try {
      readFile();
      return description;
    } catch (IOException e) {
      logger.error("Was not possible to load probes. Reason: %s", e.getMessage(), e);
      return description;
    }
  }
}
