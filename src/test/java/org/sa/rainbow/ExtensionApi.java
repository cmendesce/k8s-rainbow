package org.sa.rainbow;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static io.kubernetes.client.util.Config.defaultClient;

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

    Request request = client.buildRequest("/apis/metrics.k8s.io/v1beta1/nodes", "GET", new ArrayList<>(), new ArrayList<>(), null, new HashMap<>(), new HashMap<>(), new String[]{}, null);
    Response response = client.getHttpClient().newCall(request).execute();

  }
}
