package com.gregjandl.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IntersectionTest {
  @Test
  @DisplayName("An intersection encapsulates t and an object")
  void testCreate() {
    var s = new Sphere();
    var i = new Intersection(3.5f, s);
    assertEquals(3.5f, i.getT());
    assertSame(i.getObject(), s);
  }
}
