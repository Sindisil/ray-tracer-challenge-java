package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CameraTest {
  @Test
  @DisplayName("Constructing a Camera")
  void testCreate() {
    int hSize = 160;
    int vSize = 120;
    float fov = (float) Math.PI / 2;
    var c = new Camera(hSize, vSize, fov);
    assertEquals(hSize, c.getHSize());
    assertEquals(vSize, c.getVSize());
    assertEquals(fov, c.getFov());
  }

  @Test
  @DisplayName("The pixel size for a horizontal canvas")
  void testPixelSizeHorizontal() {
    var c = new Camera(200, 125, Math.PI / 2);
    assertEquals(0.01f, c.getPixelSize());
  }

  @Test
  @DisplayName("The pixel size for a vertical canvas")
  void testPixelSizeVertical() {
    var c = new Camera(125, 200, Math.PI / 2);
    assertEquals(0.01f, c.getPixelSize());
  }

  @Nested
  @DisplayName("Getting the ray for a specific canvas pixel")
  class TestRayForPixel {
    Camera c;

    @BeforeEach
    void initCamera() {
      c  = new Camera(201, 101, Math.PI/2);
    }

    @Test
    @DisplayName("Constructing a ray through the center of the canvas")
    void testRayThroughCenter() {
      var expected = new Ray(new Point(0, 0, 0), new Vector3(0, 0, -1));
      var r = c.rayForPixel(100, 50);
      assertEquals(expected, r);
    }

    @Test
    @DisplayName("Constructing a ray through a corner of the canvas")
    void testRayThroughCorner() {
      var expected = new Ray(new Point(0, 0, 0), new Vector3(0.66519f, 0.33259f, -0.66851f));
      var r = c.rayForPixel(0, 0);
      assertEquals(expected, r);
    }

    @Test
    @DisplayName("Constructing a ray when the camera is transformed")
    void testRayWithTransformedCamera() {
      c.setViewTransform(Matrix4x4.translation(0, -2, 5).rotateOnY(Math.PI/4));
      var expected = new Ray(new Point(0, 2, -5), new Vector3(Math.sqrt(2)/2, 0, -Math.sqrt(2)/2));
      var r = c.rayForPixel(100, 50);
      assertEquals(expected, r);
    }
  }

  @Test
  @DisplayName("The transformation matrix for the default orientation")
  void testDefaultViewTransform() {
    var from = Point.ORIGIN;
    var to = new Point(0, 0, -1);
    var up = new Vector3(0, 1, 0);
    var c = new Camera(10, 10, 1.5);
    c.setViewTransform(from, to, up);
    assertEquals(Matrix4x4.identity(), c.getViewTransform());
  }

  @Test
  @DisplayName("A view transformation looking toward positive Z")
  void testPositiveZViewTransform() {
    var from = Point.ORIGIN;
    var to = new Point(0, 0, 1);
    var up = new Vector3(0, 1, 0);
    var c = new Camera(10, 10, 1.5);
    c.setViewTransform(from, to, up);
    assertEquals(Matrix4x4.scaling(-1, 1, -1), c.getViewTransform());
  }

  @Test
  @DisplayName("The view transformation moves the world")
  void testViewMovesWorld() {
    var from = new Point(0, 0, 8);
    var to = Point.ORIGIN;
    var up = new Vector3(0, 1, 0);
    var c = new Camera(10, 10, 1);
    c.setViewTransform(from, to, up);
    assertEquals(Matrix4x4.translation(0, 0, -8), c.getViewTransform());
  }

  @Test
  @DisplayName("An arbitrary view transformation")
  void testViewTransform() {
    var from = new Point(1, 3, 2);
    var to = new Point(4, -2, 8);
    var up = new Vector3(1, 1, 0);
    var c = new Camera(10, 10, 1);
    c.setViewTransform(from, to, up);
    var expected = new Matrix4x4(new float[][]{
        {-0.50709f, 0.50709f, 0.67612f, -2.36643f},
        {0.76772f, 0.60609f, 0.12122f, -2.82843f},
        {-0.35857f, 0.59761f, -0.71714f, 0},
        {0, 0, 0, 1}
    });
    assertEquals(expected, c.getViewTransform());
  }

  @Test
  @DisplayName("Rendering a world with a camera")
  void testRender() {
    var w = World.getDefault();
    var c = new Camera(11, 11, Math.PI/2);
    var from = new Point(0, 0, -5);
    var to = Point.ORIGIN;
    var up = new Vector3(0, 1, 0);
    c.setViewTransform(from, to, up);
    var image = c.render(w);
    var expected = new Color(0.38066f, 0.47583f, 0.2855f).toAwtColor();
    assertEquals(expected.getRGB(), image.getRGB(5, 5));
  }
}
