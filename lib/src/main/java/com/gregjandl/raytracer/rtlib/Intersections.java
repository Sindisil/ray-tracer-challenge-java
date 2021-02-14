package com.gregjandl.raytracer.rtlib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class Intersections {
  private final List<Intersection> xs;

  Intersections() {
    xs = new ArrayList<>();
  }

  void add(float t, Sphere object) {
    var intersection = new Intersection(t, object);
    int i = Collections.binarySearch(xs, intersection);
    xs.add(i < 0 ? -(i + 1) : i, intersection);
  }

  Intersection get(int i) {
    return xs.get(i);
  }

  int size() { return xs.size(); }

  boolean isEmpty() { return xs.isEmpty(); }

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
