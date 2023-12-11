package io.github.dhi13man.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LaureateData {

  private String id;
  private Name knownName;
  private Name givenName;
  private Name familyName;
  private Name fullName;
  private String fileName;
  private String gender;
  private Birth birth;
  private Wikipedia wikipedia;
  private Wikidata wikidata;
  private List<String> sameAs;
  private List<Link> links;
  private List<NobelPrize> nobelPrizes;
  private Meta meta;
  private Object death;

  @Data
  @NoArgsConstructor
  public static class Name {

    private String en;
    private String no;
    private String se;
  }

  @Data
  @NoArgsConstructor
  public static class Birth {

    private String date;
    private Place place;

    @Data
    @NoArgsConstructor
    public static class Place {

      private Location city;
      private Location country;
      private Location cityNow;
      private Location countryNow;
      private Location continent;
      private Location locationString;
    }
  }

  @Data
  @NoArgsConstructor
  public static class Wikipedia {

    private String slug;
    private String english;
  }

  @Data
  @NoArgsConstructor
  public static class Wikidata {

    private String id;
    private String url;
  }

  @Data
  @NoArgsConstructor
  public static class Link {

    private String rel;
    private String href;
    private String action;
    private String types;
    private String title;
    @JsonProperty("class")
    private List<String> _class;
  }

  @Data
  @NoArgsConstructor
  public static class NobelPrize {

    private String awardYear;
    private Name category;
    private Name categoryFullName;
    private String sortOrder;
    private String portion;
    private String dateAwarded;
    private String prizeStatus;
    private Name motivation;
    private int prizeAmount;
    private int prizeAmountAdjusted;
    private List<Affiliation> affiliations;
    private List<Link> links;

    @Data
    @NoArgsConstructor
    public static class Affiliation {

      private Name name;
      private Name nameNow;
      private Location city;
      private Location country;
      private Location cityNow;
      private Location countryNow;
      private Location continent;
      private Location locationString;
    }
  }

  @Data
  @NoArgsConstructor
  public static class Meta {

    private String terms;
    private String license;
    private String disclaimer;
  }

  @Data
  @NoArgsConstructor
  public static class Location {

    private String en;
    private String no;
    private String se;
    private List<String> sameAs;
    private String latitude;
    private String longitude;
  }
}