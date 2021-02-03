package com.gregjandl.raytracer;

import java.util.Objects;

public class Vector3 {
  private final float x;
  private final float y;
  private final float z;

  public Vector3(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public float getX() { return x;}

  public float getY() { return y;}

  public float getZ() { return z;}

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    var vector3 = (Vector3) o;
    return Utils.aboutEqual(x, vector3.getX()) && Utils.aboutEqual(y, vector3.getY())
        && Utils.aboutEqual(z, vector3.getZ());
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y, z);
  }

  public Vector3 subtract(Vector3 other) {
    return new Vector3(x - other.getX(), y - other.getY(), z - other.getZ());
  }

  public Vector3 add(Vector3 other) {
    return new Vector3(x + other.getX(), y + other.getY(), z + other.getZ());
  }

  public Point add(Point p) {
    return new Point(x + p.getX(), y + p.getY(), z + p.getZ());
  }

  public Vector3 negate() {
    return new Vector3(-x, -y, -z);
  }

  public Vector3 multiply(float n) {
    return new Vector3(x * n, y * n, z * n);
  }

  public Vector3 divide(float n) {
    return new Vector3(x / n, y / n, z / n);
  }

  public double magnitude() {
    return Math.sqrt(x * x + y * y + z * z);
  }
}
