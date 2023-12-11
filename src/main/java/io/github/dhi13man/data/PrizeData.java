package io.github.dhi13man.data;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrizeData {

  private String id;
  private String awardYear;
  private Name category;
  private Name categoryFullName;
  private String dateAwarded;
  private int prizeAmount;
  private int prizeAmountAdjusted;
  private Link links;
  private List<Laureate> laureates;
  private Meta meta;

  @Data
  @NoArgsConstructor
  public static class Name {

    private String en;
    private String no;
    private String se;
  }

  @Data
  @NoArgsConstructor
  public static class Link {

    private String rel;
    private String href;
    private String action;
    private String types;
  }

  @Data
  @NoArgsConstructor
  public static class Laureate {

    private String id;
    private Name knownName;
    private Name fullName;
    private String portion;
    private String sortOrder;
    private Name motivation;
    private Link links;
  }

  @Data
  @NoArgsConstructor
  public static class Meta {

    private String terms;
    private String license;
    private String disclaimer;
  }
}