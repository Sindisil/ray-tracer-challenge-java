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

  public Matrix4x4 multiply(Matrix4x4 other) {
    var res = new Matrix4x4();
    for (int r = 0; r < 4; ++r) {
      for (int c = 0; c < 4; ++c) {
        res.set(r, c,
            get(r, 0) * other.get(0, c)
                + get(r, 1) * other.get(1, c)
                + get(r, 2) * other.get(2, c)
                + get(r, 3) * other.get(3, c));
      }
    }
    return res;
  }

  public Vector3 multiply(Vector3 v) {
    return new Vector3(
        get(0, 0) * v.getX() + get(0, 1) * v.getY() + get(0, 2) * v.getZ(),
        get(1, 0) * v.getX() + get(1, 1) * v.getY() + get(1, 2) * v.getZ(),
        get(2, 0) * v.getX() + get(2, 1) * v.getY() + get(2, 2) * v.getZ());
  }

  public Point multiply(Point p) {
    return new Point(
        get(0, 0) * p.getX() + get(0, 1) * p.getY() + get(0, 2) * p.getZ() + get(0, 3),
        get(1, 0) * p.getX() + get(1, 1) * p.getY() + get(1, 2) * p.getZ() + get(1, 3),
        get(2, 0) * p.getX() + get(2, 1) * p.getY() + get(2, 2) * p.getZ() + get(2, 3));
  }

  public Matrix4x4 transpose() {
    var ret = new Matrix4x4();
    for (int c = 0; c < 4; ++c) {
      for (int r = 0; r < 4; ++r) {
        ret.set(c, r, get(r, c));
      }
    }
    return ret;
  }

  /**
   * Calculates the determinant of the matrix.
   *
   * @return the determinant
   */
  public float determinant() {
    // 2x2 determinants needed to compute the full determinant.
    // name format is dCCRR where CC == columns and RR == rows
    var d2323 = get(2, 2) * get(3, 3) - get(2, 3) * get(3, 2);
    var d1323 = get(2, 1) * get(3, 3) - get(2, 3) * get(3, 1);
    var d1223 = get(2, 1) * get(3, 2) - get(2, 2) * get(3, 1);
    var d0323 = get(2, 0) * get(3, 3) - get(2, 3) * get(3, 0);
    var d0223 = get(2, 0) * get(3, 2) - get(2, 2) * get(3, 0);
    var d0123 = get(2, 0) * get(3, 1) - get(2, 1) * get(3, 0);

    return get(0, 0) * (get(1, 1) * d2323 - get(1, 2) * d1323 + get(1, 3) * d1223)
        - get(0, 1) * (get(1, 0) * d2323 - get(1, 2) * d0323 + get(1, 3) * d0223)
        + get(0, 2) * (get(1, 0) * d1323 - get(1, 1) * d0323 + get(1, 3) * d0123)
        - get(0, 3) * (get(1, 0) * d1223 - get(1, 1) * d0223 + get(1, 2) * d0123);
  }

  public boolean isInvertible() {
    return determinant() != 0;
  }

  public Matrix4x4 invert() {
    // 2x2 determinants needed to compute the full determinant.
    // name format is dCCRR where CC == columns and RR == rows
    var d2323 = get(2, 2) * get(3, 3) - get(2, 3) * get(3, 2);
    var d1323 = get(2, 1) * get(3, 3) - get(2, 3) * get(3, 1);
    var d1223 = get(2, 1) * get(3, 2) - get(2, 2) * get(3, 1);
    var d0323 = get(2, 0) * get(3, 3) - get(2, 3) * get(3, 0);
    var d0223 = get(2, 0) * get(3, 2) - get(2, 2) * get(3, 0);
    var d0123 = get(2, 0) * get(3, 1) - get(2, 1) * get(3, 0);

    var determinant = get(0, 0) * (get(1, 1) * d2323 - get(1, 2) * d1323 + get(1, 3) * d1223)
        - get(0, 1) * (get(1, 0) * d2323 - get(1, 2) * d0323 + get(1, 3) * d0223)
        + get(0, 2) * (get(1, 0) * d1323 - get(1, 1) * d0323 + get(1, 3) * d0123)
        - get(0, 3) * (get(1, 0) * d1223 - get(1, 1) * d0223 + get(1, 2) * d0123);

    if (determinant == 0) {
      throw new ArithmeticException("Matrix with 0 determinant not invertible.");
    }

    var invDeterminant = 1 / determinant;

    // Additional 2x2 determinants needed to invert matrix
    //    names are dCCRR where CC == cols && RR == rows
    var d2313 = get(1, 2) * get(3, 3) - get(1, 3) * get(3, 2);
    var d1313 = get(1, 1) * get(3, 3) - get(1, 3) * get(3, 1);
    var d1213 = get(1, 1) * get(3, 2) - get(1, 2) * get(3, 1);
    var d2312 = get(1, 2) * get(2, 3) - get(1, 3) * get(2, 2);
    var d1312 = get(1, 1) * get(2, 3) - get(1, 3) * get(2, 1);
    var d1212 = get(1, 1) * get(2, 2) - get(1, 2) * get(2, 1);
    var d0313 = get(1, 0) * get(3, 3) - get(1, 3) * get(3, 0);
    var d0213 = get(1, 0) * get(3, 2) - get(1, 2) * get(3, 0);
    var d0312 = get(1, 0) * get(2, 3) - get(1, 3) * get(2, 0);
    var d0212 = get(1, 0) * get(2, 2) - get(1, 2) * get(2, 0);
    var d0113 = get(1, 0) * get(3, 1) - get(1, 1) * get(3, 0);
    var d0112 = get(1, 0) * get(2, 1) - get(1, 1) * get(2, 0);

    Matrix4x4 inverted = new Matrix4x4();
    inverted.set(0, 0,
        invDeterminant * (get(1, 1) * d2323 - get(1, 2) * d1323 + get(1, 3) * d1223));
    inverted.set(0, 1,
        invDeterminant * -(get(0, 1) * d2323 - get(0, 2) * d1323 + get(0, 3) * d1223));
    inverted.set(0, 2,
        invDeterminant * (get(0, 1) * d2313 - get(0, 2) * d1313 + get(0, 3) * d1213));
    inverted.set(0, 3,
        invDeterminant * -(get(0, 1) * d2312 - get(0, 2) * d1312 + get(0, 3) * d1212));

    inverted.set(1, 0,
        invDeterminant * -(get(1, 0) * d2323 - get(1, 2) * d0323 + get(1, 3) * d0223));
    inverted.set(1, 1,
        invDeterminant * (get(0, 0) * d2323 - get(0, 2) * d0323 + get(0, 3) * d0223));
    inverted.set(1, 2,
        invDeterminant * -(get(0, 0) * d2313 - get(0, 2) * d0313 + get(0, 3) * d0213));
    inverted.set(1, 3,
        invDeterminant * (get(0, 0) * d2312 - get(0, 2) * d0312 + get(0, 3) * d0212));

    inverted.set(2, 0,
        invDeterminant * (get(1, 0) * d1323 - get(1, 1) * d0323 + get(1, 3) * d0123));
    inverted.set(2, 1,
        invDeterminant * -(get(0, 0) * d1323 - get(0, 1) * d0323 + get(0, 3) * d0123));
    inverted.set(2, 2,
        invDeterminant * (get(0, 0) * d1313 - get(0, 1) * d0313 + get(0, 3) * d0113));
    inverted.set(2, 3,
        invDeterminant * -(get(0, 0) * d1312 - get(0, 1) * d0312 + get(0, 3) * d0112));

    inverted.set(3, 0,
        invDeterminant * -(get(1, 0) * d1223 - get(1, 1) * d0223 + get(1, 2) * d0123));
    inverted.set(3, 1,
        invDeterminant * (get(0, 0) * d1223 - get(0, 1) * d0223 + get(0, 2) * d0123));
    inverted.set(3, 2,
        invDeterminant * -(get(0, 0) * d1213 - get(0, 1) * d0213 + get(0, 2) * d0113));
    inverted.set(3, 3,
        invDeterminant * (get(0, 0) * d1212 - get(0, 1) * d0212 + get(0, 2) * d0112));

    return inverted;
  }
}
