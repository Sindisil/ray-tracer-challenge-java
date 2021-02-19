package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

  @Nested
  @DisplayName("Lighting")
  class TestLighting {
    final Material m = new Material.Builder().build();
    final Point position = new Point(0, 0, 0);
    final Vector3 normal = new Vector3(0, 0, -1);

    @Test
    @DisplayName("Lighting with the eye between the light and the surface")
    void testEyeBetweenLightAndSurface() {
      var eyeVec = new Vector3(0, 0, -1);
      var light = new PointLight(new Point(0, 0, -10));
      var result = m.lighting(light, position, eyeVec, normal);
      assertEquals(new Color(1.9f, 1.9f, 1.9f), result);
    }

    @Test
    @DisplayName("Lighting with eye between light and surface, eye offset 45 deg.")
    void testEyeBetweenLightAndSurface45Deg() {
      var eyeVec = new Vector3(0, Math.sqrt(2) / 2, Math.sqrt(2) / 2);
      var light = new PointLight(new Point(0, 0, -10));
      var result = m.lighting(light, position, eyeVec, normal);
      assertEquals(Color.WHITE, result);
    }

    @Test
    @DisplayName("Lighting with the eye opposite surface, light offset 45 deg.")
    void testLightOffset40Deg() {
      var eyeVec = new Vector3(0, 0, -1);
      var light = new PointLight(new Point(0, 10, -10));
      var result = m.lighting(light, position, eyeVec, normal);
      assertEquals(new Color(0.7364f, 0.7364f, 0.7364f), result);
    }

    @Test
    @DisplayName("Lighting with eye in the path of the reflection vector")
    void testEyeInPathOfReflection() {
      var eyeVec = new Vector3(0, -Math.sqrt(2) / 2, -Math.sqrt(2) / 2);
      var light = new PointLight(new Point(0, 10, -10));
      var result = m.lighting(light, position, eyeVec, normal);
      assertEquals(new Color(1.6364f, 1.6364f, 1.6364f), result);
    }

    @Test
    @DisplayName("Lighting with the light behind the surface")
    void testLightBehindSurface() {
      var eyeVec = new Vector3(0, 0, -1);
      var light = new PointLight(new Point(0, 0, 10));
      var result = m.lighting(light, position, eyeVec, normal);
      assertEquals(new Color(.1f, .1f, .1f), result);
    }
  }

}
