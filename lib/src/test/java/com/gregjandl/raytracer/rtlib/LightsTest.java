package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LightsTest {
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
}
