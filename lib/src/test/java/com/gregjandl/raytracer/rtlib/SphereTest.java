package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    assertEquals(2, xs.size());
    assertTrue(Utils.aboutEqual(xs.get(0).getT(), 4));
    assertTrue(Utils.aboutEqual(xs.get(1).getT(), 6));
  }

  @Test
  @DisplayName("A ray intersects a sphere at a tangent")
  void testRayIntersectsAtATangent() {
    var r = new Ray(new Point(0, 1, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(2, xs.size());
    assertTrue(Utils.aboutEqual(xs.get(0).getT(), 5));
    assertTrue(Utils.aboutEqual(xs.get(1).getT(), 5));
  }

  @Test
  @DisplayName("A ray misses a sphere")
  void testRayMisses() {
    var r = new Ray(new Point(0, 2, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertTrue(xs.isEmpty());
  }

  @Test
  @DisplayName("A ray originates inside a sphere")
  void testRayFromInside() {
    var r = new Ray(new Point(0, 0, 0), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(2, xs.size());
    assertTrue(Utils.aboutEqual(xs.get(0).getT(), -1));
    assertTrue(Utils.aboutEqual(xs.get(1).getT(), 1));
  }

  @Test
  @DisplayName("A sphere is behind a ray")
  void testSphereBehindRay() {
    var r = new Ray(new Point(0, 0, 5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(2, xs.size());
    assertTrue(Utils.aboutEqual(xs.get(0).getT(), -6));
    assertTrue(Utils.aboutEqual(xs.get(1).getT(), -4));
  }

  @Test
  @DisplayName("Intersect sets the object of the Intersection")
  void testIntersectionObject() {
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(2, xs.size());
    assertSame(xs.get(0).getObject(), s);
    assertSame(xs.get(1).getObject(), s);
  }

  @Test
  @DisplayName("A Sphere has a default identity transformation")
  void testDefaultTransformation() {
    var s = new Sphere();
    assertEquals(Matrix4x4.identity(), s.getTransform());
  }

  @Test
  @DisplayName("A Sphere's transformation matrix may be set to a non-null Matrix4x4")
  void testSetTransform() {
    var s = new Sphere();
    assertEquals(Matrix4x4.identity(), s.getTransform());
    var t = Matrix4x4.translation(2, 3, 4);
    s.setTransform(t);
    assertEquals(t, s.getTransform());
    assertThrows(NullPointerException.class, () -> s.setTransform(null));
  }

  @Test
  @DisplayName("Intersecting a scaled sphere with a ray")
  void testIntersectScaled() {
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    s.setTransform(Matrix4x4.scaling(2, 2, 2));
    var xs = s.intersects(r);
    assertEquals(2, xs.size());
    assertEquals(3, xs.get(0).getT());
    assertEquals(7, xs.get(1).getT());
  }

  @Test
  @DisplayName("Intersecting a translated sphere with a ray")
  void testIntersectTranslated() {
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    s.setTransform(Matrix4x4.translation(5, 0, 0));
    var xs = s.intersects(r);
    assertTrue(xs.isEmpty());
  }
}
