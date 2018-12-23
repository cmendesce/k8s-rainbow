package org.sa.rainbow;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.apis.AppsV1Api;
import io.kubernetes.client.apis.AppsV1beta2Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.apis.ExtensionsApi;
import io.kubernetes.client.models.V1Deployment;
import io.kubernetes.client.models.V1Pod;
import io.kubernetes.client.models.V1PodList;
import io.kubernetes.client.models.V1beta2Deployment;
import org.assertj.core.util.Streams;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static io.kubernetes.client.util.Config.defaultClient;
import static java.util.stream.Collectors.*;
import static org.assertj.core.util.Streams.*;

public class ExtensionApi {

  public static void main(String[] args) throws IOException, ApiException {
    var client = defaultClient();
    ExtensionsApi api1 = new ExtensionsApi(defaultClient());

    AppsV1Api appsV1Api = new AppsV1Api(defaultClient());
    V1Deployment dep = null;
    V1Pod pod = null;

    var corev1 = new CoreV1Api(client);
    V1PodList list = null;

//    appsV1beta2Api.readNamespacedDeployment()
      // /apis/apps/v1beta2/namespaces/{namespace}/deployments/{name}
      // /apis/apps/v1     /namespaces/{namespace}/deployments/{name}


//    Request request = client.buildRequest("/apis/metrics.k8s.io/v1beta1/nodes", "GET", new ArrayList<>(), new ArrayList<>(), null, new HashMap<>(), new HashMap<>(), new String[]{}, null);

//    Request request = client.buildRequest("/apis/metrics.k8s.io/v1beta1/nodes", "GET", new ArrayList<>(), new ArrayList<>(), null, new HashMap<>(), new HashMap<>(), new String[]{}, null);

    Request request = client.buildRequest("/apis/custom.metrics.k8s.io/v1beta1/namespaces/default/pods/*/http_requests?selector=app=podinfo", "GET", new ArrayList<>(), new ArrayList<>(), null, new HashMap<>(), new HashMap<>(), new String[]{}, null);
    Response response = client.getHttpClient().newCall(request).execute();

    ObjectMapper mapper = new ObjectMapper();


    var jsonNode = mapper.readValue(response.body().string(), JsonNode.class);
    var items = jsonNode.withArray("items").elements();

    Arrays.stream(new double[]{1,3,2,5,8}).average().getAsDouble();

    var values = stream(items).map(ExtensionApi::toFloat).toArray();

    System.out.println(values);
  }

  public static double toFloat(JsonNode node) {
    return Double.parseDouble(
            node.get("value").toString().replace("m", "").replace("\"", "")
    );
  }
}
