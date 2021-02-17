package com.gregjandl.raytracer.rtlib;

import com.gregjandl.raytracer.rtlib.Intersections.Intersection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents a collection of zero or more {@code Intersection}s, ordered by {@code t}.
 */
class Intersections extends AbstractList<Intersection> {
  private final List<Intersection> xs;

  /**
   * Create an empty {@code Intersections} collection.
   */
  public Intersections() {
    xs = new ArrayList<>();
  }

  @Override
  public Iterator<Intersection> iterator() {
    return xs.iterator();
  }

  @Override
  public boolean add(Intersection intersection) {
    int i = 0;
    while (i < xs.size() && intersection.t > xs.get(i).t) {
      ++i;
    }
    xs.add(i, intersection);
    return true;
  }

  /**
   * Construct a new {@code Intersection} from the specified time {@code t} and Sphere {@code
   * object} and add it to this {@code Intersections} collection, maintaining the sorted order of
   * entries.
   *
   * @param t time of new {@code Intersection}
   * @param object {@code Sphere} of new {{@code Intersection}
   */
  public void add(float t, Sphere object) {
    var intersection = new Intersection(t, object);
    add(intersection);
  }

  @Override
  public boolean contains(Object o) {
    return xs.contains(o);
  }

  @Override
  public Intersection get(int i) {
    return xs.get(i);
  }

  @Override
  public int size() { return xs.size(); }

  public Optional<Intersection> hit() {
    return xs.stream().filter(i -> i.t >= 0).findFirst();
  }


  /**
   * Represents a single intersection between a Ray and a Sphere.
   */
  static class Intersection {
    private final float t;
    private final Sphere object;

    Intersection(float t, Sphere object) {
      Objects.requireNonNull(object, "Intersection can't be constructed with null object");
      this.t = t;
      this.object = object;
    }

    float getT() { return t; }

    Sphere getObject() { return object; }

    @Override
    public boolean equals(Object o) {
      if (this == o) { return true; }
      if (o == null || getClass() != o.getClass()) { return false; }
      Intersection that = (Intersection) o;
      return Utils.aboutEqual(t, that.t) && Objects.equals(object, that.object);
    }

    @Override
    public int hashCode() {
      return Objects.hash(t, object);
    }

  }
}
