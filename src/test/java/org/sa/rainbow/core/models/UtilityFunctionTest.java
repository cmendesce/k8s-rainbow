package org.sa.rainbow.core.models;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static java.util.Map.entry;

import static org.assertj.core.api.Assertions.*;

public class UtilityFunctionTest {

  /**
   * 0: 1.00
   * 100: 1.00
   * 200: 0.99
   * 500: 0.90
   * 1000: 0.75
   * 1500: 0.50
   * 2000: 0.25
   * 4000: 0.00
   */
  private Map<Integer, Double> responseTime = Map.ofEntries(
          entry(0, 1d),
          entry(1, 1d),
          entry(2, 0.99d),
          entry(500, 0.90d),
          entry(1000, 0.75d),
          entry(1500, 0.50d),
          entry(2000, 0.25d),
          entry(4000, 0d)
  );

  private UtilityFunction utility;

  @Before
  public void beforeTest() {
    utility = new UtilityFunction("id", "label", "mapping", "desc", responseTime);
  }

  @Test
  public void should_calculate_when_exact_value() {
    assertThat(utility.f(500d)).isEqualTo(.90d);
  }

  @Test
  public void should_calculate_when_below_value() {
    assertThat(utility.f(-10d)).isEqualTo(1d);
  }

  @Test
  public void should_calculate_when_negative_value() {
    assertThat(utility.f(5000d)).isEqualTo(0d);
  }

  @Test
  public void should_approximate_when_below_value() {
    final double expected = 0.9898192771084338d;
    assertThat(utility.f(3)).isEqualTo(expected);
  }
}
