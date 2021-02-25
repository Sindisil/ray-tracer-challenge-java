package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SphereTest {
  @Test
  @DisplayName("Spheres have toString()")
  void testToString() {
    assertNotNull(new Sphere().toString());
  }

  @SuppressWarnings({"SimplifiableAssertion", "EqualsBetweenInconvertibleTypes",
      "ConstantConditions", "EqualsWithItself"})
  @Test
  @DisplayName("Spheres may be compared for equality, which is distinct from identity")
  void testEquals() {
    var s1 = new Sphere();
    var s2 = new Sphere();
    var s3 = new Sphere();
    var s4 = new Sphere();
    var m1 = new Material.Builder().build();
    var m2 = new Material.Builder(m1).color(Color.BLUE).build();
    var t1 = Matrix4x4.scaling(2, 2, 2);

    s1.setMaterial(m1);
    s2.setMaterial(m2);
    s3.setMaterial(m2);
    s4.setMaterial(m2).setTransform(t1);

    assertTrue(s4.equals(s4)); // equals self
    assertSame(s4, s4);
    assertTrue(s2.equals(s3)); // equals
    assertTrue(s3.equals(s2)); // symmetry
    assertNotSame(s2, s3); // not same
    assertFalse(s1.equals(s2)); // differ
    assertFalse(s3.equals(s4)); // differ
    assertFalse(s2.equals(s1)); // symmetry
    assertFalse(s3.equals(s4)); // symmetry
    assertFalse(s4.equals(null));
    assertFalse(s4.equals("not a sphere"));
  }

  @Test
  @DisplayName("A ray intersects a sphere at two points")
  void testRayIntersectsAtTwoPoints() {
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(2, xs.size());
    assertTrue(Utils.aboutEqual(xs.get(0).getT(), 4));
    assertTrue(Utils.aboutEqual(xs.get(1).getT(), 6));
  }

  @Test
  @DisplayName("A ray intersects a sphere at a tangent")
  void testRayIntersectsAtATangent() {
    var r = new Ray(new Point(0, 1, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(2, xs.size());
    assertTrue(Utils.aboutEqual(xs.get(0).getT(), 5));
    assertTrue(Utils.aboutEqual(xs.get(1).getT(), 5));
  }

  @Test
  @DisplayName("A ray misses a sphere")
  void testRayMisses() {
    var r = new Ray(new Point(0, 2, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertTrue(xs.isEmpty());
  }

  @Test
  @DisplayName("A ray originates inside a sphere")
  void testRayFromInside() {
    var r = new Ray(new Point(0, 0, 0), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(2, xs.size());
    assertTrue(Utils.aboutEqual(xs.get(0).getT(), -1));
    assertTrue(Utils.aboutEqual(xs.get(1).getT(), 1));
  }

  @Test
  @DisplayName("A sphere is behind a ray")
  void testSphereBehindRay() {
    var r = new Ray(new Point(0, 0, 5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(2, xs.size());
    assertTrue(Utils.aboutEqual(xs.get(0).getT(), -6));
    assertTrue(Utils.aboutEqual(xs.get(1).getT(), -4));
  }

  @Test
  @DisplayName("Intersect sets the object of the Intersection")
  void testIntersectionObject() {
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var xs = s.intersects(r);
    assertEquals(2, xs.size());
    assertSame(xs.get(0).getObject(), s);
    assertSame(xs.get(1).getObject(), s);
  }

  @Test
  @DisplayName("A Sphere has a default identity transformation")
  void testDefaultTransformation() {
    var s = new Sphere();
    assertEquals(Matrix4x4.identity(), s.getTransform());
  }

  @Test
  @DisplayName("A Sphere's transformation matrix may be set to a non-null Matrix4x4")
  void testSetTransform() {
    var s = new Sphere();
    assertEquals(Matrix4x4.identity(), s.getTransform());
    var t = Matrix4x4.translation(2, 3, 4);
    s.setTransform(t);
    assertEquals(t, s.getTransform());
    assertThrows(NullPointerException.class, () -> s.setTransform(null));
  }

  @Test
  @DisplayName("Intersecting a scaled sphere with a ray")
  void testIntersectScaled() {
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    s.setTransform(Matrix4x4.scaling(2, 2, 2));
    var xs = s.intersects(r);
    assertEquals(2, xs.size());
    assertEquals(3, xs.get(0).getT());
    assertEquals(7, xs.get(1).getT());
  }

  @Test
  @DisplayName("Intersecting a translated sphere with a ray")
  void testIntersectTranslated() {
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    s.setTransform(Matrix4x4.translation(5, 0, 0));
    var xs = s.intersects(r);
    assertTrue(xs.isEmpty());
  }

  @Test
  @DisplayName("A sphere has a default material and may be assigned a new one")
  void testCreateSphere() {
    var s = new Sphere();
    var m = new Material.Builder().build();
    assertEquals(m, s.getMaterial());
    var m2 = new Material.Builder().ambient(1).build();
    s.setMaterial(m2);
    assertEquals(m2, s.getMaterial());
  }

  @Nested
  @DisplayName("Normals on a sphere")
  class TestNormals {
    final Sphere sphere = new Sphere();

    @Test
    @DisplayName("The normal on a sphere at a point on the X axis")
    void testNormalOnXAxis() {
      var n = sphere.normalAt(new Point(1, 0, 0));
      assertEquals(new Vector3(1, 0, 0), n);
    }

    @Test
    @DisplayName("The normal on a sphere at a point on the Y axis")
    void testNormalOnYAxis() {
      var n = sphere.normalAt(new Point(0, 1, 0));
      assertEquals(new Vector3(0, 1, 0), n);
    }

    @Test
    @DisplayName("The normal on a sphere at a point on the Z axis")
    void testNormalOnZAxis() {
      var n = sphere.normalAt(new Point(0, 0, 1));
      assertEquals(new Vector3(0, 0, 1), n);
    }

    @Test
    @DisplayName("The normal on a sphere at a point on a nonaxial point")
    void testNormalOnNonaxialPoint() {
      var n = sphere.normalAt(new Point(Math.sqrt(3) / 3, Math.sqrt(3) / 3, Math.sqrt(3) / 3));
      assertEquals(new Vector3(Math.sqrt(3) / 3, Math.sqrt(3) / 3, Math.sqrt(3) / 3), n);
    }

    @Test
    @DisplayName("The normal is a normalized vector")
    void testNormalIsNormalized() {
      var n = sphere.normalAt(new Point(Math.sqrt(3) / 3, Math.sqrt(3) / 3, Math.sqrt(3) / 3));
      assertEquals(n.normalize(), n);
    }

    @Test
    @DisplayName("Normal on a translated sphere")
    void testNormalOnTranslatedSphere() {
      sphere.setTransform(Matrix4x4.translation(0, 1, 0));
      var n = sphere.normalAt(new Point(0, 1.70711f, -0.70711f));
      assertEquals(new Vector3(0, 0.70711f, -0.70711f), n);
    }

    @Test
    @DisplayName("Normal on a scaled and rotated sphere")
    void testNormalOnTransformedSphere() {
      sphere.setTransform(Matrix4x4.rotationOnZ(Math.PI / 5).scale(1, 0.5f, 1));
      var n = sphere.normalAt(new Point(0, Math.sqrt(2) / 2, -Math.sqrt(2) / 2));
      assertEquals(new Vector3(0, 0.97014f, -0.24254f), n);
    }
  }

}
