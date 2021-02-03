package com.gregjandl.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PointTest {

  @Test
  @DisplayName("A Point may be created from three floats")
  void testPointCreation() {
    var p = new Point(4, -4, 3);
    assertEquals(p.getX(), 4);
    assertEquals(p.getY(), -4);
    assertEquals(p.getZ(), 3);
  }

  @Nested
  @DisplayName("Points may be compared for equality")
  class testEquality {
    final Point a = new Point(5, -3f, 17f);
    final Point b = new Point(5, -3, 17f);
    final Point c = new Point(5, 56, -3f);

    @Test
    @DisplayName("a and b should compare equal to each other, but not to c")
    void testAEqualsBNotC() {
      assertEquals(b, a);
      assertEquals(a, b);
      assertNotEquals(a, c);
      assertNotEquals(c, a);
      assertNotEquals(b, c);
      assertNotEquals(c, b);
    }

    @Test
    @DisplayName("All three components are compared")
    void testAllComponentsCompared() {
      assertNotEquals(new Point(5, -3, 0), a);
      assertNotEquals(new Point(5, 0, 17), a);
      assertNotEquals(new Point(0, -3, 17), a);
    }
  }

  @Test
  @DisplayName("A Vector3 may be added to a Point")
  void testAddPoint() {
    var p = new Point(3, -2, 5);
    var v = new Vector3(-2, 3, 1);
    assertEquals(p.add(v), new Point(1, 1, 6));
  }

  @Test
  @DisplayName("Points may be subtracted")
  void testSubtract() {
    var p1 = new Point(3, 2, 1);
    var p2 = new Point(5, 6, 7);
    assertEquals(new Vector3(-2, -4, -6), p1.subtract(p2));
  }

  @Test
  @DisplayName("A Vector may be subtracted from a Point")
  void testSubtractVector3() {
    var p = new Point(3f, 2f, 1f);
    var v = new Vector3(5f, 6f, 7f);
    assertEquals(new Point(-2f, -4f, -6f), p.subtract(v));
  }

}

