package com.gregjandl.raytracer.rtlib;

/** Represents a spherical object in space. */
public class Sphere extends AbstractShape<Sphere> implements Shape {
  @Override
  protected Sphere getThis() {
    return this;
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
