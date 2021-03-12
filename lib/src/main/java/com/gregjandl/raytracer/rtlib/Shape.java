package com.gregjandl.raytracer.rtlib;

public interface Shape {
  /**
   * Return this {@code Shape}'s current transformation matrix.
   * <p>
   * A newly constructed {@code Shape} has an identity transformation matrix.
   *
   * @return the transformation matrix
   */
  Matrix4x4 getTransform();

  /**
   * Set this {@code Shape}'s transformation matrix to the specified matrix, which must be non-null
   *
   * @param t the new transformation matrix
   * @return this {@code Shape}
   * @throws NullPointerException if the specified matrix is {@code null}
   */
  Shape setTransform(Matrix4x4 t);

  /**
   * Return this {@code Shape}'s {@code Material}.
   *
   * @return the material
   */
  Material getMaterial();

  /**
   * Replace this {@code Shape}'s {@code Material} with the specified replacement.
   *
   * @param m the new {@code Material}
   * @return this {@code Shape}
   */
  Shape setMaterial(Material m);

  /**
   * Adds entries to the specified {@code IntersectionList} representing where the specified {@code
   * Ray} intersects the surface of this Shape.
   *
   * @param r  the {@code Ray} that may intersect with this Shape
   * @param xs the {@code IntersectionList} to which any new intersections should be added
   * @return the specified {@code IntersectionList} with zero or more {@code Intersection}s with
   * this {@code Sphere} added
   */
  IntersectionList intersects(Ray r, IntersectionList xs);

  /**
   * Returns an {@code IntersectionList} representing where the specified {@code Ray} intersects the
   * surface of this {@code Sphere}.
   *
   * @param r the {@code Ray} that may intersect with this {@code Sphere}
   * @return list of zero or more {@code Intersection}s with this {@code Sphere}
   */
  IntersectionList intersects(Ray r);

  /**
   * Return the normal at the specified point on this {@code Shape}.
   *
   * @param worldPoint Point on {@code Shape} for which to calculate the normal
   * @return the normal
   */
  Vector3 normalAt(Point worldPoint);
}
