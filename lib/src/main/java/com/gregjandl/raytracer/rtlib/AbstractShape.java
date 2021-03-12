package com.gregjandl.raytracer.rtlib;

import java.util.Objects;

public abstract class AbstractShape<T extends AbstractShape<T>> implements Shape {
  private final T thisObj;
  protected Matrix4x4 transform = Matrix4x4.identity();
  protected Material material = new Material.Builder().build();

  public AbstractShape() {
    thisObj = getThis();
  }

  protected abstract T getThis();

  /**
   * Return this {@code AbstractShape}'s current transformation matrix.
   * <p>
   * A newly constructed {@code AbstractShape} has an identity transformation matrix.
   *
   * @return the transformation matrix
   */
  @Override
  public Matrix4x4 getTransform() {
    return transform;
  }

  /**
   * Set this {@code AbstractShape}'s transformation matrix to the specified matrix, which must be
   * non-null
   *
   * @param t the new transformation matrix
   * @return this {@code AbstractShape}
   * @throws NullPointerException if the specified matrix is {@code null}
   */
  @Override
  public T setTransform(Matrix4x4 t) {
    Objects.requireNonNull(t);
    transform = t;
    return thisObj;
  }

  /**
   * Return this {@code AbstractShape}'s {@code Material}.
   *
   * @return the material
   */
  @Override
  public Material getMaterial() {
    return material;
  }

  /**
   * Replace this {@code AbstractShape}'s {@code Material} with the specified replacement.
   *
   * @param m the new {@code Material}
   * @return this {@code AbstractShape}
   */
  @Override
  public T setMaterial(Material m) {
    Objects.requireNonNull(m);
    material = m;
    return thisObj;
  }

  /**
   * Returns a string representation of this {@code AbstractShape}. This method is intended to be
   * used for debugging purposes; the representation may change, but will not be {@code null}.
   *
   * @return a string representation of this {@code AbstractShape}
   */
  @Override
  public String toString() {
    return "Shape{transform=" + transform + ", material=" + material + '}';
  }

  /**
   * Compares this {@code AbstractShape} with the specified {@code Object} for equality.
   *
   * @param o {@code Object} to which this {@code AbstractShape} is to be compared.
   * @return {@code true} if and only if the specified {@code Object} is a {@code AbstractShape}
   * whose transform and material are equal to this {@code AbstractShape}'s
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    var shape = getClass().cast(o);
    return transform.equals(shape.transform) && material.equals(shape.material);
  }

  /**
   * Returns the hash code for this {@code AbstractShape}
   *
   * @return hash code for this {@code AbstractShape}
   */
  @Override
  public int hashCode() {
    return Objects.hash(transform, material);
  }

  /**
   * Adds entries to the specified {@code IntersectionList} representing where the specified {@code
   * Ray} (which has been transformed relative to this {@code Shape}) intersects the surface of this
   * Shape.
   *
   * @param r  the {@code Ray} that may intersect with this Shape
   * @param xs the {@code IntersectionList} to which any new intersections should be added
   * @return the specified {@code IntersectionList} with zero or more {@code Intersection}s with
   * this {@code Sphere} added
   */
  protected abstract IntersectionList localIntersect(Ray r, IntersectionList xs);

  /**
   * Adds entries to the specified {@code IntersectionList} representing where the specified {@code
   * Ray} intersects the surface of this Shape.
   *
   * @param r  the {@code Ray} that may intersect with this Shape
   * @param xs the {@code IntersectionList} to which any new intersections should be added
   * @return the specified {@code IntersectionList} with zero or more {@code Intersection}s with
   * this {@code Sphere} added
   */
  @Override
  public IntersectionList intersects(Ray r, IntersectionList xs) {
    var localRay = r.transform(transform.invert());
    return localIntersect(localRay, xs);
  }

  /**
   * Returns an {@code IntersectionList} representing where the specified {@code Ray} intersects the
   * surface of this {@code Sphere}.
   *
   * @param r the {@code Ray} that may intersect with this {@code Sphere}
   * @return list of zero or more {@code Intersection}s with this {@code Sphere}
   */
  @Override
  public IntersectionList intersects(Ray r) {
    var xs = new IntersectionList();
    return intersects(r, xs);
  }

  /**
   * Return the normal at the specified point on this sphere.
   *
   * @param localPoint Point on sphere for which to calculate the normal
   * @return the normal
   */
  protected abstract Vector3 localNormalAt(Point localPoint);

  /**
   * Return the normal at the specified point on this sphere.
   *
   * @param worldPoint Point on sphere for which to calculate the normal
   * @return the normal
   */
  @Override
  public Vector3 normalAt(Point worldPoint) {
    Objects.requireNonNull(worldPoint);
    var localPoint = transform.invert().multiply(worldPoint);
    var localNormal = localNormalAt(localPoint);
    var worldNormal = transform.invert().transpose().multiply(localNormal);
    return worldNormal.normalize();
  }
}
