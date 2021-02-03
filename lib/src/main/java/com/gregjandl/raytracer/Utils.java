package com.gregjandl.raytracer;

public class Utils {
  public static final float EPSILON = 0.0001f;

  public static boolean aboutEqual(float lhs, float rhs) {
    return Math.abs(lhs - rhs) < EPSILON;
  }

  private Utils() {}

}
