package com.gregjandl.raytracer.rtlib;

import com.gregjandl.raytracer.rtlib.Intersections.Intersection;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

class Intersections extends AbstractList<Intersection> {
  private final List<Intersection> xs;

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

  static class Intersection implements Comparable<Intersection> {
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

    /**
     * Compares this {@code Intersection} with the specified {@code Intersection} for order. Returns
     * a negative integer, zero, or a positive integer as this {@code Intersection} is less than,
     * equal to, or greater than the specified {@code Intersection}.
     *
     * <p> Note: this class has a natural ordering that is inconsistent with equals -- only
     * {@code t} is compared for ordering.
     *
     * @param o the {@code Intersection} to be compared.
     * @return a negative integer, zero, or a positive integer as this {@code Intersection} is less
     * than, equal to, or greater than the specified {@code Intersection}.
     * @throws NullPointerException if the specified {@code Intersection} is null
     */
    @Override
    public int compareTo(Intersection o) {
      return Float.compare(t, Objects.requireNonNull(o, "Intersection was null").t);
    }

  }
}
