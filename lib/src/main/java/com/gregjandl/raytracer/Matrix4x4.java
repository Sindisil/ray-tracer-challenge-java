package com.gregjandl.raytracer;

import java.util.Arrays;

public class Matrix4x4 {
  private final float[] m = new float[16];

  /**
   * Constructs a 4x4 matrix initialized to all zeros.
   */
  public Matrix4x4() {}

  /**
   * Constructs a 4x4 matrix from the supplied two dimensional float array.
   *
   * @param data float[4][4] containing initialization data for new matrix
   */
  public Matrix4x4(float[][] data) {
    if (data.length != 4 || data[0].length != 4) {
      throw new IllegalArgumentException("Matrix4x4 invalid initializer size [" + data.length +
          "][" + (data.length > 0 ? data[0].length : "--") + "]");
    }
    for (int row = 0; row < data.length; ++row) {
      System.arraycopy(data[row], 0, m, row * 4, data[0].length);
    }
  }

  /**
   * Returns a 4x4 identity Matrix.
   */
  public static Matrix4x4 identity() {
    return new Matrix4x4().set(0, 0, 1).set(1, 1, 1).set(2, 2, 1).set(3, 3, 1);
  }

  public float get(int row, int col) {
    if (row < 0 || row > 3 || col < 0 || col > 3) {
      throw new IndexOutOfBoundsException("index [" + row + ", " + col + "] not in [0-3, 0-3]");
    }
    return m[row * 4 + col];
  }

  public Matrix4x4 set(int row, int col, float val) {
    if (row < 0 || row > 3 || col < 0 || col > 3) {
      throw new IndexOutOfBoundsException("index [" + row + ", " + col + "] not in [0-3, 0-3]");
    }
    m[row * 4 + col] = val;
    return this;
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

  @Override
  public String toString() {
    var b = new StringBuilder("Matrix4x4{");
    for (int row = 0; row < 4; ++row) {
      b.append("{");
      for (int col = 0; col < 4; ++col) {
        b.append(m[row * 4 + col]);
        if (col < 3) {
          b.append(", ");
        }
      }
      b.append(row < 3 ? "}, " : "}");
    }
    b.append("}");
    return b.toString();
  }
}
