package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MaterialsTest {
  @Test
  @DisplayName("Creating a default Material")
  void testCreateMaterial() {
    var m = new Material.Builder().build();
    assertEquals(Color.WHITE, m.getColor());
    assertTrue(Utils.aboutEqual(0.1, m.getAmbient()));
    assertTrue(Utils.aboutEqual(0.9, m.getDiffuse()));
    assertTrue(Utils.aboutEqual(0.9, m.getSpecular()));
    assertTrue(Utils.aboutEqual(200, m.getShininess()));
  }
}
