package com.gregjandl.raytracer;

import java.util.Collections;
import java.util.List;

public class Sphere {
  private static final Point WORLD_ORIGIN = new Point(0, 0, 0);

  public List<Intersection> intersects(Ray r) {
    var sphereToRay = r.getOrigin().subtract(WORLD_ORIGIN);
    var a = r.getDirection().dot(r.getDirection());
    var b = 2 * r.getDirection().dot(sphereToRay);
    var c = sphereToRay.dot(sphereToRay) - 1;

    var discriminant = (b * b) - 4 * a * c;

    if (discriminant < 0) {
      return Collections.emptyList();
    }

    var sqrtOfDiscriminant = Math.sqrt(discriminant);
    return List.of(
        new Intersection((float) (-b - sqrtOfDiscriminant) / (2 * a), this),
        new Intersection((float) (-b + sqrtOfDiscriminant) / (2 * a), this));
  }
}
