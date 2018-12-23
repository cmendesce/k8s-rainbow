package org.sa.rainbow.k8s;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class YamlParser {

  public final ObjectMapper mapper;

  public YamlParser() {
    mapper = new ObjectMapper(new YAMLFactory());
  }

  public Map<String, Map> readValue(InputStream src) throws IOException {
    return mapper.readValue(src, Map.class);
  }

  public <T> T readValue(InputStream src, Class<T> valueType) throws IOException {
    return mapper.readValue(src, valueType);
  }
}
