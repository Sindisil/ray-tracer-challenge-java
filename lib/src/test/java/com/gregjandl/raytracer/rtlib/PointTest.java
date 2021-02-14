package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

  @Test
  @DisplayName("Point has toString")
  void testToString() {
    var expected = "Point{x=" + 1.1 + ", y=" + 2.2 + ", z=" + 3.3 + '}';
    var actual = new Point(1.1f, 2.2f, 3.3f).toString();
    assertEquals(expected, actual);
  }

  @Nested
  @DisplayName("Points may be compared for equality")
  class testEquality {
    final Point a = new Point(5, -3f, 17f);
    final Point b = new Point(5, -3, 17f);
    final Point c = new Point(5, 56, -3f);

    @SuppressWarnings({"SimplifiableAssertion", "ConstantConditions",
        "EqualsBetweenInconvertibleTypes", "EqualsWithItself"})
    @Test
    @DisplayName("a and b should compare equal to each other, but not to c")
    void testAEqualsBNotC() {
      assertTrue(a.equals(b)); // equals
      assertTrue(b.equals(a)); // symmetry
      assertFalse(a.equals(c)); // differ
      assertFalse(c.equals(a)); // symmetry
      assertFalse(b.equals(c)); // differ
      assertFalse(c.equals(b)); // symmetry
      assertTrue(a.equals(a)); // equals self
      assertFalse(a.equals("not a Point")); // differ by class
      assertFalse(a.equals(null)); // not equal null
    }

    @Test
    @DisplayName("hash codes are equal if objects are equal")
    void testHashCode() {
      var a = new Point(1, 2, 3);
      var b = new Point(1, 2, 3);
      assertEquals(a.hashCode(), b.hashCode());
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
