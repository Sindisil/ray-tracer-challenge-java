package com.gregjandl.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SphereTest {
  @Test
  @DisplayName("A ray intersects a sphere at two points")
  void testRayIntersectsAtTwoPoints() {
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(2, xs.length);
    assertTrue(Utils.aboutEqual(xs[0], 4));
    assertTrue(Utils.aboutEqual(xs[1], 6));
  }

  @Test
  @DisplayName("A ray intersects a sphere at a tangent")
  void testRayIntersectsAtATangent() {
    var r = new Ray(new Point(0, 1, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(2, xs.length);
    assertTrue(Utils.aboutEqual(xs[0], 5));
    assertTrue(Utils.aboutEqual(xs[1], 5));
  }

  @Test
  @DisplayName("A ray misses a sphere")
  void testRayMisses() {
    var r = new Ray(new Point(0, 2, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(0, xs.length);
  }

  @Test
  @DisplayName("A ray originates inside a sphere")
  void testRayFromInside() {
    var r = new Ray(new Point(0, 0, 0), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(xs.length, 2);
    assertTrue(Utils.aboutEqual(xs[0], -1));
    assertTrue(Utils.aboutEqual(xs[1], 1));
  }

  @Test
  @DisplayName("A sphere is behind a ray")
  void testSphereBehindRay() {
    var r = new Ray(new Point(0, 0, 5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(xs.length, 2);
    assertTrue(Utils.aboutEqual(xs[0], -6));
    assertTrue(Utils.aboutEqual(xs[1], -4));
  }
}
