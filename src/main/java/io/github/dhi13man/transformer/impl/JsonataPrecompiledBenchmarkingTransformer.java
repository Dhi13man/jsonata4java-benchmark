package io.github.dhi13man.transformer.impl;

import static io.github.dhi13man.constants.GeneralConstants.BENCHMARKING_FORKS_COUNT;
import static io.github.dhi13man.constants.GeneralConstants.BENCHMARKING_ITERATIONS_COUNT;
import static io.github.dhi13man.constants.GeneralConstants.BENCHMARKING_ITERATIONS_TIME_SECONDS;
import static io.github.dhi13man.constants.GeneralConstants.BENCHMARKING_WARMUP_FORKS_COUNT;
import static io.github.dhi13man.constants.GeneralConstants.BENCHMARKING_WARMUP_ITERATIONS_COUNT;
import static io.github.dhi13man.constants.JsonataConstants.COMPLEX_EXPRESSION_JSONATA;
import static io.github.dhi13man.constants.JsonataConstants.JOIN_EXPRESSION_JSONATA;
import static io.github.dhi13man.constants.JsonataConstants.SIMPLE_EXPRESSION_JSONATA;

import com.api.jsonata4java.expressions.EvaluateException;
import com.api.jsonata4java.expressions.Expressions;
import com.api.jsonata4java.expressions.ParseException;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.dhi13man.data.provider.BenchmarkingDataProvider;
import io.github.dhi13man.transformer.BenchmarkingTransformer;
import io.github.dhi13man.utils.BenchmarkingUtilities;
import java.io.IOException;
import org.jetbrains.annotations.NotNull;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;

@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@Fork(value = BENCHMARKING_FORKS_COUNT, warmups = BENCHMARKING_WARMUP_FORKS_COUNT)
@Measurement(iterations = BENCHMARKING_ITERATIONS_COUNT, time = BENCHMARKING_ITERATIONS_TIME_SECONDS)
@Warmup(iterations = BENCHMARKING_WARMUP_ITERATIONS_COUNT, time = BENCHMARKING_ITERATIONS_TIME_SECONDS)
public class JsonataPrecompiledBenchmarkingTransformer implements BenchmarkingTransformer {

  private static final @NotNull Expressions simpleExpression =
      getJsonataExpression(SIMPLE_EXPRESSION_JSONATA);

  private static final @NotNull Expressions complexExpression =
      getJsonataExpression(COMPLEX_EXPRESSION_JSONATA);

  private static final @NotNull Expressions joinExpression =
      getJsonataExpression(JOIN_EXPRESSION_JSONATA);

  @NotNull
  private static Expressions getJsonataExpression(String expression) {
    try {
      return Expressions.parse(expression);
    } catch (ParseException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Benchmark
  @Override
  public String simpleTransform(BenchmarkingDataProvider benchmarkingDataProvider) {
    final String laureateDataString = benchmarkingDataProvider.getLaureatesString();
    final JsonNode laureateData = BenchmarkingUtilities.getInstance()
        .getJsonNodeFromJsonString(laureateDataString);
    final JsonNode result = evaluateJsonataExpression(simpleExpression, laureateData);
    return result.toString();
  }

  @Benchmark
  @Override
  public String complexTransform(BenchmarkingDataProvider benchmarkingDataProvider) {
    final String laureateDataString = benchmarkingDataProvider.getLaureatesString();
    final JsonNode laureateData = BenchmarkingUtilities.getInstance()
        .getJsonNodeFromJsonString(laureateDataString);
    final JsonNode result = evaluateJsonataExpression(complexExpression, laureateData);
    return result.toString();
  }

  @Benchmark
  @Override
  public String joinTransform(BenchmarkingDataProvider benchmarkingDataProvider) {
    // Extra serialization step that is not in native transformer
    final String benchmarkingJson = BenchmarkingUtilities.getInstance()
        .getJsonStringFromDto(benchmarkingDataProvider);
    final JsonNode benchmarkingDataJsonNode = BenchmarkingUtilities.getInstance()
        .getJsonNodeFromJsonString(benchmarkingJson);
    final JsonNode result = evaluateJsonataExpression(joinExpression, benchmarkingDataJsonNode);
    return result.toString();
  }

  private JsonNode evaluateJsonataExpression(@NotNull Expressions expression, JsonNode jsonNode) {
    try {
      return expression.evaluate(jsonNode);
    } catch (EvaluateException e) {
      throw new RuntimeException(e);
    }
  }
}
