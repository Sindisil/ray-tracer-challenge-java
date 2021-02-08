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

  @SuppressWarnings("ResultOfObjectAllocationIgnored")
  @Test
  @DisplayName("Matrix ctor is bounds checked")
  void testBadCreate() {
    assertThrows(IllegalArgumentException.class, () -> new Matrix4x4(new float[][]{{1}, {2}, {3}}));
    //noinspection ZeroLengthArrayAllocation
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
  void testCheckInvertible() {
    var m = new Matrix4x4(new float[][]{{6, 4, 4, 4}, {5, 5, 7, 6}, {4, -9, 3, -7}, {9, 1, 7, -6}});
    assertTrue(m.isInvertible());
  }

  @Test
  @DisplayName("Testing an invertible matrix for invertibility")
  void testCheckNonInvertible() {
    var m = new Matrix4x4(
        new float[][]{{-4, 2, -2, 3}, {9, 6, 2, 6}, {0, -5, 1, -5}, {0, 0, 0, 0}});
    assertFalse(m.isInvertible());
  }

  @Test
  @DisplayName("Attempting to invert a non-invertible matrix should throw")
  void testInvertNonInvertible() {
    var m = new Matrix4x4(
        new float[][]{{-4, 2, -2, 3}, {9, 6, 2, 6}, {0, -5, 1, -5}, {0, 0, 0, 0}});
    assertThrows(ArithmeticException.class, m::invert);
  }

  @Test
  @DisplayName("Calculating the inverse of matrices")
  void testInvert() {
    var m1 = new Matrix4x4(
        new float[][]{{-5, 2, 6, -8}, {1, -5, 1, 8}, {7, 7, -6, -7}, {1, -3, 7, 4}});
    var invM1 = new Matrix4x4(new float[][]{
        {0.21805f, 0.45113f, 0.24060f, -0.04511f},
        {-0.80827f, -1.45677f, -0.44361f, 0.52068f},
        {-0.07895f, -0.22368f, -0.05263f, 0.19737f},
        {-0.52256f, -0.81391f, -0.30075f, 0.30639f}});
    var m2 = new Matrix4x4(
        new float[][]{{8, -5, 9, 2}, {7, 5, 6, 1}, {-6, 0, 9, 6}, {-3, 0, -9, -4}});
    var invM2 = new Matrix4x4(new float[][]{
        {-.15385f, -.15385f, -.28205f, -.53846f},
        {-.07692f, .12308f, .02564f, .03077f},
        {.35897f, .35897f, .43590f, .92308f},
        {-.69231f, -.69231f, -.76923f, -1.92308f}});
    assertEquals(invM1, m1.invert());
    assertEquals(invM2, m2.invert());
  }

  @Test
  @DisplayName("Multiplying a product by one factor's inverse")
  void testMultiplyByInverse() {
    var m1 = new Matrix4x4(
        new float[][]{{3, -9, 7, 3}, {3, -8, 2, -9}, {-4, 4, 4, 4}, {1, -6, 5, -1}});
    var m2 = new Matrix4x4(new float[][]{{8, 2, 2, 2}, {3, -1, 7, 0}, {7, 0, 5, 4}, {6, -2, 0, 5}});
    assertEquals(m1, m1.multiply(m2).multiply(m2.invert()));
  }

  @Test
  @DisplayName("Multiplying by a translation matrix")
  void testMultiplyPointByTranslationMatrix() {
    var translation = Matrix4x4.translation(5, -3, 2);
    var point = new Point(-3, 4, 5);
    var translatedPoint = new Point(2, 1, 7);
    assertEquals(translatedPoint, translation.multiply(point));
    assertEquals(translatedPoint, Matrix4x4.identity().translate(5, -3, 2).multiply(point));
  }

  @Test
  @DisplayName("Multiplying by the inverse of a translation matrix")
  void testMultiplyPointByInverseTranslationMatrix() {
    var translation = Matrix4x4.translation(5, -3, 2);
    var point = new Point(-3, 4, 5);
    assertEquals(new Point(-8, 7, 3), translation.invert().multiply(point));
  }

  @Test
  @DisplayName("Translation doesn't affect vectors")
  void testMultiplyVectorByTranslationMatrix() {
    var translation = Matrix4x4.translation(5, -3, 2);
    var vector = new Vector3(-3, 4, 5);
    assertEquals(vector, translation.multiply(vector));
  }

  @Test
  @DisplayName("A scaling matrix applied to a point")
  void testApplyScalingMatrixToPoint() {
    var point = new Point(-4, 6, 8);
    var scaledPoint = new Point(-8, 18, 32);
    assertEquals(scaledPoint, Matrix4x4.scaling(2, 3, 4).multiply(point));
    assertEquals(scaledPoint, Matrix4x4.identity().scale(2, 3, 4).multiply(point));
  }

  @Test
  @DisplayName("A scaling matrix applied to a vector")
  void testApplyScalingMatrixToVector() {
    var vector = new Vector3(-4, 6, 8);
    var scaledVector = new Vector3(-8, 18, 32);
    assertEquals(scaledVector, Matrix4x4.scaling(2, 3, 4).multiply(vector));
    assertEquals(scaledVector, Matrix4x4.identity().scale(2, 3, 4).multiply(vector));
  }

  @Test
  @DisplayName("Multiplying by the inverse of a scaling matrix")
  void testMultiplyByInverseScalingMatrix() {
    var vector = new Vector3(-4, 6, 8);
    var scaledVector = new Vector3(-2, 2, 2);
    assertEquals(scaledVector, Matrix4x4.scaling(2, 3, 4).invert().multiply(vector));
    assertEquals(scaledVector, Matrix4x4.identity().scale(2, 3, 4).invert().multiply(vector));
  }

  @Test
  @DisplayName("Reflection is scaling by a negative value")
  void testReflection() {
    var point = new Point(2, 3, 4);
    var reflected = new Point(-2, 3, 4);
    assertEquals(reflected, Matrix4x4.scaling(-1, 1, 1).multiply(point));
    assertEquals(reflected, Matrix4x4.identity().scale(-1, 1, 1).multiply(point));
  }

  @Test
  @DisplayName("Rotating a point around the x axis")
  void testRotateOnX() {
    var p = new Point(0, 1, 0);
    var pRotEighth = new Point(0, Math.sqrt(2) / 2, Math.sqrt(2) / 2);
    var pRotQuarter = new Point(0, 0, 1);

    assertEquals(pRotEighth, Matrix4x4.rotationOnX(Math.PI / 4).multiply(p));
    assertEquals(pRotEighth, Matrix4x4.identity().rotateOnX(Math.PI / 4).multiply(p));
    assertEquals(pRotQuarter, Matrix4x4.rotationOnX(Math.PI / 2).multiply(p));
    assertEquals(pRotQuarter, Matrix4x4.identity().rotateOnX(Math.PI / 2).multiply(p));
  }

  @Test
  @DisplayName("Inverse of rotation matrix rotates in the opposite direction")
  void testRotateOnXInverse() {
    var p = new Point(0, 1, 0);
    var pRotEighth = new Point(0, Math.sqrt(2) / 2, -Math.sqrt(2) / 2);

    assertEquals(pRotEighth, Matrix4x4.rotationOnX(Math.PI / 4).invert().multiply(p));
    assertEquals(pRotEighth, Matrix4x4.identity().rotateOnX(Math.PI / 4).invert().multiply(p));
  }

  @Test
  @DisplayName("Rotating a point around the y axis")
  void testRotateOnY() {
    var p = new Point(0, 0, 1);
    var pRotEighth = new Point(Math.sqrt(2) / 2, 0, Math.sqrt(2) / 2);
    var pRotQuarter = new Point(1, 0, 0);

    assertEquals(pRotEighth, Matrix4x4.rotationOnY(Math.PI / 4).multiply(p));
    assertEquals(pRotEighth, Matrix4x4.identity().rotateOnY(Math.PI / 4).multiply(p));
    assertEquals(pRotQuarter, Matrix4x4.rotationOnY(Math.PI / 2).multiply(p));
    assertEquals(pRotQuarter, Matrix4x4.identity().rotateOnY(Math.PI / 2).multiply(p));
  }

  @Test
  @DisplayName("Inverse of rotation matrix rotates in the opposite direction")
  void testRotateOnYInverse() {
    var p = new Point(0, 0, 1);
    var pRotEighth = new Point(-Math.sqrt(2) / 2, 0, Math.sqrt(2) / 2);

    assertEquals(pRotEighth, Matrix4x4.rotationOnY(Math.PI / 4).invert().multiply(p));
    assertEquals(pRotEighth, Matrix4x4.identity().rotateOnY(Math.PI / 4).invert().multiply(p));
  }

  @Test
  @DisplayName("Rotating a point around the Z axis")
  void testRotateOnZ() {
    var p = new Point(0, 1, 0);
    var pRotEighth = new Point(-Math.sqrt(2) / 2, Math.sqrt(2) / 2, 0);
    var pRotQuarter = new Point(-1, 0, 0);

    assertEquals(pRotEighth, Matrix4x4.rotationOnZ(Math.PI / 4).multiply(p));
    assertEquals(pRotEighth, Matrix4x4.identity().rotateOnZ(Math.PI / 4).multiply(p));
    assertEquals(pRotQuarter, Matrix4x4.rotationOnZ(Math.PI / 2).multiply(p));
    assertEquals(pRotQuarter, Matrix4x4.identity().rotateOnZ(Math.PI / 2).multiply(p));
  }

  @Test
  @DisplayName("Inverse of rotation matrix rotates in the opposite direction")
  void testRotateOnZInverse() {
    var p = new Point(0, 1, 0);
    var pRotEighth = new Point(Math.sqrt(2) / 2, Math.sqrt(2) / 2, 0);

    assertEquals(pRotEighth, Matrix4x4.rotationOnZ(Math.PI / 4).invert().multiply(p));
    assertEquals(pRotEighth, Matrix4x4.identity().rotateOnZ(Math.PI / 4).invert().multiply(p));
  }

  @Test
  @DisplayName("A shearing transformation moving x in proportion to y")
  void testShearXY() {
    var point = new Point(2, 3, 4);
    var shearedPoint = new Point(5, 3, 4);
    assertEquals(shearedPoint, Matrix4x4.shearing(1, 0, 0, 0, 0, 0).multiply(point));
    assertEquals(shearedPoint, Matrix4x4.identity().shear(1, 0, 0, 0, 0, 0).multiply(point));
  }

  @Test
  @DisplayName("A shearing transformation moving x in proportion to z")
  void testShearXZ() {
    var point = new Point(2, 3, 4);
    var shearedPoint = new Point(8, 3, 4);
    assertEquals(shearedPoint, Matrix4x4.shearing(0, 1.5f, 0, 0, 0, 0).multiply(point));
    assertEquals(shearedPoint, Matrix4x4.identity().shear(0, 1.5f, 0, 0, 0, 0).multiply(point));
  }

  @Test
  @DisplayName("A shearing transformation moving y in proportion to x")
  void testShearYX() {
    var point = new Point(2, 3, 4);
    var shearedPoint = new Point(2, 5, 4);
    assertEquals(shearedPoint, Matrix4x4.shearing(0, 0, 1, 0, 0, 0).multiply(point));
    assertEquals(shearedPoint, Matrix4x4.identity().shear(0, 0, 1, 0, 0, 0).multiply(point));
  }

  @Test
  @DisplayName("A shearing transformation moving y in proportion to z")
  void testShearYZ() {
    var point = new Point(2, 3, 4);
    var shearedPoint = new Point(2, 7, 4);
    assertEquals(shearedPoint, Matrix4x4.shearing(0, 0, 0, 1, 0, 0).multiply(point));
    assertEquals(shearedPoint, Matrix4x4.identity().shear(0, 0, 0, 1, 0, 0).multiply(point));
  }

  @Test
  @DisplayName("A shearing transformation moving z in proportion to x")
  void testShearZX() {
    var point = new Point(2, 3, 4);
    var shearedPoint = new Point(2, 3, 6);
    assertEquals(shearedPoint, Matrix4x4.shearing(0, 0, 0, 0, 1, 0).multiply(point));
    assertEquals(shearedPoint, Matrix4x4.identity().shear(0, 0, 0, 0, 1, 0).multiply(point));
  }

  @Test
  @DisplayName("A shearing transformation moving z in proportion to y")
  void testShearZY() {
    var point = new Point(2, 3, 4);
    var shearedPoint = new Point(2, 3, 7);
    assertEquals(shearedPoint, Matrix4x4.shearing(0, 0, 0, 0, 0, 1).multiply(point));
    assertEquals(shearedPoint, Matrix4x4.identity().shear(0, 0, 0, 0, 0, 1).multiply(point));
  }

  @Test
  @DisplayName("Transformations are applied in sequence")
  void testTransformationSequence() {
    var point = new Point(1, 0, 1);
    var rotation = Matrix4x4.rotationOnX(Math.PI / 2);
    var scaling = Matrix4x4.scaling(5, 5, 5);
    var translation = Matrix4x4.translation(10, 5, 7);

    var p2 = rotation.multiply(point);
    assertEquals(new Point(1, -1, 0), p2);
    var p3 = scaling.multiply(p2);
    assertEquals(new Point(5, -5, 0), p3);
    var p4 = translation.multiply(p3);
    assertEquals(new Point(15, 0, 7), p4);
  }

  @Test
  @DisplayName("Chained transformations must be applied in the correct order")
  void testChainedTransformations() {
    var p = new Point(1, 0, 1);
    var rotation = Matrix4x4.rotationOnX(Math.PI / 2);
    var scaling = Matrix4x4.scaling(5, 5, 5);
    var translation = Matrix4x4.translation(10, 5, 7);
    var expected = new Point(15, 0, 7);

    var transformation = translation.multiply(scaling).multiply(rotation);
    assertEquals(expected, transformation.multiply(p));
    var p2 = Matrix4x4.rotationOnX(Math.PI / 2).scale(5, 5, 5).translate(10, 5, 7).multiply(p);
    assertEquals(expected, p2);
  }
}
