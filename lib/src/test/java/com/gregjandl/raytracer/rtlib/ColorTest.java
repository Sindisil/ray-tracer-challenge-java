package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ColorTest {
  @Test
  @DisplayName("Color creation")
  void testColorCreation() {
    var c = new Color(-0.5f, 0.4f, 1.7f);
    assertEquals(Float.compare(-.5f, c.getR()), 0);
    assertEquals(Float.compare(.4f, c.getG()), 0);
    assertEquals(Float.compare(1.7f, c.getB()), 0);
  }

  @Test
  @DisplayName("Color has toString")
  void testToString() {
    assertNotNull(Color.BLACK.toString());
  }

  @Test
  @DisplayName("A Color may be converted to an AWT Color")
  void testConvertToAwtColor() {
    var c = Color.GRAY;
    assertEquals(java.awt.Color.GRAY, c.toAwtColor());
  }

  @Test
  @DisplayName("Colors may be added")
  void testAdd() {
    var c1 = new Color(.9f, .6f, .75f);
    var c2 = new Color(.7f, .1f, .25f);
    assertEquals(new Color(1.6f, .7f, 1), c1.add(c2));
    assertEquals(new Color(1.6f, .7f, 1), c2.add(c1));
  }

  @Test
  @DisplayName("Colors may be subtracted")
  void testSubtract() {
    var c1 = new Color(.9f, .6f, .75f);
    var c2 = new Color(.7f, .1f, .25f);
    assertEquals(new Color(.2f, .5f, .5f), c1.subtract(c2));
    assertEquals(new Color(-.2f, -.5f, -.5f), c2.subtract(c1));
  }

  @Test
  @DisplayName("A Color my be multiplied by a scalar")
  void testMultiplyByScalar() {
    var c = new Color(.2f, .3f, .4f);
    assertEquals(new Color(.4f, .6f, .8f), c.multiply(2));
  }

  @Test
  @DisplayName("Colors may be multiplied")
  void testMultiply() {
    var c1 = new Color(1, .2f, .4f);
    var c2 = new Color(.9f, 1, .1f);
    assertEquals(new Color(.9f, .2f, .04f), c1.multiply(c2));
    assertEquals(new Color(.9f, .2f, .04f), c2.multiply(c1));
  }

  @SuppressWarnings({
    "SimplifiableAssertion",
    "EqualsWithItself",
    "EqualsBetweenInconvertibleTypes",
    "ConstantConditions"
  })
  @Test
  @DisplayName("Colors may be tested for equality")
  void testEquals() {
    var c1a = new Color(1, 2, 3);
    var c1b = new Color(1, 2, 3);
    var c2 = new Color(4, 2, 3);
    var c3 = new Color(1, 4, 3);
    var c4 = new Color(1, 2, 4);

    assertTrue(c1a.equals(c1a)); // equals self
    assertTrue(c1a.equals(c1b)); // equals
    assertTrue(c1b.equals(c1a)); // symmetric
    assertFalse(c1a.equals(c2)); // differ in each field
    assertFalse(c1a.equals(c3)); // differ in each field
    assertFalse(c1a.equals(c4)); // differ in each field
    assertFalse(c4.equals(c1a)); // symmetric
    assertFalse(c1a.equals("not a color")); // differ in type
    assertFalse(c1a.equals(null)); // differ from null
  }

  @Test
  @DisplayName("Color hash codes are equal if objects are equal")
  void testHashCode() {
    var c1 = new Color(1, 2, 3);
    var c2 = new Color(1, 2, 3);
    assertEquals(c2.hashCode(), c1.hashCode());
  }
}
