package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AbstractShapeTest {
  @Test
  @DisplayName("shapes have toString()")
  void testToString() {
    assertNotNull(new TestShape().toString());
  }

  @SuppressWarnings({"SimplifiableAssertion", "EqualsBetweenInconvertibleTypes",
      "ConstantConditions", "EqualsWithItself"})
  @Test
  @DisplayName("Shapes may be compared for equality, which is distinct from identity")
  void testEquals() {
    var s1 = new TestShape();
    var s2 = new TestShape();
    var s3 = new TestShape();
    var s4 = new TestShape();
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
    assertFalse(s4.equals("not a TestShape"));
  }

  @Test
  @DisplayName("A shape has a default identity transformation")
  void testDefaultTransformation() {
    Shape s = new TestShape();
    assertEquals(Matrix4x4.identity(), s.getTransform());
  }

  @Test
  @DisplayName("An shape's transformation matrix may be set to a non-null Matrix4x4")
  void testSetTransform() {
    Shape s = new TestShape();
    assertEquals(Matrix4x4.identity(), s.getTransform());
    var t = Matrix4x4.translation(2, 3, 4);
    var s2 = s.setTransform(t);
    assertSame(s, s2);
    assertEquals(t, s.getTransform());
    assertThrows(NullPointerException.class, () -> s.setTransform(null));
  }

  @Test
  @DisplayName("A shape has a default material and may be assigned a new one")
  void testCreateShape() {
    Shape s = new TestShape();
    var m = new Material.Builder().build();
    assertEquals(m, s.getMaterial());
    var m2 = new Material.Builder().ambient(1).build();
    var s2 = s.setMaterial(m2);
    assertSame(s, s2);
    assertEquals(m2, s.getMaterial());
  }

  @Test
  @DisplayName("Intersecting a scaled shape with a ray")
  void testIntersectScaled() {
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = new TestShape();
    s.setTransform(Matrix4x4.scaling(2, 2, 2));
    s.intersects(r); // only care about side effect of saving localRay
    assertEquals(new Point(0, 0, -2.5f), s.localRay.getOrigin());
    assertEquals(new Vector3(0, 0, 0.5f), s.localRay.getDirection());
  }

  @Test
  @DisplayName("Intersecting a translated shape with a ray")
  void testIntersectTranslated() {
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = new TestShape();
    s.setTransform(Matrix4x4.translation(5, 0, 0));
    s.intersects(r); // only care about side effect of saving localRay
    assertEquals(new Point(-5, 0, -5), s.localRay.getOrigin());
    assertEquals(new Vector3(0, 0, 1), s.localRay.getDirection());
  }

  static class TestShape extends AbstractShape<TestShape> implements Shape {
    Ray localRay;

    @Override
    protected TestShape getThis() {
      return this;
    }

    @Override
    protected IntersectionList localIntersect(Ray r, IntersectionList xs) {
      localRay = r;
      return xs;
    }

    @Override
    protected Vector3 localNormalAt(Point localPoint) {
      return new Vector3(localPoint.getX(), localPoint.getY(), localPoint.getZ());
    }
  }

}
