package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class IntersectionsTest {
  @Test
  @DisplayName("An intersection encapsulates t and an object")
  void testIntersectionCreate() {
    var s = new Sphere();
    var i = new Intersections.Intersection(3.5f, s);
    assertEquals(3.5f, i.getT());
    assertSame(i.getObject(), s);
  }

  @Test
  @DisplayName("An Intersection may be compared to another Intersection for ordering")
  void testIntersectionCompareTo() {
    var s = new Sphere();
    var i1 = new Intersections.Intersection(3.4f, s);
    var i2 = new Intersections.Intersection(3.4f, s);
    var i3 = new Intersections.Intersection(4.5f, s);
    var i4 = new Intersections.Intersection(5.6f, s);

    assertEquals(Math.signum(i1.compareTo(i3)), -Math.signum(i3.compareTo(i1)));
    assertEquals(i1.compareTo(i2), i2.compareTo(i1));
    assertTrue(i4.compareTo(i3) > 0 && i3.compareTo(i2) > 0 && i4.compareTo(i2) > 0);
    assertTrue(i2.compareTo(i3) < 0 && i3.compareTo(i4) < 0 && i2.compareTo(i4) < 0);
    assertEquals(Math.signum(i1.compareTo(i4)), Math.signum(i2.compareTo(i4)));
  }

  @SuppressWarnings({"EqualsBetweenInconvertibleTypes", "ConstantConditions",
      "SimplifiableAssertion", "EqualsWithItself"})
  @Test
  @DisplayName("Intersections may be compared for equality")
  void testIntersectionEquals() {
    var s = new Sphere();
    var i1 = new Intersections.Intersection(3.4f, s);
    var i2 = new Intersections.Intersection(3.4f, s);
    var i3 = new Intersections.Intersection(4.5f, s);

    assertTrue(i1.equals(i1));
    assertTrue(i1.equals(i2) && i2.equals(i1));
    assertFalse(i1.equals(i3));
    assertFalse(i1.equals("not an Intersection"));
    assertFalse(i1.equals(null));
  }

  @Test
  @DisplayName("hash codes of equal Intersections are equal")
  void testIntersectionHashCode() {
    var s = new Sphere();
    var i1 = new Intersections.Intersection(3.4f, s);
    var i2 = new Intersections.Intersection(3.4f, s);

    assertEquals(i1.hashCode(), i2.hashCode());
  }
}
