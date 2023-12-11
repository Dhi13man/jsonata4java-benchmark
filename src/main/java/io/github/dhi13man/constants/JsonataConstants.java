package io.github.dhi13man.constants;

public final class JsonataConstants {

  public static final String SIMPLE_EXPRESSION_JSONATA = "[knownName.en]";
  public static final String COMPLEX_EXPRESSION_JSONATA = """
      [
        $.{
              "name": knownName.en ? knownName.en : orgName.en,
              "gender": gender,
              "prizes": nobelPrizes.categoryFullName.en[]
        }
      ]""";
  public static final String JOIN_EXPRESSION_JSONATA = """
      [
        {
            "name": laureates.knownName.en,
            "gender": laureates.gender,
            "prizes": prizes.categoryFullName.en[],
            "prizeMetadata": prizes.meta
        }
      ]""";

  private JsonataConstants() {
  }
}
