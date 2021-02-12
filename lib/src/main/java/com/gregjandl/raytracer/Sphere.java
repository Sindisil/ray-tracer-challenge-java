package com.gregjandl.raytracer;

public class Sphere {
  private static final float[] EMPTY_ARRAY = new float[0];
  private static final Point WORLD_ORIGIN = new Point(0, 0, 0);

  public float[] intersects(Ray r) {
    var sphereToRay = r.getOrigin().subtract(WORLD_ORIGIN);
    var a = r.getDirection().dot(r.getDirection());
    var b = 2 * r.getDirection().dot(sphereToRay);
    var c = sphereToRay.dot(sphereToRay) - 1;

    var discriminant = (b * b) - 4 * a * c;

    if (discriminant < 0) {
      return EMPTY_ARRAY;
    }

    var sqrtOfDiscriminant = Math.sqrt(discriminant);
    return new float[]{
        (float) (-b - sqrtOfDiscriminant) / (2 * a),
        (float) (-b + sqrtOfDiscriminant) / (2 * a)};
  }
}
