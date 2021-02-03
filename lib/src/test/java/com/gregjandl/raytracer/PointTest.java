package com.gregjandl.raytracer;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PointTest {

  @Test
  @DisplayName("A Point may be created from three floats")
  void testPointCreation() {
    Point p = new Point(4, -4, 3);
    assertEquals(p.getX(), 4);
    assertEquals(p.getY(), -4);
    assertEquals(p.getZ(), 3);
  }

  @Nested
  @DisplayName("Points may be compared for equality")
  class testEquality {
    Point a = new Point(5.0f, -3.0f, 17.0f);
    Point b = new Point(5.0f, -3.0f, 17.0f);
    Point c = new Point(5.0f, 56.0f, -3.0f);

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
      assertNotEquals(new Point(5.0f, -3.0f, 0.0f), a);
      assertNotEquals(new Point(5.0f, 0.0f, 17.0f), a);
      assertNotEquals(new Point(0.0f, -3.0f, 17.0f), a);
    }
  }
}
