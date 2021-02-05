package com.gregjandl.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Matrix4x4Test {
  @Test
  @DisplayName("Creating and inspecting a 4x4 matrix")
  void testCreateAndAccess() {
    var m = new Matrix4x4(new float[][]{
        {1, 2, 3, 4},
        {5.5f, 6.5f, 7.5f, 8.5f},
        {9, 10, 11, 12},
        {13.5f, 14.5f, 15.5f, 16.5f}
    });
    assertEquals(1, m.get(0, 0));
    assertEquals(4, m.get(0, 3));
    assertEquals(5.5f, m.get(1, 0));
    assertEquals(7.5f, m.get(1, 2));
    assertEquals(11, m.get(2, 2));
    assertEquals(15.5f, m.get(3, 2));
  }

  @Test
  @DisplayName("Matrix cells may be individually set")
  void testSet() {
    var m = new Matrix4x4();
    assertEquals(0, m.get(1, 1));
    m.set(1, 1, 42);
    assertEquals(42, m.get(1, 1));
    m.set(1, 1, -13);
    assertEquals(-13, m.get(1, 1));
  }

  @Test
  @DisplayName("Matrix set is bounds checked")
  void testBadSet() {
    var m = new Matrix4x4();
    assertThrows(IndexOutOfBoundsException.class, () -> m.set(-1, 0, 1));
    assertThrows(IndexOutOfBoundsException.class, () -> m.set(1, 5, 2));
  }

  @Test
  @DisplayName("Matrix ctor is bounds checked")
  void testBadCreate() {
    assertThrows(IllegalArgumentException.class, () -> new Matrix4x4(new float[][]{{1}, {2}, {3}}));
    assertThrows(IllegalArgumentException.class, () -> new Matrix4x4(new float[][]{}));
  }

  @Test
  @DisplayName("Matrix has a toString")
  void testToString() {
    var actual = Matrix4x4.identity().toString();
    var expected = "Matrix4x4{{1.0, 0.0, 0.0, 0.0}, {0.0, 1.0, 0.0, 0.0}, {0.0, 0.0, 1.0, 0.0}, "
        + "{0.0, 0.0, 0.0, 1.0}}";
    assertEquals(expected, actual);
  }

  @Test
  @DisplayName("Matrix access is bounds checked")
  void testBadAccess() {
    var m = new Matrix4x4(new float[][]{
        {1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 16}});
    assertThrows(IndexOutOfBoundsException.class, () -> m.get(-1, -1));
    assertThrows(IndexOutOfBoundsException.class, () -> m.get(-1, 1));
    assertThrows(IndexOutOfBoundsException.class, () -> m.get(4, 4));
    assertThrows(IndexOutOfBoundsException.class, () -> m.get(3, 4));
    assertThrows(IndexOutOfBoundsException.class, () -> m.get(1, 4));
  }

  @Test
  @DisplayName("Matrix equality")
  void testEquals() {
    var a = new Matrix4x4(new float[][]{
        {1, 2, 3, 4}, {5, 6, 7, 8}, {9.1f, 8.1f, 7.1f, 6.1f}, {5, 4, 3, 2}});
    var b = new Matrix4x4(new float[][]{
        {1, 2, 3, 4}, {5, 6, 7, 8}, {9.1f, 8.1f, 7.1f, 6.1f}, {5, 4, 3, 2}});
    var c = new Matrix4x4(new float[][]{
        {1, 2, 3, 4}, {5, 6, 7, 0}, {9.1f, 8.1f, 7.1f, 6.1f}, {5, 4, 3, 2}});
    var d = new Matrix4x4(new float[][]{
        {1, 2, 3, 4}, {5, 6, 7, 8}, {9.1f, 8.1f, 7.1f, 6.1f}, {5, 4, 3, 2.1f}});

    assertEquals(a, b);
    assertNotEquals(a, c);
    assertNotEquals(a, d);
  }

  @Test
  @DisplayName("Matrices may be multiplied")
  void testMultiply() {
    var a = new Matrix4x4(new float[][]{
        {1, 2, 3, 4}, {5, 6, 7, 8}, {9, 8, 7, 6}, {5, 4, 3, 2}});
    var b = new Matrix4x4(new float[][]{
        {-2, 1, 2, 3}, {3, 2, 1, -1}, {4, 3, 6, 5}, {1, 2, 7, 8}});
    var c = new Matrix4x4(new float[][]{
        {20, 22, 50, 48}, {44, 54, 114, 108}, {40, 58, 110, 102}, {16, 26, 46, 42}});

    assertEquals(c, a.multiply(b));
  }

  @Test
  @DisplayName("A matrix may be multiplied by a Vector")
  void testMultiplyByVector() {
    var m = new Matrix4x4(new float[][]{{1, 2, 3, 4}, {2, 4, 4, 2}, {8, 6, 4, 1}, {0, 0, 0, 1}});
    var v = new Vector3(1, 2, 3);
    assertEquals(new Vector3(14, 22, 32), m.multiply(v));
  }

  @Test
  @DisplayName("A matrix may be multiplied by a Point")
  void testMultiplyByPoint() {
    var m = new Matrix4x4(new float[][]{{1, 2, 3, 4}, {2, 4, 4, 2}, {8, 6, 4, 1}, {0, 0, 0, 1}});
    var p = new Point(1, 2, 3);
    assertEquals(new Point(18, 24, 33), m.multiply(p));
  }

  @Test
  @DisplayName("Multiplying by the identity matrix returns the original value")
  void testMultiplyByIdentity() {
    var m = new Matrix4x4(new float[][]{{1, 2, 3, 4}, {2, 4, 4, 2}, {8, 6, 4, 1}, {0, 0, 0, 1}});
    var p = new Point(1, 2, 3);
    var v = new Vector3(4, 5, 6);
    assertEquals(m, Matrix4x4.identity().multiply(m));
    assertEquals(p, Matrix4x4.identity().multiply(p));
    assertEquals(v, Matrix4x4.identity().multiply(v));
  }

  @Test
  @DisplayName("A Matrix may be transposed")
  void testTranspose() {
    var m = new Matrix4x4(new float[][]{{0, 9, 3, 0}, {9, 8, 0, 8}, {1, 8, 5, 3}, {0, 0, 5, 8}});
    var expected = new Matrix4x4(
        new float[][]{{0, 9, 1, 0}, {9, 8, 8, 0}, {3, 0, 5, 5}, {0, 8, 3, 8}});
    assertEquals(expected, m.transpose());
  }

  @Test
  @DisplayName("The identity matrix transposed is unchanged")
  void testTransposeIdentity() {
    assertEquals(Matrix4x4.identity(), Matrix4x4.identity().transpose());
  }

  @Test
  @DisplayName("Calculating the determinant of a 4x4 matrix")
  void testDeterminant() {
    var m = new Matrix4x4(
        new float[][]{{-2, -8, 3, 5}, {-3, 1, 7, 3}, {1, 2, -9, 6}, {-6, 7, 7, -9}});
    assertTrue(Utils.aboutEqual(-4071f, m.determinant()));

  }

  @Test
  @DisplayName("Testing an invertible matrix for invertibility")
  void testInvertible() {
    var m = new Matrix4x4(new float[][]{{6, 4, 4, 4}, {5, 5, 7, 6}, {4, -9, 3, -7}, {9, 1, 7, -6}});
    assertTrue(m.isInvertible());
  }

  @Test
  @DisplayName("Testing an invertible matrix for invertibility")
  void testNonInvertible() {
    var m = new Matrix4x4(
        new float[][]{{-4, 2, -2, 3}, {9, 6, 2, 6}, {0, -5, 1, -5}, {0, 0, 0, 0}});
    assertFalse(m.isInvertible());
  }
}
