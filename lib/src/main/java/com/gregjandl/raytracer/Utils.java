package com.gregjandl.raytracer;

/**
 * {@code Utils} collects utility classes and constants related to this raytracer implementation.
 */
public final class Utils {
  /**
   * The minimum absolute value difference between two values for them to be considered different.
   */
  public static final float EPSILON = 0.0001f;

  /**
   * Compare two values for equality within {@code EPSILON}.
   * <p>
   * This method accepts {@code double} values for convenience, but they are down cast to {@code
   * float} before comparison.
   *
   * @param lhs first value to compare
   * @param rhs second value to compare
   * @return {@code} true if and only if the two values differ by less than {@code EPSILON}
   */
  public static boolean aboutEqual(double lhs, double rhs) {
    return Math.abs(lhs - rhs) < EPSILON;
  }

  private Utils() {}

}
