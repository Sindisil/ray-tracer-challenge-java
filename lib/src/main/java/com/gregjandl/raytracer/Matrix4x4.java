package com.gregjandl.raytracer;

import java.util.Arrays;

public class Matrix4x4 {
  private final float[] m;

  public Matrix4x4(float[][] data) {
    if (data.length != 4 || data[0].length != 4) {
      throw new IllegalArgumentException("Matrix4x4 invalid initializer size [" + data.length +
          "][" + data[0].length + "]");
    }
    m = new float[16];
    for (int row = 0; row < data.length; ++row) {
      System.arraycopy(data[row], 0, m, row * 4, data[0].length);
    }
  }

  public float get(int row, int col) {
    if (row < 0 || row > 3 || col < 0 || col > 3) {
      throw new IndexOutOfBoundsException("index [" + row + ", " + col + "] not in [0-3, 0-3]");
    }
    return m[row * 4 + col];
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    Matrix4x4 other = (Matrix4x4) o;
    for (int i = 0; i < 16; ++i) {
      if (!Utils.aboutEqual(m[i], other.m[i])) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(m);
  }
}
