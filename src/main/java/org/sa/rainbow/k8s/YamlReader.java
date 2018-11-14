package org.sa.rainbow.k8s;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YamlReader {

  private final ObjectMapper mapper;

  public YamlReader() {
    mapper = new ObjectMapper(new YAMLFactory());
  }

  public Map<String, Map> readValue(InputStream src) throws IOException {
    return mapper.readValue(src, Map.class);
  }
}
