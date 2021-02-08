package com.gregjandl.raytracer;

public final class Utils {
  public static final float EPSILON = 0.0001f;

  public static boolean aboutEqual(double lhs, double rhs) {
    return Math.abs(lhs - rhs) < EPSILON;
  }

  private Utils() {}

}
