package io.github.dhi13man.data.provider;

import static io.github.dhi13man.constants.GeneralConstants.LAUREATE_JSON_DATA_SOURCE;
import static io.github.dhi13man.constants.GeneralConstants.PRIZES_JSON_DATA_SOURCE;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.dhi13man.data.LaureateData;
import io.github.dhi13man.data.LaureateList;
import io.github.dhi13man.data.PrizeData;
import io.github.dhi13man.data.PrizeList;
import io.github.dhi13man.utils.BenchmarkingUtilities;
import java.util.List;
import lombok.Data;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@Data
@State(Scope.Benchmark)
public class BenchmarkingDataProvider {

  private List<LaureateData> laureates;

  private List<PrizeData> prizes;

  @JsonIgnore
  private String laureatesString;

  @JsonIgnore
  private String prizesString;

  public BenchmarkingDataProvider() {
    this.fetchBenchmarkingData();
  }

  @Setup(Level.Trial)
  public void fetchBenchmarkingData() {
    this.laureatesString = BenchmarkingUtilities.getInstance().getRestTemplate()
        .getForObject(LAUREATE_JSON_DATA_SOURCE, String.class);
    this.prizesString = BenchmarkingUtilities.getInstance().getRestTemplate()
        .getForObject(PRIZES_JSON_DATA_SOURCE, String.class);
    this.laureates = BenchmarkingUtilities.getInstance()
        .getDtoFromJsonString(laureatesString, LaureateList.class);
    this.prizes = BenchmarkingUtilities.getInstance()
        .getDtoFromJsonString(prizesString, PrizeList.class);
  }
}
