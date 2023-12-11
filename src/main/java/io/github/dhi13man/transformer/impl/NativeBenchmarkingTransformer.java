package io.github.dhi13man.transformer.impl;

import static io.github.dhi13man.constants.GeneralConstants.BENCHMARKING_FORKS_COUNT;
import static io.github.dhi13man.constants.GeneralConstants.BENCHMARKING_ITERATIONS_COUNT;
import static io.github.dhi13man.constants.GeneralConstants.BENCHMARKING_ITERATIONS_TIME_SECONDS;
import static io.github.dhi13man.constants.GeneralConstants.BENCHMARKING_WARMUP_FORKS_COUNT;
import static io.github.dhi13man.constants.GeneralConstants.BENCHMARKING_WARMUP_ITERATIONS_COUNT;

import io.github.dhi13man.data.LaureateData;
import io.github.dhi13man.data.PrizeData;
import io.github.dhi13man.data.PrizeData.Laureate;
import io.github.dhi13man.data.provider.BenchmarkingDataProvider;
import io.github.dhi13man.transformer.BenchmarkingTransformer;
import io.github.dhi13man.utils.BenchmarkingUtilities;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.springframework.util.StringUtils;

@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@Fork(value = BENCHMARKING_FORKS_COUNT, warmups = BENCHMARKING_WARMUP_FORKS_COUNT)
@Measurement(iterations = BENCHMARKING_ITERATIONS_COUNT, time = BENCHMARKING_ITERATIONS_TIME_SECONDS)
@Warmup(iterations = BENCHMARKING_WARMUP_ITERATIONS_COUNT, time = BENCHMARKING_ITERATIONS_TIME_SECONDS)
public class NativeBenchmarkingTransformer implements BenchmarkingTransformer {

  @Benchmark
  @Override
  public String simpleTransform(BenchmarkingDataProvider benchmarkingDataProvider) {
    final List<LaureateData> laureates = benchmarkingDataProvider.getLaureates();
    final List<String> names = laureates.stream()
        .map(this::singleSimpleTransform)
        .collect(Collectors.toList());
    return BenchmarkingUtilities.getInstance().getJsonStringFromDto(names);
  }

  @Benchmark
  @Override
  public String complexTransform(BenchmarkingDataProvider benchmarkingDataProvider) {
    final List<LaureateData> laureates = benchmarkingDataProvider.getLaureates();
    final List<ComplexAndJoinResponse> complexAndJoinRespons = laureates.stream()
        .map(this::singleComplexTransform)
        .collect(Collectors.toList());
    return BenchmarkingUtilities.getInstance().getJsonStringFromDto(complexAndJoinRespons);
  }

  @Benchmark
  @Override
  public String joinTransform(BenchmarkingDataProvider benchmarkingDataProvider) {
    final List<LaureateData> laureates = benchmarkingDataProvider.getLaureates();
    final List<PrizeData> prizes = benchmarkingDataProvider.getPrizes();
    final Map<String, LaureateData> laureateIdToLaureateMap = laureates.stream()
        .collect(Collectors.toMap(LaureateData::getId, laureate -> laureate));
    final List<ComplexAndJoinResponse> complexAndJoinRespons = prizes.stream()
        .map(
            prizeData -> {
              for (final Laureate laureate : prizeData.getLaureates()) {
                if (!laureateIdToLaureateMap.containsKey(laureate.getId())) {
                  continue;
                }
                final ComplexAndJoinResponse complexAndJoinResponse =
                    singleComplexTransform(laureateIdToLaureateMap.get(laureate.getId()));
                complexAndJoinResponse.setPrizeMetadata(prizeData.getMeta());
                return complexAndJoinResponse;
              }
              return null;
            }
        )
        .filter(Objects::nonNull)
        .toList();
    return BenchmarkingUtilities.getInstance().getJsonStringFromDto(complexAndJoinRespons);
  }

  private String singleSimpleTransform(LaureateData laureate) {
    return laureate.getKnownName().getEn();
  }

  private ComplexAndJoinResponse singleComplexTransform(LaureateData laureateData) {
    final String name = StringUtils.hasText(singleSimpleTransform(laureateData))
        ? singleSimpleTransform(laureateData)
        : laureateData.getFullName().getEn();
    final List<String> prizes = laureateData.getNobelPrizes().stream()
        .map(nobelPrize -> nobelPrize.getCategoryFullName().getEn())
        .collect(Collectors.toList());
    return ComplexAndJoinResponse.builder()
        .name(name)
        .gender(laureateData.getGender())
        .prizes(prizes)
        .build();
  }

}
