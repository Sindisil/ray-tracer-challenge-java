package com.gregjandl.raytracer.rtlib;

import java.util.Objects;

/**
 * Represents a spherical object in space.
 */
public class Sphere {
  private static final Point ORIGIN = new Point(0, 0, 0);

  private Matrix4x4 transform = Matrix4x4.identity();

  /**
   * Returns an {@code IntersectionList} representing where the specified {@code Ray} intersects the
   * surface of this {@code Sphere}.
   *
   * @param r the {@code Ray} that may intersect with this {@code Sphere}
   * @return list of zero or more {@code Intersection}s with this {@code Sphere}
   */
  public IntersectionList intersects(Ray r) {
    var transformedRay = r.transform(transform.invert());
    var sphereToRay = transformedRay.getOrigin().subtract(ORIGIN);
    var a = transformedRay.getDirection().dot(transformedRay.getDirection());
    var b = 2 * transformedRay.getDirection().dot(sphereToRay);
    var c = sphereToRay.dot(sphereToRay) - 1;

    var discriminant = (b * b) - 4 * a * c;
    var xs = new IntersectionList();

    if (discriminant >= 0) {
      var sqrtOfDiscriminant = Math.sqrt(discriminant);
      xs.add((float) (-b - sqrtOfDiscriminant) / (2 * a), this);
      xs.add((float) (-b + sqrtOfDiscriminant) / (2 * a), this);
    }

    return xs;

  }

  /**
   * Return this {@code Sphere}'s current transformation matrix.
   * <p>
   * A newly constructed {@code Sphere} has an identity transformation matrix.
   *
   * @return the transformation matrix
   */
  public Matrix4x4 getTransform() {
    return transform;
  }

  /**
   * Set this {@code Sphere}'s transformation matrix to the specified matrix, which must be
   * non-null
   *
   * @param t the new transformation matrix
   * @throws NullPointerException if the specified matrix is {@code null}
   */
  public void setTransform(Matrix4x4 t) {
    Objects.requireNonNull(t);
    transform = t;
  }

  /**
   * Return the normal at the specified point on this sphere.
   * @param worldPoint Point on sphere for which to calculate the normal
   * @return the normal
   */
  public Vector3 normalAt(Point worldPoint) {
    Objects.requireNonNull(worldPoint);
    var objectPoint = transform.invert().multiply(worldPoint);
    var objectNormal = objectPoint.subtract(ORIGIN);
    var worldNormal = transform.invert().transpose().multiply(objectNormal);
    return worldNormal.normalize();
  }
}
