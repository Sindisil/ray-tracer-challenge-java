package com.gregjandl.raytracer.rtlib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LightsTest {
  @Test
  @DisplayName("A point light has a position and intensity")
  void testCreatePointLight() {
    var intensity = Color.WHITE;
    var position = new Point(0, 0, 0);
    var light = new PointLight(position, intensity);
    Assertions.assertEquals(position, light.getPosition());
    Assertions.assertEquals(intensity, light.getColor());
  }
}
