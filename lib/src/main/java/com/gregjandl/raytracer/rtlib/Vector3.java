package com.gregjandl.raytracer.rtlib;

import java.util.Objects;

/**
 * A vector in 3D space, specified to float precision.
 *
 * @author Greg Jandl (greg.jandl@gmail.com)
 */
public class Vector3 {
  private final float x;
  private final float y;
  private final float z;

  /**
   * Construct a vector in 3D space.
   *
   * @param x the X component of the vector
   * @param y the Y component of the vector
   * @param z the Z component of the vector
   */
  public Vector3(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Construct a vector in 3D space.
   *
   * @param x the X component of the vector
   * @param y the Y component of the vector
   * @param z the Z component of the vector
   */
  public Vector3(double x, double y, double z) {
    this.x = (float) x;
    this.y = (float) y;
    this.z = (float) z;
  }

  /**
   * Return the X component of the vector.
   *
   * @return the X component of the vector
   */
  public float getX() { return x;}


  /**
   * Return the Y component of the vector.
   *
   * @return the Y component of the vector
   */
  public float getY() { return y;}


  /**
   * Return the Z component of the vector.
   *
   * @return the Z component of the vector
   */
  public float getZ() { return z;}

  /**
   * Compares this {@code Vector3} to the specified {@code Object}.
   *
   * @param o {@code Object} to which this {@code Vector3} should be compared
   * @return {@code true} if and only if the specified {@code Object} is a {@code Vector3} whose
   * elements differ by less than {@link Utils#EPSILON}.
   * @see Utils#aboutEqual(double, double)
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    var vector3 = (Vector3) o;
    return Utils.aboutEqual(x, vector3.x) && Utils.aboutEqual(y, vector3.y)
        && Utils.aboutEqual(z, vector3.z);
  }

  /**
   * Returns the hash code for this {@code Vector3}
   *
   * @return hash code for this {@code Vector3}
   */
  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  /**
   * Returns a string representation of this {@code Vector3}.  This method is intended to be used
   * for debugging purposes; the representation may change, but will not be {@code null}.
   *
   * @return a string representation of this {@code Vector3}
   */
  @Override
  public String toString() {
    return "Vector3{x=" + x + ", y=" + y + ", z=" + z + '}';
  }

  /**
   * Returns the {@code Vector3} resulting from subtracting the specified {@code Vector3} from this
   * {@code Vector3}
   *
   * @param v {@code Vector3} to subtract from this {@code Vector3}
   * @return this - v
   */
  public Vector3 subtract(Vector3 v) {
    return new Vector3(x - v.getX(), y - v.getY(), z - v.getZ());
  }

  /**
   * Returns the {@code Vector3} resulting from adding the specified {@code Vector3} to this {@code
   * Vector3}
   *
   * @param v {@code Vector3} to add to this {@code Vector3}
   * @return this + v
   */
  public Vector3 add(Vector3 v) {
    return new Vector3(x + v.getX(), y + v.getY(), z + v.getZ());
  }

  /**
   * Returns the {@code Point} resulting from adding the specified {@code Point} to this {@code
   * Vector3}
   *
   * @param p {@code Point} to add to this {@code Vector3}
   * @return this + p
   */
  public Point add(Point p) {
    return new Point(x + p.getX(), y + p.getY(), z + p.getZ());
  }

  /**
   * Returns a {@code Vector3} that is the negation of this {@code Vector3}.
   *
   * @return the negation of this {@code Vector3}
   */
  public Vector3 negate() {
    return new Vector3(-x, -y, -z);
  }

  /**
   * Returns the {@code Vector3} resulting from multiplying this {$code Vector3} by the specified
   * scalar multiplier.
   *
   * @param n scalar multiplier
   * @return this * n
   */
  public Vector3 multiply(float n) {
    return new Vector3(x * n, y * n, z * n);
  }

  /**
   * Returns the {@code Vector3} resulting from dividing this {$code Vector3} by the specified
   * scalar multiplier.
   *
   * @param n scalar multiplier
   * @return this / n
   */
  public Vector3 divide(float n) {
    return new Vector3(x / n, y / n, z / n);
  }

  /**
   * Returns the magnitude (i.e. length) of this {@code Vector3}
   *
   * @return magnitude of this {@code Vector3}
   */
  public float magnitude() {
    return (float) Math.sqrt(x * x + y * y + z * z);
  }

  /**
   * Returns a normalized copy this {@code Vector3}.
   *
   * @return normalized copy of this {@code Vector3}
   * @throws ArithmeticException if this {@code Vector3} has a zero magnitude, which would result in
   *                             division by zero.
   */
  public Vector3 normalize() {
    if (magnitude() == 0) {
      throw new ArithmeticException("Can't normalize a Vector3 with zero magnitude");
    }
    var m = magnitude();
    return new Vector3(x / m, y / m, z / m);
  }

  /**
   * Returns the dot product of this {@code Vector3} and the specified {@code Vector3}.
   *
   * @param v {@code Vector3} with which to compute a dot product
   * @return the dot product of {@code v} and this {@code Vector3}
   */
  public float dot(Vector3 v) {
    return x * v.x + y * v.y + z * v.z;
  }

  /**
   * Returns the cross product of this {@code Vector3} and the specified {@code Vector3}.
   *
   * @param v {@code Vector3} with which to compute a cross product
   * @return the cross product of {@code v} and this {@code Vector3}
   */
  public Vector3 cross(Vector3 v) {
    return new Vector3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
  }
}
