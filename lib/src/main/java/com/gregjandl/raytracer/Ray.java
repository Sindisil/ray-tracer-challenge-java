package com.gregjandl.raytracer;

import java.util.Objects;

/**
 * {@code Ray} represents a geometric ray in 3D space, with an origin point and a direction vector.
 */
public class Ray {
  private final Point origin;
  private final Vector3 direction;

  /**
   * Create a {@code Ray} with the specified origin and direction
   *
   * @param origin    the Ray's origin
   * @param direction the Ray's direction
   */
  public Ray(Point origin, Vector3 direction) {
    this.origin = origin;
    this.direction = direction;
  }

  /**
   * Return this {@code Ray}'s origin
   *
   * @return this {@code Ray}'s origin
   */
  public Point getOrigin() {
    return origin;
  }

  /**
   * Return this {@code Ray}'s direction
   *
   * @return this {@code Ray}'s direction
   */
  public Vector3 getDirection() {
    return direction;
  }

  /**
   * Compares this {@code Ray} with the specified {@code Object} for equality.
   *
   * @param o {@code Object} to which this {@code Ray} is to be compared.
   * @return {@code true} if and only if the specified {@code Object} is a {@code Ray} whose origin
   * and direction are equal to this {@code Ray}'s
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    Ray ray = (Ray) o;
    return origin.equals(ray.origin) && direction.equals(ray.direction);
  }

  /**
   * Returns the hash code for this {@code Ray}
   *
   * @return hash code for this {@code Ray}
   */
  @Override
  public int hashCode() {
    return Objects.hash(origin, direction);
  }

  /**
   * Returns a string representation of this {@code Ray}. This method is intended to be used for
   * debugging purposes; the representation may change, but will not be {@code null}.
   *
   * @return a string representation of this {@code Ray}
   */
  @Override
  public String toString() {
    return "Ray{origin=" + origin + ", direction=" + direction + '}';
  }

  public Point getPosition(float t) {
    return origin.add(direction.multiply(t));
  }
}
