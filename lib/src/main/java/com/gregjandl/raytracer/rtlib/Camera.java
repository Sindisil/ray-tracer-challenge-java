package com.gregjandl.raytracer.rtlib;

import java.awt.image.BufferedImage;

public class Camera {
  private final int hSize;
  private final int vSize;
  private final float fov;
  private final float pixelSize;
  private final float halfWidth;
  private final float halfHeight;
  private Matrix4x4 viewTransform;

  public Camera(int hSize, int vSize, float fov) {
    viewTransform = Matrix4x4.identity();

    this.hSize = hSize;
    this.vSize = vSize;
    this.fov = fov;

    var halfView = Math.tan(fov / 2);
    var aspectRatio = hSize / (float) vSize;

    if (aspectRatio >= 1) {
      halfWidth = (float) halfView;
      halfHeight = (float) (halfView / aspectRatio);
    } else {
      halfWidth = (float) (halfView * aspectRatio);
      halfHeight = (float) halfView;
    }

    pixelSize = (halfWidth * 2) / hSize;
  }

  public Camera(int hSize, int vSize, double fov) {
    this(hSize, vSize, (float) fov);
  }

  public int getHSize() { return hSize; }

  public int getVSize() { return vSize; }

  public float getFov() { return fov; }

  float getPixelSize() { return pixelSize; }

  Ray rayForPixel(int pixelX, int pixelY) {
    var xOffset = (pixelX + 0.5f) * pixelSize;
    var yOffset = (pixelY + 0.5f) * pixelSize;

    var worldX = halfWidth - xOffset;
    var worldY = halfHeight - yOffset;

    var inverseTransform = viewTransform.invert();
    var pixel = inverseTransform.multiply(new Point(worldX, worldY, -1));
    var origin = inverseTransform.multiply(Point.ORIGIN);
    var direction = pixel.subtract(origin).normalize();

    return new Ray(origin, direction);
  }

  void setViewTransform(Point from, Point to, Vector3 up) {
    var forward = to.subtract(from).normalize();
    var left = forward.cross(up.normalize());
    var trueUp = left.cross(forward);
    var orientation = new Matrix4x4(new float[][]{
        {left.getX(), left.getY(), left.getZ(), 0},
        {trueUp.getX(), trueUp.getY(), trueUp.getZ(), 0},
        {-forward.getX(), -forward.getY(), -forward.getZ(), 0},
        {0, 0, 0, 1}
    });
    viewTransform =
        orientation.multiply(Matrix4x4.translation(-from.getX(), -from.getY(), -from.getZ()));
  }

  Matrix4x4 getViewTransform() {
    return viewTransform;
  }

  void setViewTransform(Matrix4x4 viewTransform) {
    this.viewTransform = viewTransform;
  }

  BufferedImage render(World w) {
    var image = new BufferedImage(hSize, vSize, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < vSize - 1; ++y) {
      for (int x = 0; x < hSize - 1; ++x) {
        var r = rayForPixel(x, y);
        var c = w.colorAt(r);
        image.setRGB(x, y, c.toAwtColor().getRGB());
      }
    }

    return image;
  }

  @Override
  public String toString() {
    return "Camera{hSize=" + hSize + ", vSize=" + vSize + ", fov=" + fov
        + ", pixelSize=" + pixelSize + ", halfWidth=" + halfWidth + ", halfHeight=" + halfHeight
        + ", viewTransform=" + viewTransform + '}';
  }
}
