package org.sa.rainbow.k8s.models;

import org.sa.rainbow.core.error.RainbowException;
import org.sa.rainbow.core.models.IModelInstance;
import org.sa.rainbow.core.models.commands.ModelCommandFactory;
import org.sa.rainbow.k8s.YamlParser;
import org.sa.rainbow.k8s.models.command.K8sModelCommandFactory;
import org.sa.rainbow.k8s.models.component.Deployment;
import org.sa.rainbow.k8s.models.parser.ModelParser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.sa.rainbow.k8s.models.K8sDescription.MODEL_TYPE;

public class K8sModelInstance implements IModelInstance<K8sDescription> {

  private final String modelName;
  private final InputStream fileInput;
  private String source;
  private K8sDescription modelDescription;
  private K8sModelCommandFactory commandFactory;

  public K8sModelInstance(String modelName, InputStream fileInputStream, YamlParser yamlParser) throws IOException {
    this.modelName = modelName;
    this.fileInput = fileInputStream;
    this.modelDescription = new K8sDescription();
    readFile();
  }

  public K8sModelInstance(String modelName, InputStream fileInputStream) throws IOException {
    this(modelName, fileInputStream, null);
  }

  private void readFile() throws IOException {
    ModelParser parser = new ModelParser();
    modelDescription = parser.parse(this.fileInput);
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
    return MODEL_TYPE;
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
