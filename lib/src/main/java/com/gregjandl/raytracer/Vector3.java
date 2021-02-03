package com.gregjandl.raytracer;

import java.util.Objects;

public class Vector3 {
  private final float x;
  private final float y;
  private final float z;

  @Override
  public String toString() {
    return "Vector3{x=" + x + ", y=" + y + ", z=" + z + '}';
  }

  public Vector3(float x, float y, float z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public Vector3(double x, double y, double z) {
    this.x = (float) x;
    this.y = (float) y;
    this.z = (float) z;
  }

  public float getX() { return x;}

  public float getY() { return y;}

  public float getZ() { return z;}

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    var vector3 = (Vector3) o;
    return Utils.aboutEqual(x, vector3.x) && Utils.aboutEqual(y, vector3.y)
        && Utils.aboutEqual(z, vector3.z);
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

  public float magnitude() {
    return (float) Math.sqrt(x * x + y * y + z * z);
  }

  public Vector3 normalize() {
    if (magnitude() == 0) {
      throw new ArithmeticException("Can't normalize Vector3(0,0,0)");
    }
    var m = magnitude();
    return new Vector3(x / m, y / m, z / m);
  }

  public float dot(Vector3 v) {
    return x * v.x + y * v.y + z * v.z;
  }

  public Vector3 cross(Vector3 v) {
    return new Vector3(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
  }
}
