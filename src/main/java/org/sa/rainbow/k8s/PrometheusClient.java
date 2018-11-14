package org.sa.rainbow.k8s;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PrometheusClient {

  private static final String PATH_QUERY = "/api/v1/query";
  private final String host;

  public PrometheusClient(String host) {
    this.host = host;
  }

  public JSONObject query(String promql) {
    try {
      String url = host.concat(PATH_QUERY).concat("?query=").concat(promql);
      return doGet(new URL(url));
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
    return null;
  }

  private JSONObject doGet(URL url) {
    try {
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      if (connection.getResponseCode() == 200) {
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          content.append(line);
        }
        reader.close();
        return new JSONObject(content.toString());
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return (JSONObject) JSONObject.NULL;
  }
}

