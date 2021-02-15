package com.gregjandl.raytracer.rtlib;

import java.util.Objects;

/**
 * Represents a spherical object in space.
 */
public class Sphere {
  private static final Point WORLD_ORIGIN = new Point(0, 0, 0);

  private Matrix4x4 transform = Matrix4x4.identity();

  /**
   * Returns an {@code Intersections} object representing where the specified {@code Ray}
   * intersects the surface of this {@code Sphere}.
   * @param r the {@code Ray} that may intersect with this {@code Sphere}
   * @return {@code Intersections} object containing zero or more {@code Intersection}s with this
   * {@code Sphere}
   */
  public Intersections intersects(Ray r) {
    var sphereToRay = r.getOrigin().subtract(WORLD_ORIGIN);
    var a = r.getDirection().dot(r.getDirection());
    var b = 2 * r.getDirection().dot(sphereToRay);
    var c = sphereToRay.dot(sphereToRay) - 1;

    var discriminant = (b * b) - 4 * a * c;
    var xs = new Intersections();

    if (discriminant >= 0) {
      var sqrtOfDiscriminant = Math.sqrt(discriminant);
      xs.add((float) (-b - sqrtOfDiscriminant) / (2 * a), this);
      xs.add((float) (-b + sqrtOfDiscriminant) / (2 * a), this);
    }

    return xs;

  }

  /**
   * Return this {@code Sphere}'s current transformation matrix.
   *
   * A newly constructed {@code Sphere} has an identity transformation matrix.
   * @return the transformation matrix
   */
  public Matrix4x4 getTransform() {
    return transform;
  }

  /**
   * Set this {@code Sphere}'s transformation matrix to the specified matrix, which must be
   * non-null
   * @param t the new transformation matrix
   * @throws NullPointerException if the specified matrix is {@code null}
   */
  public void setTransform(Matrix4x4 t) {
    Objects.requireNonNull(t);
    transform = t;
  }
}
