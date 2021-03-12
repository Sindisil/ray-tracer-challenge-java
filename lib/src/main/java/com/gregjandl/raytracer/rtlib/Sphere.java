package com.gregjandl.raytracer.rtlib;

/**
 * Represents a spherical object in space.
 */
public class Sphere extends AbstractShape<Sphere> implements Shape {
  @Override
  protected Sphere getThis() {
    return this;
  }

  /**
   * Returns a string representation of this {@code Sphere}. This method is intended to be used for
   * debugging purposes; the representation may change, but will not be {@code null}.
   *
   * @return a string representation of this {@code Sphere}
   */
  @Override
  public String toString() {
    return "Sphere{transform=" + transform + ", material=" + material + '}';
  }

  @Override
  protected IntersectionList localIntersect(Ray localRay, IntersectionList xs) {
    var sphereToRay = localRay.getOrigin().subtract(Point.ORIGIN);
    var a = localRay.getDirection().dot(localRay.getDirection());
    var b = 2 * localRay.getDirection().dot(sphereToRay);
    var c = sphereToRay.dot(sphereToRay) - 1;

    var discriminant = (b * b) - 4 * a * c;

    if (discriminant >= 0) {
      var sqrtOfDiscriminant = Math.sqrt(discriminant);
      xs.add((float) (-b - sqrtOfDiscriminant) / (2 * a), this);
      xs.add((float) (-b + sqrtOfDiscriminant) / (2 * a), this);
    }

    return xs;
  }

  @Override
  protected Vector3 localNormalAt(Point localPoint) {
    return localPoint.subtract(Point.ORIGIN);
  }
}
