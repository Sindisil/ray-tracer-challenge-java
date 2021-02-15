package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
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
    var s1 = new Sphere();
    var s2 = new Sphere();
    var i1 = new Intersections.Intersection(3.4f, s1);
    var i2 = new Intersections.Intersection(3.4f, s1);
    var i3 = new Intersections.Intersection(4.5f, s1);
    var i4 = new Intersections.Intersection(4.5f, s2);

    assertTrue(i1.equals(i1));
    assertTrue(i1.equals(i2) && i2.equals(i1));
    assertFalse(i1.equals(i3));
    assertFalse(i3.equals(i4));
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

  @Test
  @DisplayName("An Intersections collection may be asked if it contains a specified Intersection")
  void testContains() {
    var s = new Sphere();
    var i1 = new Intersections.Intersection(1, s);
    var i2 = new Intersections.Intersection(2, s);
    var i3 = new Intersections.Intersection(-1, s);
    var xs = new Intersections();
    xs.addAll(List.of(i1, i2));
    assertTrue(xs.contains(i1));
    assertFalse(xs.contains(i3));
  }

  @Test
  @DisplayName("An Intersections container may be iterated over")
  void testIteration() {
    var s = new Sphere();
    var i1 = new Intersections.Intersection(5, s);
    var i2 = new Intersections.Intersection(7, s);
    var i3 = new Intersections.Intersection(-3, s);
    var i4 = new Intersections.Intersection(2, s);
    var xs = new Intersections();
    xs.addAll(List.of(i1, i2, i3, i4));
    for (var i : xs) {
      assertNotNull(i);
    }
  }

  @Test
  @DisplayName("The hit, when all intersections have positive t")
  void testHitWithAllPositiveT() {
    var s = new Sphere();
    var i1 = new Intersections.Intersection(1, s);
    var i2 = new Intersections.Intersection(2, s);
    var xs = new Intersections();
    xs.addAll(List.of(i1, i2));
    var i = xs.hit();
    assertEquals(i1, i.orElseThrow());
  }

  @Test
  @DisplayName("The hit, when some intersections have negative t")
  void testHitWithSomeNegativeT() {
    var s = new Sphere();
    var i1 = new Intersections.Intersection(-1, s);
    var i2 = new Intersections.Intersection(1, s);
    var xs = new Intersections();
    xs.addAll(List.of(i1, i2));
    var i = xs.hit();
    assertEquals(i2, i.orElseThrow());
  }

  @Test
  @DisplayName("The hit, when all intersections have negative t")
  void testHitWithAllNegativeT() {
    var s = new Sphere();
    var i1 = new Intersections.Intersection(-2, s);
    var i2 = new Intersections.Intersection(-1, s);
    var xs = new Intersections();
    xs.addAll(List.of(i1, i2));
    var i = xs.hit();
    assertTrue(i.isEmpty());
  }

  @Test
  @DisplayName("The hit is always the lowest non-negative Intersection")
  void testHitAlwaysLowest() {
    var s = new Sphere();
    var i1 = new Intersections.Intersection(5, s);
    var i2 = new Intersections.Intersection(7, s);
    var i3 = new Intersections.Intersection(-3, s);
    var i4 = new Intersections.Intersection(2, s);
    var xs = new Intersections();
    xs.addAll(List.of(i1, i2, i3, i4));
    var i = xs.hit();
    assertEquals(i4, i.orElseThrow());
  }
}
