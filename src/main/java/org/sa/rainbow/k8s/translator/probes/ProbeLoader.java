package org.sa.rainbow.k8s.translator.probes;

import org.sa.rainbow.core.Rainbow;
import org.sa.rainbow.core.models.ModelReference;
import org.sa.rainbow.core.models.ProbeDescription;
import org.sa.rainbow.k8s.models.K8sDescription;
import org.sa.rainbow.k8s.models.attributes.ModelAttributeType;
import org.sa.rainbow.k8s.models.attributes.ResourceModelAttribute;
import org.sa.rainbow.k8s.models.component.Deployment;
import org.sa.rainbow.translator.probes.IProbe;
import org.sa.rainbow.translator.probes.IProbeLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static java.lang.String.format;

public class ProbeLoader implements IProbeLoader {

  private Logger logger = LoggerFactory.getLogger(ProbeLoader.class);
  private final ProbeDescription description;

  public ProbeLoader() {
    description = new ProbeDescription();
  }

  private K8sDescription getModelInstance() {
    return Rainbow.instance().getRainbowMaster().modelsManager().<K8sDescription>getModelInstance(new ModelReference("K8s", "K8s")).getModelInstance();
  }

  public void readFile() throws IOException {
    var model = getModelInstance();

    for (var deployment : model.deployments()) {
      description.probes.add(createProbeForDeployment(deployment));

      for (var attribute : deployment.attributes()) {

        if (attribute.type() == ModelAttributeType.RESOURCE) {
          if (attribute instanceof ResourceModelAttribute) {
            description.probes.add(createMetricProbeForDeployment(deployment, "cpu_usage", "cpu"));
            description.probes.add(createMetricProbeForDeployment(deployment, "memory_usage_bytes", "memory"));
          }
        }
      }
    }
  }

  ProbeDescription.ProbeAttributes createProbeForDeployment(Deployment deployment) {
    var probe = new ProbeDescription.ProbeAttributes();
    probe.name = format("deployments.%s.%s", deployment.getNamespace(), deployment.getName());
    probe.setLocation("127.0.0.1");
    probe.alias = probe.name;
    probe.setKindName("java");
    probe.kind = IProbe.Kind.JAVA;
    probe.putInfo("class", DeploymentInfoProbe.class.getCanonicalName());
    probe.putInfo("period", "5000");
    probe.putArray("args", new String[] {deployment.getNamespace(), deployment.getName(), deployment.getSelector()});
    return probe;
  }

  ProbeDescription.ProbeAttributes createMetricProbeForDeployment(Deployment deployment, String metricName, String metricAlias) {
    var probe = new ProbeDescription.ProbeAttributes();
    probe.name = format("deployments.%s.%s.%s", deployment.getNamespace(), deployment.getName(), metricAlias);
    probe.setLocation("127.0.0.1");
    probe.alias = probe.name;
    probe.setKindName("java");
    probe.kind = IProbe.Kind.JAVA;
    probe.putInfo("class", DeploymentCustomMetricProbe.class.getCanonicalName());
    probe.putInfo("period", "5000");
    probe.putArray("args", new String[] {deployment.getNamespace(), deployment.getName(), deployment.getSelector(), metricName, metricAlias});
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
