package io.github.dhi13man.transformer;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.dhi13man.data.provider.BenchmarkingDataProvider;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

public interface BenchmarkingTransformer {

  String simpleTransform(BenchmarkingDataProvider benchmarkingDataProvider);

  String complexTransform(BenchmarkingDataProvider benchmarkingDataProvider);

  String joinTransform(BenchmarkingDataProvider benchmarkingDataProvider);

  @AllArgsConstructor
  @Builder
  @Data
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @NoArgsConstructor
  class ComplexAndJoinResponse {

    private String name;

    private String gender;

    private List<String> prizes;

    @Nullable
    private Object prizeMetadata;
  }
}
