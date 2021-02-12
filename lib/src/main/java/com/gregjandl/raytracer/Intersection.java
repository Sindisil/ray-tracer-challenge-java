package com.gregjandl.raytracer;

public class Intersection {
  private final float t;
  private final Sphere object;

  public Intersection(float t, Sphere object) {
    this.t = t;
    this.object = object;
  }

  public float getT() { return t; }

  public Sphere getObject() { return object; }
}
