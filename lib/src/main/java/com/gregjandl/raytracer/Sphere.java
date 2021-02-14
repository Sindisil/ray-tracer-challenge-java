package com.gregjandl.raytracer;

public class Sphere {
  private static final Point WORLD_ORIGIN = new Point(0, 0, 0);

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
}
