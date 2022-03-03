package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PointLightTest {
  @Test
  @DisplayName("A point light has a position and intensity")
  void testCreatePointLight() {
    var intensity = Color.WHITE;
    var position = new Point(0, 0, 0);
    var light = new PointLight(position, intensity);
    assertEquals(position, light.getPosition());
    assertEquals(intensity, light.getColor());
  }

  @Test
  @DisplayName("A convenience constructor for White point lights")
  void testCreateWhitePointLight() {
    var position = new Point(0, 0, 0);
    var light = new PointLight(position);
    assertEquals(position, light.getPosition());
    assertEquals(Color.WHITE, light.getColor());
  }

  @Test
  @DisplayName("PointLights have toString")
  void testToString() {
    assertNotNull(new PointLight(new Point(0, 0, 0)).toString());
  }

  @Test
  @DisplayName("Hash codes are equal if PointLights are equal")
  void testHashCode() {
    var a = new PointLight(new Point(1, 1, 1));
    var b = new PointLight(new Point(1, 1, 1));
    assertTrue(a.equals(b) && a.hashCode() == b.hashCode());
  }

  @Test
  @DisplayName("PointLights may be compared for equality")
  void testEquals() {
    var p1 = new PointLight(new Point(1, 1, 1));
    var p2 = new PointLight(new Point(1, 1, 1));
    var p3 = new PointLight(new Point(2, 2, 2), Color.BLUE);
    var pCol = new PointLight(new Point(1, 1, 1), Color.BLUE);
    var pPoint = new PointLight(new Point(2, 2, 2));

    assertTrue(p1.equals(p1)); // self
    assertTrue(p1.equals(p2)); // equal
    assertTrue(p2.equals(p1)); // symmetry
    assertFalse(p2.equals(p3)); // differ
    assertFalse(p3.equals(p2)); // symmetry
    assertFalse(p2.equals(pCol)); // differ color
    assertFalse(p2.equals(pPoint)); // differ point
    assertFalse(p2.equals(null)); // null
    assertFalse(p2.equals("not a point light")); // different class
  }
}
