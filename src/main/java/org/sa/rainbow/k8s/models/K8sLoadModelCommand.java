package org.sa.rainbow.k8s.models;

import org.sa.rainbow.core.error.RainbowException;
import org.sa.rainbow.core.error.RainbowModelException;
import org.sa.rainbow.core.models.IModelInstance;
import org.sa.rainbow.core.models.IModelsManager;
import org.sa.rainbow.core.models.ModelReference;
import org.sa.rainbow.core.models.ModelsManager;
import org.sa.rainbow.core.models.commands.AbstractLoadModelCmd;

import java.io.IOException;
import java.io.InputStream;

import static org.sa.rainbow.k8s.models.K8sDescription.MODEL_TYPE;

public class K8sLoadModelCommand extends AbstractLoadModelCmd<K8sDescription> {

  private String modelName;
  private IModelsManager modelsManager;
  private K8sModelInstance modelInstance;
  private ModelReference modelReference;

  public K8sLoadModelCommand(String modelName, IModelsManager modelsManager, InputStream is, String source) {
    super("loadK8sModel", modelsManager, MODEL_TYPE, is, source);
    this.modelName = modelName;
    this.modelsManager = modelsManager;
    this.modelReference = new ModelReference(modelName, MODEL_TYPE);
  }

  public static K8sLoadModelCommand loadCommand(
          ModelsManager modelsManager, String modelName, InputStream modelDescriptionFile, String source) {
    return new K8sLoadModelCommand(modelName, modelsManager, modelDescriptionFile, source);
  }

  @Override
  protected void subExecute() {
    try {
      modelInstance = new K8sModelInstance(this.modelName, getStream());
      this.doPostExecute();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (RainbowModelException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void subRedo() throws RainbowException {
    doPostExecute();
  }

  @Override
  protected void subUndo() throws RainbowException {
    doPostUndo();
  }

  @Override
  protected boolean checkModelValidForCommand(Object o) {
    return false;
  }

  @Override
  public ModelReference getModelReference() {
    return modelReference;
  }

  @Override
  public IModelInstance<K8sDescription> getResult() throws IllegalStateException {
    return modelInstance;
  }
}
