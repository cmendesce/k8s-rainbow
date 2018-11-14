//import com.squareup.okhttp.Call;
//import com.squareup.okhttp.Request;
//import com.squareup.okhttp.Response;
//import io.kubernetes.client.ApiClient;
//import io.kubernetes.client.ApiException;
//import io.kubernetes.client.Configuration;
//import io.kubernetes.client.apis.AppsV1Api;
//import io.kubernetes.client.apis.AppsV1beta1Api;
//import io.kubernetes.client.apis.AppsV1beta2Api;
//import io.kubernetes.client.apis.CoreV1Api;
//import io.kubernetes.client.models.*;
//import io.kubernetes.client.util.Config;
//import org.sa.rainbow.k8s.models.SystemMetrics;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class K8sTest {
//
//  public static void main(String[] args) throws IOException, ApiException {
////    ApiClient client = Config.defaultClient();
////    Configuration.setDefaultApiClient(client);
////
////    AppsV1Api apiv1 = new AppsV1Api(client);
////
////
////    AppsV1beta2Api api = new AppsV1beta2Api(client);
////
////
////    CoreV1Api coreV1Api = new CoreV1Api(client);
////
////
////    String namespace = "monitoring"; // String | object name and auth scope, such as for teams and projects
////    Boolean includeUninitialized = true; // Boolean | If true, partially initialized resources are included in the response.
////    String pretty = ""; // String | If 'true', then the output is pretty printed.
////    String _continue = ""; // String | The continue option should be set when retrieving more results from the server. Since this value is server defined, clients may only use the continue value from a previous query result with identical query parameters (except for the value of continue) and the server may reject a continue value it does not recognize. If the specified continue value is no longer valid whether due to expiration (generally five to fifteen minutes) or a configuration change on the server, the server will respond with a 410 ResourceExpired error together with a continue token. If the client needs a consistent list, it must restart their list without the continue field. Otherwise, the client may send another list request with the token received with the 410 error, the server will respond with a list starting from the next key, but from the latest snapshot, which is inconsistent from the previous list results - objects that are created, modified, or deleted after the first list request will be included in the response, as long as their keys are after the \"next key\".  This field is not supported when watch is true. Clients may start a watch from the last resourceVersion value returned by the server and not miss any modifications.
////    String fieldSelector = ""; // String | A selector to restrict the list of returned objects by their fields. Defaults to everything.
////    String labelSelector = ""; // String | A selector to restrict the list of returned objects by their labels. Defaults to everything.
////    Integer limit = 56; // Integer | limit is a maximum number of responses to return for a list call. If more items exist, the server will set the `continue` field on the list metadata to a value that can be used with the same initial query to retrieve the next set of results. Setting a limit may return fewer than the requested amount of items (up to zero items) in the event all requested objects are filtered out and clients should only use the presence of the continue field to determine whether more results are available. Servers may choose not to support the limit argument and will return all of the available results. If limit is specified and the continue field is empty, clients may assume that no more results are available. This field is not supported if watch is true.  The server guarantees that the objects returned when using continue will be identical to issuing a single list call without a limit - that is, no objects created, modified, or deleted after the first request is issued will be included in any subsequent continued requests. This is sometimes referred to as a consistent snapshot, and ensures that a client that is using limit to receive smaller chunks of a very large result can ensure they see all possible objects. If objects are updated during a chunked list the version of the object that was present at the time the first list result was calculated is returned.
////    String resourceVersion = ""; // String | When specified with a watch call, shows changes that occur after that particular version of a resource. Defaults to changes from the beginning of history. When specified for list: - if unset, then the result is returned from remote storage based on quorum-read flag; - if it's 0, then we simply return what we currently have in cache, no guarantee; - if set to non zero, then the result is at least as fresh as given rv.
////    Integer timeoutSeconds = 56; // Integer | Timeout for the list/watch call. This limits the duration of the call, regardless of any activity or inactivity.
////    Boolean watch = false; // Boolean | Watch for changes to the described resources and return them as a stream of add, update, and remove notifications. Specify resourceVersion.
////
////      V1PodList result = coreV1Api.listNamespacedPod(namespace, pretty, _continue, fieldSelector, includeUninitialized, labelSelector, limit, resourceVersion, timeoutSeconds, watch);
////
////      V1beta2DeploymentList deploymentList = api.listNamespacedDeployment(namespace, pretty, _continue, fieldSelector,
////              includeUninitialized, labelSelector, limit, resourceVersion, timeoutSeconds, watch);
////
////      for (V1beta2Deployment deployment :
////              deploymentList.getItems()) {
////        try {
//////          deployment.getMetadata().
//////          V1Pod pod = coreV1Api.readNamespacedPod(deployment.getMetadata().getName(), namespace, pretty, false, true);
//////          System.out.println(pod);
////        } catch (ApiException e) {
////          System.out.println("error");
////        }
////
////      }
//
//
//
//
//    //String path, String method, List<Pair> queryParams, List<Pair> collectionQueryParams, Object body, Map<String, String> headerParams, Map<String, Object> formParams, String[] authNames, ProgressRequestListener progressRequestListener
////    Request request = client.buildRequest("/apis/metrics.k8s.io/v1beta1/nodes", "GET", new ArrayList<>(), new ArrayList<>(), null, new HashMap<>(), new HashMap<>(), new String[]{}, null);
////    Response response = client.getHttpClient().newCall(request).execute();
////
////
////    AppsV1beta1Api api = new AppsV1beta1Api(client);
////    AppsV1beta2Api api2 = new AppsV1beta2Api(client);
////    V1APIResourceList list =api2.getAPIResources();
//
//
//
////    V2beta1MetricSpec
//
//
//
//
////    AppsV1beta1Deployment deployment = api.readNamespacedDeployment("prometheus", "monitoring", null, null, null);
////    AppsV1beta1DeploymentSpec deploymentSpec = deployment.getSpec();
////    deploymentSpec.setReplicas(2);
////    System.out.println(deploymentSpec.getReplicas());
//
//  }
//}
