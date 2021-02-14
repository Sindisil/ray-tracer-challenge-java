package com.gregjandl.raytracer.rtlib;

import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class Vector3Test {
  @Test
  @DisplayName("A Vector3 may be constructed from three floats")
  void testVector3Creation() {
    Vector3 v = new Vector3(4, -4, 3);
    assertEquals(v.getX(), 4);
    assertEquals(v.getY(), -4);
    assertEquals(v.getZ(), 3);
  }

  @Test
  @DisplayName("Vectors have toString")
  void testToString() {
    var expected = "Vector3{x=" + 1.1 + ", y=" + 2.2 + ", z=" + 3.3 + '}';
    var actual = new Vector3(1.1, 2.2, 3.3).toString();
    assertEquals(expected, actual);
  }

  @Nested
  @DisplayName("Vector3s may be compared for equality")
  class testEquality {
    final Vector3 a = new Vector3(5.0f, -3.0f, 17.0f);
    final Vector3 b = new Vector3(5.0f, -3.0f, 17.0f);
    final Vector3 c = new Vector3(5.0f, 56.0f, -3.0f);

    @SuppressWarnings({"ConstantConditions", "EqualsBetweenInconvertibleTypes", "EqualsWithItself",
        "SimplifiableAssertion"})
    @Test
    @DisplayName("a and b should compare equal to each other symmetrically, but not to c, "
        + "non-Vector3 or null")
    void testAEqualsBNotC() {
      assertTrue(a.equals(a)); // equal to self
      assertTrue(a.equals(b)); // equal
      assertTrue(b.equals(a)); // symmetric
      assertFalse(a.equals(c)); // differ
      assertFalse(c.equals(a)); // symmetric
      assertFalse(b.equals(c)); // differ
      assertFalse(c.equals(b)); // symmetric
      assertFalse(a.equals("not a vector")); // differ in type
      assertFalse(a.equals(null)); // differ from null
    }

    @Test
    @DisplayName("All three components are compared")
    void testAllComponentsCompared() {
      assertNotEquals(new Vector3(5.0f, -3.0f, 0.0f), a);
      assertNotEquals(new Vector3(5.0f, 0.0f, 17.0f), a);
      assertNotEquals(new Vector3(0.0f, -3.0f, 17.0f), a);
    }
  }

  @Test
  @DisplayName("hash codes are equal if objects are equal")
  void testHashCode() {
    var a = new Vector3(1, 2, 3);
    var b = new Vector3(1, 2, 3);
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  @DisplayName("Two Vector3s may be subtracted")
  void testSubtract() {
    var v1 = new Vector3(3.0f, 2.0f, 1.0f);
    var v2 = new Vector3(5.0f, 6.0f, 7.0f);
    assertEquals(v1.subtract(v2), new Vector3(-2.0f, -4.0f, -6.0f));
  }

  @Test
  @DisplayName("Two Vector3s may be added together")
  void testAddVector3() {
    var v1 = new Vector3(-2, 3, 1);
    var v2 = new Vector3(2, 1.2f, -6.7f);
    assertEquals(new Vector3(0, 4.2f, -5.7f), v1.add(v2));
  }

  @Test
  @DisplayName("A Point may be added to a Vector3")
  void testAddPoint() {
    var p = new Point(3, -2, 5);
    var v = new Vector3(-2, 3, 1);
    assertEquals(v.add(p), new Point(1, 1, 6));
  }

  @Test
  @DisplayName("A Vector may be negated")
  void testNegate() {
    var v = new Vector3(1, -2, 3);
    assertEquals(new Vector3(-1, 2, -3), v.negate());
  }

  @Test
  @DisplayName("A vector may be multiplied by a scalar")
  void testMultiplyByScalar() {
    var v = new Vector3(1, -2, 3);
    assertEquals(new Vector3(3.5f, -7, 10.5f), v.multiply(3.5f));
    assertEquals(new Vector3(0.5f, -1, 1.5f), v.multiply(0.5f));
  }

  @Test
  @DisplayName("A Vector may be divided by scalar")
  void testDivideByScalar() {
    var v = new Vector3(1, -2, 3);
    assertEquals(new Vector3(0.5f, -1f, 1.5f), v.divide(2));
  }

  @Test
  @DisplayName("Vectors can return their magnitude")
  void testMagnitude() {
    assertEquals(1, new Vector3(1, 0, 0).magnitude());
    assertEquals(1, new Vector3(0, 1, 0).magnitude());
    assertEquals(1, new Vector3(0, 0, 1).magnitude());
    assertTrue(Utils.aboutEqual(sqrt(14), new Vector3(1, 2, 3).magnitude()));
    assertTrue(Utils.aboutEqual(sqrt(14), new Vector3(-1, -2, -3).magnitude()));
  }

  @Nested
  @DisplayName("Vector3 normalization")
  class Normalization {
    @Test
    @DisplayName("Attempting to normalize zero length Vector throws")
    void testNormalizeZero() {
      var ex = assertThrows(ArithmeticException.class, () -> new Vector3(0, 0, 0).normalize());
      assertEquals("Can't normalize a Vector3 with zero magnitude", ex.getMessage());
    }

    @Test
    @DisplayName("Non-zero Vectors may be normalized")
    void testNormalize() {
      assertEquals(new Vector3(1, 0, 0), new Vector3(4, 0, 0).normalize());
      assertEquals(new Vector3(1 / sqrt(14), 2 / sqrt(14), 3 / sqrt(14)),
          new Vector3(1, 2, 3).normalize());
    }

    @Test
    @DisplayName("Normalized Vector has magnitude 1")
    void testNormalizedMagnitude() {
      assertTrue(Utils.aboutEqual(1, new Vector3(1, 2, 3).normalize().magnitude()));
    }
  }

  @Test
  @DisplayName("The dot product of two Vectors")
  void testDotProduct() {
    assertEquals(20, new Vector3(1, 2, 3).dot(new Vector3(2, 3, 4)));
  }

  @Test
  @DisplayName("The cross product of two Vectors")
  void testCrossProduct() {
    var v1 = new Vector3(1, 2, 3);
    var v2 = new Vector3(2, 3, 4);
    assertEquals(new Vector3(-1, 2, -1), v1.cross(v2));
    assertEquals(new Vector3(1, -2, 1), v2.cross(v1));
  }
}
