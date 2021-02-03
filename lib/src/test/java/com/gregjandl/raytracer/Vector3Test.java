package com.gregjandl.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class Vector3Test {
  @Test
  @DisplayName("A Vector3 may be constructed from three floats")
  void testVector3Creation() {
    Vector3 v = new Vector3(4, -4, 3);
    assertEquals(v.getX(), 4);
    assertEquals(v.getY(), -4);
    assertEquals(v.getZ(), 3);

  }

  @Nested
  @DisplayName("Vector3s may be compared for equality")
  class testEquality {
    final Vector3 a = new Vector3(5.0f, -3.0f, 17.0f);
    final Vector3 b = new Vector3(5.0f, -3.0f, 17.0f);
    final Vector3 c = new Vector3(5.0f, 56.0f, -3.0f);

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
      assertNotEquals(new Vector3(5.0f, -3.0f, 0.0f), a);
      assertNotEquals(new Vector3(5.0f, 0.0f, 17.0f), a);
      assertNotEquals(new Vector3(0.0f, -3.0f, 17.0f), a);
    }
  }

  @Test
  @DisplayName("Two Vector3s may be subtracted")
  void testSubtract() {
    var v1 = new Vector3(3.0f, 2.0f, 1.0f);
    var v2 = new Vector3(5.0f, 6.0f, 7.0f);
    assertEquals(v1.subtract(v2), new Vector3(-2.0f, -4.0f, -6.0f));
  }

  @Test
  @DisplayName("Two Vector3s may be added together")
  void testAddVector3() {
    var v1 = new Vector3(-2, 3, 1);
    var v2 = new Vector3(2, 1.2f, -6.7f);
    assertEquals(new Vector3(0, 4.2f, -5.7f), v1.add(v2));
  }

  @Test
  @DisplayName("A Point may be added to a Vector3")
  void testAddPoint() {
    var p = new Point(3, -2, 5);
    var v = new Vector3(-2, 3, 1);
    assertEquals(v.add(p), new Point(1, 1, 6));
  }

}
