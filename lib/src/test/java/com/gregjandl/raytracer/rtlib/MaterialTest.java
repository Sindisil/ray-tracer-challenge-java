package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MaterialTest {
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

  @Test
  @DisplayName("Create a custom material")
  void testCreateCustomMaterial() {
    var m = new Material.Builder()
        .color(Color.BLUE).ambient(.2f).diffuse(.3f).specular(.4f).shininess(100)
        .build();
    assertEquals(Color.BLUE, m.getColor());
    assertTrue(Utils.aboutEqual(0.2, m.getAmbient()));
    assertTrue(Utils.aboutEqual(0.3, m.getDiffuse()));
    assertTrue(Utils.aboutEqual(0.4, m.getSpecular()));
    assertTrue(Utils.aboutEqual(100, m.getShininess()));
  }
}
