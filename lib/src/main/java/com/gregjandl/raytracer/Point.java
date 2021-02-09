package com.gregjandl.raytracer;

import java.util.Objects;

/**
 * A point representing a location in 3D space, specified in float precision.
 *
 * @author Greg Jandl (greg.jandl@gmail.com)
 */
public class Point {
  private final float x;
  private final float y;
  private final float z;

  /**
   * Constructs a {@code Point} in 3D space
   *
   * @param x x coordinate
   * @param y y coordinate
   * @param z z coordinate
   */
  Point(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  /**
   * Constructs a {@code Point} in 3D space
   *
   * @param x x coordinate
   * @param y y coordinate
   * @param z z coordinate
   */
  Point(double x, double y, double z) {
    this.x = (float) x;
    this.y = (float) y;
    this.z = (float) z;
  }

  /**
   * Returns the X coordinate of this {@code Point}.
   *
   * @return the X coordinate of this {@code Point}
   */
  public float getX() { return x;}

  /**
   * Returns the Y coordinate of this {@code Point}.
   *
   * @return the Y coordinate of this {@code Point}
   */
  public float getY() { return y;}

  /**
   * Returns the Z coordinate of this {@code Point}.
   *
   * @return the Z coordinate of this {@code Point}
   */
  public float getZ() { return z;}

  /**
   * Compares this {@code Point} to the specified {@code Object}.
   *
   * @param o {@code Object} to which this {@code Point} should be compared
   * @return {@code true} if and only if the specified {@code Object} is a {@code Point} whose
   * elements differ by less than {@link Utils#EPSILON}.
   * @see Utils#aboutEqual(double, double)
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true;}
    if (o == null || getClass() != o.getClass()) { return false;}
    var point = (Point) o;
    return Utils.aboutEqual(point.x, x) && Utils.aboutEqual(point.y, y)
        && Utils.aboutEqual(point.z, z);
  }

  /**
   * Returns the hash code for this {@code Point}
   *
   * @return hash code for this {@code Point}
   */
  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  /**
   * Returns a string representation of this {@code Point}. This method is intended to be used for
   * debugging purposes; the representation may change, but will not be {@code null}.
   *
   * @return a string representation of this {@code Point}
   */
  @Override
  public String toString() {
    return "Point{x=" + x + ", y=" + y + ", z=" + z + '}';
  }

  /**
   * Returns the a {@code Point} that is the result of adding the specified {@code Vector3} to this
   * {@code Point}.
   *
   * @param v the {@code Vector} to be added to this {@code Point}
   * @return this + v
   */
  public Point add(Vector3 v) {
    return new Point(x + v.getX(), y + v.getY(), z + v.getZ());
  }

  /**
   * Returns the a {@code Vector3} that is the result of subtracting the specified {@code Point}
   * from this {@code Point} In other words, the {@code Vector3} pointing from the {@code Point p}
   * to this {@code Point}).
   *
   * @param p the point to be subtracted from this point
   * @return this - p
   */
  public Vector3 subtract(Point p) {
    return new Vector3(x - p.getX(), y - p.getY(), z - p.getZ());
  }

  /**
   * Returns the {@code Point} that is the result of subtracting the specified {@code Vector} from
   * this point.
   *
   * @param v {@code Vector} to be subtracted from this {@code Point}
   * @return this - v
   */
  public Point subtract(Vector3 v) {
    return new Point(x - v.getX(), y - v.getY(), z - v.getZ());
  }
}
