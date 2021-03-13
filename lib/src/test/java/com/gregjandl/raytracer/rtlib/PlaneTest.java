package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PlaneTest {
  @SuppressWarnings("ConstantConditions")
  @Test
  @DisplayName("A plane is a shape")
  void testPlaneIsShape() {
    assertTrue(new Plane() instanceof  Shape);
  }

  @Test
  @DisplayName("A plane has a toString")
  void testToString() {
    var s = new Plane().toString();
    assertNotNull(s);
  }

  @Test
  @DisplayName("The normal of a plane is constant everywhere")
  void testNormalOfPlane() {
    var p = new Plane();
    var n1 = p.localNormalAt(new Point(0, 0, 0));
    var n2 = p.localNormalAt(new Point(10, 0, -10));
    var n3 = p.localNormalAt(new Point(-5, 0, 150));
    assertEquals(new Vector3(0, 1, 0), n1);
    assertEquals(new Vector3(0, 1, 0), n2);
    assertEquals(new Vector3(0, 1, 0), n3
    );
  }

  @Nested
  class IntersectionsTest {
    final Plane p = new Plane();
    @Test
    @DisplayName("Intersect with a ray parallel to the plane")
    void testIntersectRayParallel() {
      var r = new Ray(new Point(0, 10, 0), new Vector3(0, 0, 1));
      var xs = p.localIntersect(r, new IntersectionList());
      assertTrue(xs.isEmpty());
    }

    @Test
    @DisplayName("Intersect with a coplanar ray")
    void testIntersectCoplanarRay() {
      var r = new Ray(new Point(0, 0, 0), new Vector3(0, 0, 1));
      var xs = p.localIntersect(r, new IntersectionList());
      assertTrue(xs.isEmpty());
    }

    @Test
    @DisplayName("Intersecting from above")
    void testIntersectFromAbove() {
      var r = new Ray(new Point(0, 1, 0), new Vector3(0, -1, 0));
      var xs = p.localIntersect(r, new IntersectionList());
      assertEquals(1, xs.size());
      assertEquals(1, xs.get(0).getT());
      assertEquals(p, xs.get(0).getObject());
    }

    @Test
    @DisplayName("Intersecting from below")
    void testIntersectFromBelow() {
      var r = new Ray(new Point(0, -1, 0), new Vector3(0, 1, 0));
      var xs = p.localIntersect(r, new IntersectionList());
      assertEquals(1, xs.size());
      assertEquals(1, xs.get(0).getT());
      assertEquals(p, xs.get(0).getObject());
    }
  }


}
