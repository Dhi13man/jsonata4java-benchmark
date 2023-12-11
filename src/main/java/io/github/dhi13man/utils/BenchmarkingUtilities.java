package io.github.dhi13man.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Objects;
import lombok.Getter;
import org.springframework.web.client.RestTemplate;

public class BenchmarkingUtilities {

  private static BenchmarkingUtilities INSTANCE;

  private final ObjectMapper objectMapper;

  @Getter
  private final RestTemplate restTemplate;

  private BenchmarkingUtilities(ObjectMapper objectMapper, RestTemplate restTemplate) {
    this.objectMapper = objectMapper;
    this.restTemplate = restTemplate;
  }

  public static BenchmarkingUtilities getInstance() {
    if (Objects.isNull(INSTANCE)) {
      final ObjectMapper objectMapper = new ObjectMapper();
      final RestTemplate restTemplate = new RestTemplate();
      INSTANCE = new BenchmarkingUtilities(objectMapper, restTemplate);
    }
    return INSTANCE;
  }

  public <T> T getDtoFromJsonString(String json, Class<T> clazz) {
    try {
      return objectMapper.readValue(json, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public <T> String getJsonStringFromDto(T dto) {
    try {
      return objectMapper.writeValueAsString(dto);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public JsonNode getJsonNodeFromJsonString(String dataString) {
    try {
      return objectMapper.readTree(dataString);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
