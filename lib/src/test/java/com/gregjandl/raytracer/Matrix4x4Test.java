package com.gregjandl.raytracer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Matrix4x4Test {
  @Test
  @DisplayName("Creating and inspecting a 4x4 matrix")
  void testAccess() {
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
}