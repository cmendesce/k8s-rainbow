package org.sa.rainbow.k8s.models;

import org.sa.rainbow.core.error.RainbowException;
import org.sa.rainbow.core.models.IModelInstance;
import org.sa.rainbow.core.models.commands.ModelCommandFactory;
import org.sa.rainbow.k8s.YamlReader;
import org.sa.rainbow.k8s.models.command.K8sModelCommandFactory;
import org.sa.rainbow.k8s.models.component.Deployment;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class K8sModelInstance implements IModelInstance<K8sDescription> {

  private final String modelName;
  private final InputStream fileInput;
  private final YamlReader yamlReader;
  private String source;
  private K8sDescription modelDescription;
  private K8sModelCommandFactory commandFactory;

  public K8sModelInstance(String modelName, InputStream fileInputStream, YamlReader yamlReader) throws IOException {
    this.modelName = modelName;
    this.fileInput = fileInputStream;
    this.yamlReader = yamlReader;
    this.modelDescription = new K8sDescription();
    readFile();
  }

  public K8sModelInstance(String modelName, InputStream fileInputStream) throws IOException {
    this(modelName, fileInputStream, new YamlReader());
  }

  public void readFile() throws IOException {
    Map<String, Map> root = yamlReader.readValue(this.fileInput);
    Map<String, Map> deployments = root.get("deployments");

    for (String deploymentName : deployments.keySet()) {
      Map<String, Map> deployment = deployments.get(deploymentName);
      Map<String, Map> attributes = deployment.get("attributes");
      modelDescription.addDeployment(createDeployment(deploymentName, attributes));
    }
  }

  private Deployment createDeployment(String name, Map<String, Map> attributes) {
    var deployment = new Deployment(name);
    attributes.keySet().forEach(a -> deployment.addAttributes(a, attributes.get(a)));
    return deployment;
  }

  @Override
  public K8sDescription getModelInstance() {
    return modelDescription;
  }

  @Override
  public void setModelInstance(K8sDescription model) {
    modelDescription = model;
  }

  @Override
  public IModelInstance<K8sDescription> copyModelInstance(String newName) {
    try {
      return new K8sModelInstance(newName, new FileInputStream(getOriginalSource()));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String getModelType() {
    return "K8s";
  }

  @Override
  public String getModelName() {
    return modelName;
  }

  @Override
  public ModelCommandFactory<K8sDescription> getCommandFactory() {
    if (commandFactory == null) {
      commandFactory = new K8sModelCommandFactory(this);
    }
    return commandFactory;
  }

  @Override
  public void setOriginalSource(String source) {
    this.source = source;
  }

  @Override
  public String getOriginalSource() {
    return source;
  }

  @Override
  public void dispose() throws RainbowException {

  }
}
