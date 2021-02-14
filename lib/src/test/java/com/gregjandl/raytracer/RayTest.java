package com.gregjandl.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RayTest {
  @Test
  @DisplayName("Creating and querying a ray")
  void testCreateAndQuery() {
    var origin = new Point(1, 2, 3);
    var direction = new Vector3(4, 5, 6);
    var r1 = new Ray(origin, direction);
    assertEquals(origin, r1.getOrigin());
    assertEquals(direction, r1.getDirection());
  }

  @SuppressWarnings({"SimplifiableAssertion", "EqualsWithItself",
      "EqualsBetweenInconvertibleTypes", "ConstantConditions"})
  @Test
  @DisplayName("Comparing Rays for equality")
  void testEquals() {
    var r1 = new Ray(new Point(1, 2, 3), new Vector3(4, 5, 6));
    var r2 = new Ray(new Point(1, 2, 3), new Vector3(4, 5, 6));
    var r3 = new Ray(new Point(3, 2, 1), new Vector3(4, 5, 6));
    var r4 = new Ray(new Point(1, 2, 3), new Vector3(6, 5, 4));
    var r5 = new Ray(new Point(3, 2, 1), new Vector3(6, 5, 4));

    assertTrue(r1.equals(r1));
    assertTrue(r1.equals(r2) && r2.equals(r1));
    assertFalse(r1.equals(r3));
    assertFalse(r1.equals(r4));
    assertFalse(r1.equals(r5));
    assertFalse(r1.equals("not a Ray"));
    assertFalse(r1.equals(null));
  }

  @Test
  @DisplayName("hash codes are equal if objects are equal")
  void testHashCode() {
    var r1 = new Ray(new Point(1, 2, 3), new Vector3(4, 5, 6));
    var r2 = new Ray(new Point(1, 2, 3), new Vector3(4, 5, 6));
    assertEquals(r1.hashCode(), r2.hashCode());
  }

  @Test
  @DisplayName("Computing a point from a distance")
  void testPosition() {
    var r = new Ray(new Point(2, 3, 4), new Vector3(1, 0, 0));
    assertEquals(new Point(2, 3, 4), r.getPosition(0));
    assertEquals(new Point(3, 3, 4), r.getPosition(1));
    assertEquals(new Point(1, 3, 4), r.getPosition(-1));
    assertEquals(new Point(4.5, 3, 4), r.getPosition(2.5f));
  }

  @Test
  @DisplayName("Translating a ray")
  void testTranslate() {
    var r = new Ray(new Point(1, 2, 3), new Vector3(0, 1, 0));
    var translated = r.transform(Matrix4x4.translation(3, 4, 5));
    assertEquals(new Point(4, 6, 8), translated.getOrigin());
    assertEquals(new Vector3(0, 1, 0), translated.getDirection());
  }

  @Test
  @DisplayName("Scaling a ray")
  void testScaling() {
    var r = new Ray(new Point(1, 2, 3), new Vector3(0, 1, 0));
    var scaled = r.transform(Matrix4x4.scaling(2, 3, 4));
    assertEquals(new Point(2, 6, 12), scaled.getOrigin());
    assertEquals(new Vector3(0, 3, 0), scaled.getDirection());
  }
}
