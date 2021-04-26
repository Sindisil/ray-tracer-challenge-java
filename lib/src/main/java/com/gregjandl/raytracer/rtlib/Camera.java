package com.gregjandl.raytracer.rtlib;

import java.awt.image.BufferedImage;

/**
 * Represents a view, allowing a {@code World} (i.e., scene) to be rendered to a {@code
 * BufferedImage}.
 */
public class Camera {
  private final int hSize;
  private final int vSize;
  private final float fov;
  private final float pixelSize;
  private final float halfWidth;
  private final float halfHeight;
  private Matrix4x4 viewTransform;

  /**
   * Construct a {@code Camera} with the specified view size and field of view. FOV is treated saved
   * internally as a {@code float} value.
   *
   * @param hSize Width of this camera's rendered view
   * @param vSize Height of this camera's rendered view
   * @param fov this camera's field of view
   */
  public Camera(int hSize, int vSize, double fov) {
    viewTransform = Matrix4x4.identity();

    this.hSize = hSize;
    this.vSize = vSize;
    this.fov = (float) fov;

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

  /**
   * Return the horizontal size of this {@code Camera}'s view.
   *
   * @return horizontal view size
   */
  public int getHSize() {
    return hSize;
  }

  /**
   * Return the vertical size of this {@code Camera}'s view.
   *
   * @return vertical view size
   */
  public int getVSize() {
    return vSize;
  }

  /**
   * Returns the field of view for this Camera.
   *
   * @return the field of view
   */
  public float getFov() {
    return fov;
  }

  float getPixelSize() {
    return pixelSize;
  }

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

  /**
   * Returns this {@code Camera}'s view transformation
   *
   * @return the view transformation
   */
  public Matrix4x4 getViewTransform() {
    return viewTransform;
  }

  /**
   * Computes a new view transformation from the supplied {@code from} and {@code to} points and
   * {@code up} vector, and assigns it to this {@code Camera}'s view transform.
   *
   * @param from the location of the camera eye
   * @param to the point the camera is looking at
   * @param up a vector indicating the up direction
   */
  public void setViewTransform(Point from, Point to, Vector3 up) {
    var forward = to.subtract(from).normalize();
    var left = forward.cross(up.normalize());
    var trueUp = left.cross(forward);
    var orientation =
        new Matrix4x4(
            new float[][] {
              {left.getX(), left.getY(), left.getZ(), 0},
              {trueUp.getX(), trueUp.getY(), trueUp.getZ(), 0},
              {-forward.getX(), -forward.getY(), -forward.getZ(), 0},
              {0, 0, 0, 1}
            });
    viewTransform =
        orientation.multiply(Matrix4x4.translation(-from.getX(), -from.getY(), -from.getZ()));
  }

  void setViewTransform(Matrix4x4 viewTransform) {
    this.viewTransform = viewTransform;
  }

  /**
   * Render the scene represented by the supplied {@code World} into a BufferedImage, and return the
   * resulting image.
   *
   * @param scene the scene to render
   * @return the rendered scene
   */
  public BufferedImage render(Scene scene) {
    var image = new BufferedImage(hSize, vSize, BufferedImage.TYPE_INT_RGB);

    for (int y = 0; y < vSize - 1; ++y) {
      for (int x = 0; x < hSize - 1; ++x) {
        var r = rayForPixel(x, y);
        var c = scene.colorAt(r);
        image.setRGB(x, y, c.toAwtColor().getRGB());
      }
    }

    return image;
  }

  /**
   * Returns a string representation of this {@code Camera}. This method is intended to be used for
   * debugging purposes; the representation may change, but will not be {@code null}.
   *
   * @return a string representation of this {@code Camera}
   */
  @Override
  public String toString() {
    return "Camera{hSize="
        + hSize
        + ", vSize="
        + vSize
        + ", fov="
        + fov
        + ", pixelSize="
        + pixelSize
        + ", halfWidth="
        + halfWidth
        + ", halfHeight="
        + halfHeight
        + ", viewTransform="
        + viewTransform
        + '}';
  }
}
