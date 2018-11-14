package org.sa.rainbow.k8s.analysis;

import org.sa.rainbow.core.AbstractRainbowRunnable;
import org.sa.rainbow.core.RainbowComponentT;
import org.sa.rainbow.core.analysis.IRainbowAnalysis;
import org.sa.rainbow.core.error.RainbowConnectionException;
import org.sa.rainbow.core.event.IRainbowMessage;
import org.sa.rainbow.core.models.ModelReference;
import org.sa.rainbow.core.ports.*;

import static org.sa.rainbow.core.RainbowComponentT.ANALYSIS;

public class UtilityAnalysis extends AbstractRainbowRunnable implements IRainbowAnalysis {

  public UtilityAnalysis() {
    super("Rainbow K8s Utility Evaluator");
  }

  /** Used to get the current state of a model, e.g., the Acme model and the utility model **/
  private IModelsManagerPort m_modelsManagerPort;
  /** Subscribes to changes to the Acme model on this port **/
  private IModelChangeBusSubscriberPort m_modelChangePort;
  /** Changes to the Utility History model are announced on this port **/
  private IModelUSBusPort m_modelUpstreamPort;

  @Override
  public void initialize (IRainbowReportingPort port) throws RainbowConnectionException {
    super.initialize (port);
    initializeConnections ();
    initializeSubscriptions ();
  }

  private void initializeSubscriptions () {
    m_modelChangePort.subscribe(this::onModelChange, this::onModelChangeEvent);
  }

  private void initializeConnections () throws RainbowConnectionException {
    m_modelsManagerPort = RainbowPortFactory.createModelsManagerRequirerPort ();
    m_modelChangePort = RainbowPortFactory.createModelChangeBusSubscriptionPort ();
    m_modelUpstreamPort = RainbowPortFactory.createModelsManagerClientUSPort (this);
  }



  @Override
  protected void log(String txt) {

  }

  @Override
  protected void runAction() {

//    ModelReference ref = null;
//        synchronized (m_modelQ) {
//            ref = m_modelQ.poll ();
//        }
//        if (ref != null) {
//            Collection<? extends String> forTracing = m_modelsManagerPort.getRegisteredModelTypes ();
//            UtilityModelInstance utilityModel = (UtilityModelInstance )m_modelsManagerPort
//                    .<UtilityPreferenceDescription> getModelInstance (new ModelReference (ref.getModelName (), UtilityModelInstance.UTILITY_MODEL_TYPE));
//            AcmeModelInstance acmeModel = (AcmeModelInstance )m_modelsManagerPort.<IAcmeSystem> getModelInstance (ref);
//            if (utilityModel != null && acmeModel != null) {
//                Map<String, Double> utilities = computeSystemInstantUtility (utilityModel.getModelInstance (),
//                        acmeModel,
//                        m_reportingPort);
//                UtilityHistoryModelInstance historyModel = (UtilityHistoryModelInstance )m_modelsManagerPort
//                        .<UtilityHistory> getModelInstance (new ModelReference (ref.getModelName (),
//                                UtilityHistoryModelInstance.UTILITY_HISTORY_TYPE));
//                List<IRainbowOperation> cmds = new ArrayList<> (utilities.size ());
//
//                AddUtilityMeasureCmd command = historyModel.getCommandFactory ().addUtilityMeasureCmd (
//                        OVERALL_UTILITY_KEY, utilities.get (OVERALL_UTILITY_KEY));
//                cmds.add (command);
//                for (Entry<String, Double> e : utilities.entrySet ()) {
//                    if (OVERALL_UTILITY_KEY.equals (e.getKey ())) {
//                        continue;
//                    }
//                    cmds.add (historyModel.getCommandFactory ().addUtilityMeasureCmd (e.getKey (), e.getValue ()));
//                }
//                m_modelUpstreamPort.updateModel (cmds, true);
//            }
//        }
  }

//  private Map<String, Double> computeSystemInstantUtility (UtilityPreferenceDescription utilityModel,
//                                                           AcmeModelInstance acmeModel) {
//    Map<String, Double> weights = utilityModel.weights
//            .get (Rainbow.instance().getProperty (RainbowConstants.PROPKEY_SCENARIO));
//    Map<String, Double> utilities = new HashMap<>();
//    double[] conds = new double[utilityModel.getUtilities ().size ()];
//    int i = 0;
//    double score = 0.0;
//    for (Map.Entry<String, UtilityFunction> e : utilityModel.getUtilityFunctions ().entrySet ()) {
//      double v = 0.0;
//      UtilityFunction u = e.getValue ();
//      // add attribute value from current condition to accumulated agg value
//      Object condVal = acmeModel.getProperty (u.mapping ());
//      if (condVal != null) {
//        double val = 0.0;
//        if (condVal instanceof Double) {
//          val = ((Double )condVal).doubleValue ();
//        }
//        else if (condVal instanceof Float) {
//          val = ((Float )condVal).doubleValue ();
//        }
//        else if (condVal instanceof Integer) {
//          val = ((Integer )condVal).doubleValue ();
//        }
//        conds[i] = val;
//        v += conds[i];
//      }
//      // now compute the utility, apply weight, and accumulate to sum
//      if (weights.containsKey (e.getKey ())) {
//        double utility = u.f (v);
//        utilities.put (e.getKey (), utility);
//        score += weights.get (e.getKey ()) * utility;
//      }
//    }
//    utilities.put (OVERALL_UTILITY_KEY, score);
//    return utilities;
//  }

  @Override
  public RainbowComponentT getComponentType() {
    return ANALYSIS;
  }

  @Override
  public void dispose() {

  }

  public boolean onModelChange(IRainbowMessage message) {
    Object type = message.getProperty(IModelChangeBusPort.EVENT_TYPE_PROP);
    if (type != null) {
      Object modelType = message.getProperty(IModelChangeBusPort.MODEL_TYPE_PROP);
      return "K8s".equals(modelType);
    }
    return false;
  }

  public void onModelChangeEvent(ModelReference reference, IRainbowMessage message) {

  }

  @Override
  public void setProperty(String key, String value) { }

  @Override
  public String getProperty(String key) { return null; }
}
