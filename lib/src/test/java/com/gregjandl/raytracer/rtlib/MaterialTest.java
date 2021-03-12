package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

  @SuppressWarnings({"SimplifiableAssertion", "EqualsWithItself", "ConstantConditions",
      "EqualsBetweenInconvertibleTypes"})
  @Test
  @DisplayName("Materials may be compared for equality")
  void testEquals() {
    var m1 = new Material.Builder()
        .ambient(1).color(Color.BLUE).diffuse(1).shininess(1).specular(1)
        .build();
    var m2 = new Material.Builder(m1).build();
    var mAmb = new Material.Builder(m1).ambient(.5f).build();
    var mCol = new Material.Builder(m1).color(Color.GREEN).build();
    var mDif = new Material.Builder(m1).diffuse(.5f).build();
    var mShi = new Material.Builder(m1).shininess(.5f).build();
    var mSpe = new Material.Builder(m1).specular(.5f).build();

    assertTrue(m1.equals(m1)); // self
    assertTrue(m1.equals(m2)); // equal
    assertTrue(m2.equals(m1)); // symmetry
    assertFalse(m1.equals(mAmb)); // differ
    assertFalse(mAmb.equals(m1)); // symmetry
    assertFalse(m1.equals(mCol)); // differ
    assertFalse(m1.equals(mDif)); // differ
    assertFalse(m1.equals(mShi)); // differ
    assertFalse(m1.equals(mSpe)); // differ
    assertFalse(m1.equals(null)); // null
    assertFalse(m1.equals("not a material")); // different type
  }

  @Test
  @DisplayName("Materials have toString()")
  void testToString() {
    assertNotNull(new Material.Builder().build().toString());
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
      var result = m.lighting(light, position, eyeVec, normal, false);
      assertEquals(new Color(1.9f, 1.9f, 1.9f), result);
    }

    @Test
    @DisplayName("Lighting with eye between light and surface, eye offset 45 deg.")
    void testEyeBetweenLightAndSurface45Deg() {
      var eyeVec = new Vector3(0, Math.sqrt(2) / 2, Math.sqrt(2) / 2);
      var light = new PointLight(new Point(0, 0, -10));
      var result = m.lighting(light, position, eyeVec, normal, false);
      assertEquals(Color.WHITE, result);
    }

    @Test
    @DisplayName("Lighting with the eye opposite surface, light offset 45 deg.")
    void testLightOffset40Deg() {
      var eyeVec = new Vector3(0, 0, -1);
      var light = new PointLight(new Point(0, 10, -10));
      var result = m.lighting(light, position, eyeVec, normal, false);
      assertEquals(new Color(0.7364f, 0.7364f, 0.7364f), result);
    }

    @Test
    @DisplayName("Lighting with eye in the path of the reflection vector")
    void testEyeInPathOfReflection() {
      var eyeVec = new Vector3(0, -Math.sqrt(2) / 2, -Math.sqrt(2) / 2);
      var light = new PointLight(new Point(0, 10, -10));
      var result = m.lighting(light, position, eyeVec, normal, false);
      assertEquals(new Color(1.6364f, 1.6364f, 1.6364f), result);
    }

    @Test
    @DisplayName("Lighting with the light behind the surface")
    void testLightBehindSurface() {
      var eyeVec = new Vector3(0, 0, -1);
      var light = new PointLight(new Point(0, 0, 10));
      var result = m.lighting(light, position, eyeVec, normal, false);
      assertEquals(new Color(.1f, .1f, .1f), result);
    }

    @Test
    @DisplayName("Lighting with the surface in shadow")
    void testLightWithShadow() {
      var eyeVec = new Vector3(0, 0, -1);
      var light = new PointLight(new Point(0, 0, -10));
      var result = m.lighting(light, position, eyeVec, normal, true);
      assertEquals(new Color(0.1f, 0.1f, 0.1f), result);
    }
  }

}
