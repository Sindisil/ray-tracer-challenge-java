package com.gregjandl.raytracer.rtlib;

import java.util.Arrays;

/** {@code Matrix4x4} represents a 4x4 float matrix. */
public class Matrix4x4 {
  private final float[] m = new float[16];

  /** Constructs a 4x4 matrix initialized to all zeros. */
  public Matrix4x4() {}

  /**
   * Constructs a 4x4 matrix from the supplied two dimensional float array.
   *
   * @param data float[4][4] containing initialization data for new matrix
   */
  public Matrix4x4(float[][] data) {
    if (data.length != 4 || data[0].length != 4) {
      throw new IllegalArgumentException(
          "Matrix4x4 invalid initializer size ["
              + data.length
              + "]["
              + (data.length > 0 ? data[0].length : "--")
              + "]");
    }
    for (int row = 0; row < data.length; ++row) {
      System.arraycopy(data[row], 0, m, row * 4, data[0].length);
    }
  }

  /**
   * Returns a 4x4 identity Matrix.
   *
   * <pre>
   *   +--        --+
   *   | 1  0  0  0 |
   *   | 0  1  0  0 |
   *   | 0  0  1  0 |
   *   | 0  0  0  1 |
   *   +--        --+
   * </pre>
   *
   * @return a 4x4 identity Matrix
   */
  public static Matrix4x4 identity() {
    return new Matrix4x4().set(0, 0, 1).set(1, 1, 1).set(2, 2, 1).set(3, 3, 1);
  }

  /**
   * Generate a transformation matrix to translate in {@code x}, {@code y}, and {@code z}
   * dimensions.
   *
   * <pre>
   *   +--        --+
   *   | 1  0  0  x |
   *   | 0  1  0  y |
   *   | 0  0  1  z |
   *   | 0  0  0  1 |
   *   +--        --+
   * </pre>
   *
   * @param x translation factor in {@code x} dimension.
   * @param y translation factor in {@code y} dimension.
   * @param z translation factor in {@code z} dimension.
   * @return the transformation matrix
   */
  public static Matrix4x4 translation(float x, float y, float z) {
    return identity().set(0, 3, x).set(1, 3, y).set(2, 3, z);
  }

  /**
   * Generate a transformation matrix to scale in {@code x}, {@code y}, and {@code z} dimensions.
   *
   * <pre>
   *   +--        --+
   *   | x  0  0  0 |
   *   | 0  y  0  0 |
   *   | 0  0  z  0 |
   *   | 0  0  0  1 |
   *   +--        --+
   * </pre>
   *
   * @param x scaling factor in {@code x} dimension.
   * @param y scaling factor in {@code y} dimension.
   * @param z scaling factor in {@code z} dimension.
   * @return the transformation matrix
   */
  public static Matrix4x4 scaling(float x, float y, float z) {
    return new Matrix4x4().set(0, 0, x).set(1, 1, y).set(2, 2, z).set(3, 3, 1);
  }

  /**
   * Generate a transformation matrix to perform clockwise rotation around the X axis.
   *
   * <pre>
   *   +--                   --+
   *   | 1     0       0    0 |
   *   | 0   cos r  -sin r  0 |
   *   | 0   sin r   cos r  0 |
   *   | 0     0       0    0 |
   *   +--                   --+
   * </pre>
   *
   * @param r clockwise rotation in radians
   * @return the transformation matrix
   */
  public static Matrix4x4 rotationOnX(double r) {
    return new Matrix4x4()
        .set(0, 0, 1)
        .set(1, 1, (float) Math.cos(r))
        .set(1, 2, -((float) Math.sin(r)))
        .set(2, 1, (float) Math.sin(r))
        .set(2, 2, (float) Math.cos(r))
        .set(3, 3, 1);
  }

  /**
   * Generate a transformation matrix to perform clockwise rotation around the Y axis.
   *
   * <pre>
   *   +--                 --+
   *   |  cos r  0  sin r  0 |
   *   |    0    0    0    0 |
   *   | -sin r  0  cos r  0 |
   *   |    0    0    0    0 |
   *   +--                 --+
   * </pre>
   *
   * @param r clockwise rotation in radians
   * @return the transformation matrix
   */
  public static Matrix4x4 rotationOnY(double r) {
    return new Matrix4x4()
        .set(0, 0, (float) Math.cos(r))
        .set(0, 2, (float) Math.sin(r))
        .set(1, 1, 1)
        .set(2, 0, -((float) Math.sin(r)))
        .set(2, 2, (float) Math.cos(r))
        .set(3, 3, 1);
  }

  /**
   * Generate a transformation matrix to perform clockwise rotation around the Z axis.
   *
   * <pre>
   *   +--                 --+
   *   | cos r  -sin r  0  0 |
   *   | sin r   cos r  0  0 |
   *   |  0       0     0  0 |
   *   |  0       0     0  0 |
   *   +--                 --+
   * </pre>
   *
   * @param r clockwise rotation in radians
   * @return the transformation matrix
   */
  public static Matrix4x4 rotationOnZ(double r) {
    return new Matrix4x4()
        .set(0, 0, (float) Math.cos(r))
        .set(0, 1, -((float) Math.sin(r)))
        .set(1, 0, (float) Math.sin(r))
        .set(1, 1, (float) Math.cos(r))
        .set(2, 2, 1)
        .set(3, 3, 1);
  }

  /**
   * Generate a transformation matrix to perform a shear operation in one or more of:
   *
   * <ul>
   *   <li>x in proportion to y
   *   <li>x in proportion to z
   *   <li>y in proportion to x
   *   <li>y in proportion to z
   *   <li>z in proportion to x
   *   <li>z in proportion to y
   * </ul>
   *
   * <pre>
   *   +--           --+
   *   |  1  xy  xz  0 |
   *   | yx   1  yz  0 |
   *   | zx  zy   1  0 |
   *   |  0   0   0  0 |
   *   +--           --+
   * </pre>
   *
   * @param xy factor y is multiplied by before adding to x
   * @param xz factor z is multiplied by before adding to x
   * @param yx factor x is multiplied by before adding to y
   * @param yz factor z is multiplied by before adding to y
   * @param zx factor x is multiplied by before adding to z
   * @param zy factor y is multiplied by before adding to z
   * @return the transformation matrix
   */
  public static Matrix4x4 shearing(float xy, float xz, float yx, float yz, float zx, float zy) {
    return identity()
        .set(0, 1, xy)
        .set(0, 2, xz)
        .set(1, 0, yx)
        .set(1, 2, yz)
        .set(2, 0, zx)
        .set(2, 1, zy);
  }

  /**
   * Return the value of the matrix element at ({@code row}, {@code col}).
   *
   * <p>The matrix coordinates are zero based (i.e., valid range is [0, 3]).
   *
   * @param row the row coordinate of the element
   * @param col the column coordinate of the element
   * @return the value of specified element
   */
  public float get(int row, int col) {
    if (row < 0 || row > 3 || col < 0 || col > 3) {
      throw new IndexOutOfBoundsException("index [" + row + ", " + col + "] not in [0-3, 0-3]");
    }
    return m[row * 4 + col];
  }

  Matrix4x4 set(int row, int col, float val) {
    if (row < 0 || row > 3 || col < 0 || col > 3) {
      throw new IndexOutOfBoundsException("index [" + row + ", " + col + "] not in [0-3, 0-3]");
    }
    m[row * 4 + col] = val;
    return this;
  }

  /**
   * Compares this {@code Matrix4x4} to the specified {@code Object}.
   *
   * @param o {@code Object} to which this {@code Matrix4x4} should be compared
   * @return {@code true} if and only if the specified {@code Object} is a {@code Matrix4x4} whose
   *     elements differ by less than {@link Utils#EPSILON}.
   * @see Utils#aboutEqual(double, double)
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Matrix4x4 other = (Matrix4x4) o;
    for (int i = 0; i < 16; ++i) {
      if (!Utils.aboutEqual(m[i], other.m[i])) {
        return false;
      }
    }
    return true;
  }

  /**
   * Returns the hash code for this {@code Vector3}
   *
   * @return hash code for this {@code Vector3}
   */
  @Override
  public int hashCode() {
    return Arrays.hashCode(m);
  }

  /**
   * Returns a string representation of this {@code Matrix4x4}. This method is intended to be used
   * for debugging purposes; the representation may change, but will not be {@code null}.
   *
   * @return a string representation of this {@code Matrix4x4}
   */
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

  /**
   * Returns a {@code Matrix4x4} whose value is (this * other).
   *
   * @param other {@code Matrix4x4} by which this {@code Matrix4x4} is to be multiplied
   * @return this * other
   */
  public Matrix4x4 multiply(Matrix4x4 other) {
    var res = new Matrix4x4();
    for (int r = 0; r < 4; ++r) {
      for (int c = 0; c < 4; ++c) {
        res.set(
            r,
            c,
            get(r, 0) * other.get(0, c)
                + get(r, 1) * other.get(1, c)
                + get(r, 2) * other.get(2, c)
                + get(r, 3) * other.get(3, c));
      }
    }
    return res;
  }

  /**
   * Returns a {@code Matrix4x4} whose value is (this * v).
   *
   * @param v {@code Vector3} by which this {@code Matrix4x4} is to be multiplied
   * @return this * v
   */
  public Vector3 multiply(Vector3 v) {
    return new Vector3(
        get(0, 0) * v.getX() + get(0, 1) * v.getY() + get(0, 2) * v.getZ(),
        get(1, 0) * v.getX() + get(1, 1) * v.getY() + get(1, 2) * v.getZ(),
        get(2, 0) * v.getX() + get(2, 1) * v.getY() + get(2, 2) * v.getZ());
  }

  /**
   * Returns a {@code Matrix4x4} whose value is (this * p).
   *
   * @param p {@code Point} by which this {@code Matrix4x4} is to be multiplied
   * @return this * p
   */
  public Point multiply(Point p) {
    return new Point(
        get(0, 0) * p.getX() + get(0, 1) * p.getY() + get(0, 2) * p.getZ() + get(0, 3),
        get(1, 0) * p.getX() + get(1, 1) * p.getY() + get(1, 2) * p.getZ() + get(1, 3),
        get(2, 0) * p.getX() + get(2, 1) * p.getY() + get(2, 2) * p.getZ() + get(2, 3));
  }

  /**
   * Returns a {@code Matrix4x4} whose values is this {@code Matrix4x4} transposed.
   *
   * @return this transposed
   */
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
    /*
     * cSpell:ignore CCRR
     * 2x2 determinants needed to compute the full determinant.
     * name format is dCCRR where CC == columns and RR == rows
     */
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

  /**
   * Tests if the matrix is invertible.
   *
   * @return {@code true} if the matrix is invertible, {@code false} if not.
   */
  public boolean isInvertible() {
    return determinant() != 0;
  }

  /**
   * Calculates the inverse of the matrix.
   *
   * @return a matrix that is the inverse of this matrix
   */
  public Matrix4x4 invert() {

    /*
     * 2x2 determinants needed to compute the full determinant.
     * name format is dCCRR where CC == columns and RR == rows
     */
    var d2323 = get(2, 2) * get(3, 3) - get(2, 3) * get(3, 2);
    var d1323 = get(2, 1) * get(3, 3) - get(2, 3) * get(3, 1);
    var d1223 = get(2, 1) * get(3, 2) - get(2, 2) * get(3, 1);
    var d0323 = get(2, 0) * get(3, 3) - get(2, 3) * get(3, 0);
    var d0223 = get(2, 0) * get(3, 2) - get(2, 2) * get(3, 0);
    var d0123 = get(2, 0) * get(3, 1) - get(2, 1) * get(3, 0);

    var determinant =
        get(0, 0) * (get(1, 1) * d2323 - get(1, 2) * d1323 + get(1, 3) * d1223)
            - get(0, 1) * (get(1, 0) * d2323 - get(1, 2) * d0323 + get(1, 3) * d0223)
            + get(0, 2) * (get(1, 0) * d1323 - get(1, 1) * d0323 + get(1, 3) * d0123)
            - get(0, 3) * (get(1, 0) * d1223 - get(1, 1) * d0223 + get(1, 2) * d0123);

    if (determinant == 0) {
      throw new ArithmeticException("Matrix with 0 determinant not invertible.");
    }

    var invDeterminant = 1 / determinant;

    /*
     * Additional 2x2 determinants needed to invert matrix
     * names are dCCRR where CC == cols && RR == rows
     */
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
    inverted.set(
        0, 0, invDeterminant * (get(1, 1) * d2323 - get(1, 2) * d1323 + get(1, 3) * d1223));
    inverted.set(
        0, 1, invDeterminant * -(get(0, 1) * d2323 - get(0, 2) * d1323 + get(0, 3) * d1223));
    inverted.set(
        0, 2, invDeterminant * (get(0, 1) * d2313 - get(0, 2) * d1313 + get(0, 3) * d1213));
    inverted.set(
        0, 3, invDeterminant * -(get(0, 1) * d2312 - get(0, 2) * d1312 + get(0, 3) * d1212));

    inverted.set(
        1, 0, invDeterminant * -(get(1, 0) * d2323 - get(1, 2) * d0323 + get(1, 3) * d0223));
    inverted.set(
        1, 1, invDeterminant * (get(0, 0) * d2323 - get(0, 2) * d0323 + get(0, 3) * d0223));
    inverted.set(
        1, 2, invDeterminant * -(get(0, 0) * d2313 - get(0, 2) * d0313 + get(0, 3) * d0213));
    inverted.set(
        1, 3, invDeterminant * (get(0, 0) * d2312 - get(0, 2) * d0312 + get(0, 3) * d0212));

    inverted.set(
        2, 0, invDeterminant * (get(1, 0) * d1323 - get(1, 1) * d0323 + get(1, 3) * d0123));
    inverted.set(
        2, 1, invDeterminant * -(get(0, 0) * d1323 - get(0, 1) * d0323 + get(0, 3) * d0123));
    inverted.set(
        2, 2, invDeterminant * (get(0, 0) * d1313 - get(0, 1) * d0313 + get(0, 3) * d0113));
    inverted.set(
        2, 3, invDeterminant * -(get(0, 0) * d1312 - get(0, 1) * d0312 + get(0, 3) * d0112));

    inverted.set(
        3, 0, invDeterminant * -(get(1, 0) * d1223 - get(1, 1) * d0223 + get(1, 2) * d0123));
    inverted.set(
        3, 1, invDeterminant * (get(0, 0) * d1223 - get(0, 1) * d0223 + get(0, 2) * d0123));
    inverted.set(
        3, 2, invDeterminant * -(get(0, 0) * d1213 - get(0, 1) * d0213 + get(0, 2) * d0113));
    inverted.set(
        3, 3, invDeterminant * (get(0, 0) * d1212 - get(0, 1) * d0212 + get(0, 2) * d0112));

    return inverted;
  }

  /**
   * Apply a transformation matrix to this matrix, translating in {@code x}, {@code y}, and {@code
   * z} dimensions.
   *
   * @param x Translation factor for {@code x} dimension.
   * @param y Translation factor for {@code y} dimension.
   * @param z Translation factor for {@code z} dimension.
   * @return the transformed matrix
   * @see Matrix4x4#translation(float x, float y, float z)
   */
  public Matrix4x4 translate(float x, float y, float z) {
    return translation(x, y, z).multiply(this);
  }

  /**
   * Apply a transformation matrix to this matrix, scaling in {@code x}, {@code y}, and {@code z}
   * dimensions.
   *
   * @param x Scaling factor for {@code x} dimension.
   * @param y Scaling factor for {@code y} dimension.
   * @param z Scaling factor for {@code z} dimension.
   * @return the transformed matrix
   * @see Matrix4x4#scale(float x, float y, float z)
   */
  public Matrix4x4 scale(float x, float y, float z) {
    return scaling(x, y, z).multiply(this);
  }

  /**
   * Apply a transformation matrix to this matrix to rotate {@code r} radians clockwise around the X
   * axis.
   *
   * @param r Amount to rotate, in radians
   * @return The transformed matrix
   * @see Matrix4x4#rotationOnX(double)
   */
  public Matrix4x4 rotateOnX(double r) {
    return rotationOnX(r).multiply(this);
  }

  /**
   * Apply a transformation matrix to this matrix to rotate {@code r} radians clockwise around the Y
   * axis.
   *
   * @param r Amount to rotate, in radians
   * @return The transformed matrix
   * @see Matrix4x4#rotationOnY(double r)
   */
  public Matrix4x4 rotateOnY(double r) {
    return rotationOnY(r).multiply(this);
  }

  /**
   * Apply a transformation matrix to this matrix to rotate {@code r} radians clockwise around the Z
   * axis.
   *
   * @param r Amount to rotate, in radians
   * @return The transformed matrix
   * @see Matrix4x4#rotationOnZ(double r)
   */
  public Matrix4x4 rotateOnZ(double r) {
    return rotationOnZ(r).multiply(this);
  }

  /**
   * Apply a transformation matrix to this matrix to shear in one or more of:
   *
   * <ul>
   *   <li>x in proportion to y
   *   <li>x in proportion to z
   *   <li>y in proportion to x
   *   <li>y in proportion to z
   *   <li>z in proportion to x
   *   <li>z in proportion to y
   * </ul>
   *
   * @param xy factor y is multiplied by before adding to x
   * @param xz factor z is multiplied by before adding to x
   * @param yx factor x is multiplied by before adding to y
   * @param yz factor z is multiplied by before adding to y
   * @param zx factor x is multiplied by before adding to z
   * @param zy factor y is multiplied by before adding to z
   * @return the transformed matrix
   * @see Matrix4x4#shearing(float xy, float xz, float yx, float yz, float zx, float zy)
   */
  public Matrix4x4 shear(float xy, float xz, float yx, float yz, float zx, float zy) {
    return shearing(xy, xz, yx, yz, zx, zy).multiply(this);
  }
}
