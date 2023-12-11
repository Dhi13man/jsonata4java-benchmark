package io.github.dhi13man.transformer;

import io.github.dhi13man.data.provider.BenchmarkingDataProvider;
import io.github.dhi13man.transformer.impl.JsonataBenchmarkingTransformer;
import io.github.dhi13man.transformer.impl.JsonataPrecompiledBenchmarkingTransformer;
import io.github.dhi13man.transformer.impl.NativeBenchmarkingTransformer;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BenchmarkingTransformerTest {

  private static final Logger LOGGER = Logger
      .getLogger(BenchmarkingTransformerTest.class.getName());

  private static final BenchmarkingDataProvider BENCHMARKING_DATA_PROVIDER = new BenchmarkingDataProvider();

  private static final BenchmarkingTransformer NATIVE_BENCHMARKING_TRANSFORMER =
      new NativeBenchmarkingTransformer();

  private static final BenchmarkingTransformer JSONATA_BENCHMARKING_TRANSFORMER =
      new JsonataBenchmarkingTransformer();

  private static final BenchmarkingTransformer JSONATA_PRECOMPILED_BENCHMARKING_TRANSFORMER =
      new JsonataPrecompiledBenchmarkingTransformer();

  @Test
  void simpleTransform() {
    // Act
    final String nativeSimpleTransform = NATIVE_BENCHMARKING_TRANSFORMER
        .simpleTransform(BENCHMARKING_DATA_PROVIDER);
    final String jsonataSimpleTransform = JSONATA_BENCHMARKING_TRANSFORMER
        .simpleTransform(BENCHMARKING_DATA_PROVIDER);
    final String jsonataPrecompiledSimpleTransform = JSONATA_PRECOMPILED_BENCHMARKING_TRANSFORMER
        .simpleTransform(BENCHMARKING_DATA_PROVIDER);

    // Assert
    Assertions.assertEquals(nativeSimpleTransform, jsonataSimpleTransform);
    Assertions.assertEquals(nativeSimpleTransform, jsonataPrecompiledSimpleTransform);
  }

  @Test
  void complexTransform() {
    // Act
    final String nativeComplexTransform = NATIVE_BENCHMARKING_TRANSFORMER
        .complexTransform(BENCHMARKING_DATA_PROVIDER);
    final String jsonataComplexTransform = JSONATA_BENCHMARKING_TRANSFORMER
        .complexTransform(BENCHMARKING_DATA_PROVIDER);
    final String jsonataPrecompiledSimpleTransform = JSONATA_PRECOMPILED_BENCHMARKING_TRANSFORMER
        .complexTransform(BENCHMARKING_DATA_PROVIDER);

    // Assert
    Assertions.assertEquals(nativeComplexTransform, jsonataComplexTransform);
    Assertions.assertEquals(nativeComplexTransform, jsonataPrecompiledSimpleTransform);
  }

  @Test
  void joinTransform() {
    // Act
    final String nativeJoinTransform = NATIVE_BENCHMARKING_TRANSFORMER
        .joinTransform(BENCHMARKING_DATA_PROVIDER);
    final String jsonataJoinTransform = JSONATA_BENCHMARKING_TRANSFORMER
        .joinTransform(BENCHMARKING_DATA_PROVIDER);
    final String jsonataPrecompiledJoinTransform = JSONATA_PRECOMPILED_BENCHMARKING_TRANSFORMER
        .joinTransform(BENCHMARKING_DATA_PROVIDER);

    // Assert
    Assertions.assertEquals(nativeJoinTransform, jsonataJoinTransform);
    Assertions.assertEquals(nativeJoinTransform, jsonataPrecompiledJoinTransform);
  }
}