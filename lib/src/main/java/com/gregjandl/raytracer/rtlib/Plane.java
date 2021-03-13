package com.gregjandl.raytracer.rtlib;

import static com.gregjandl.raytracer.rtlib.Utils.EPSILON;

public class Plane extends AbstractShape<Plane> implements Shape {
  private static final Vector3 normal = new Vector3(0, 1, 0);

  @Override
  protected Plane getThis() {
    return this;
  }

  /**
   * Adds entries to the specified {@code IntersectionList} representing where the specified {@code
   * Ray} (which has been transformed relative to this {@code Plane}) intersects the surface of this
   * {@code Plane}.
   *
   * @param r  the {@code Ray} that may intersect with this {@code Plane}
   * @param xs the {@code IntersectionList} to which any new intersections should be added
   * @return the specified {@code IntersectionList} with zero or more {@code Intersection}s with
   * this {@code Plane} added
   */
  @Override
  protected IntersectionList localIntersect(Ray r, IntersectionList xs) {
    if (Math.abs(r.getDirection().getY()) >= EPSILON) {
      // Ray is not parallel to the plane, nor is it coplanar, so compute intersection
      xs.add(-r.getOrigin().getY() / r.getDirection().getY(), this);
    }
    return xs;
  }

  /**
   * Return the normal at the specified point on this {@code Plane}.
   *
   * @param localPoint Point for which to calculate the normal
   * @return the normal
   */
  @Override
  protected Vector3 localNormalAt(Point localPoint) {
    return normal;
  }
}
