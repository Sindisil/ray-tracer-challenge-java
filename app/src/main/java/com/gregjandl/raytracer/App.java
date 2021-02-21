package com.gregjandl.raytracer;

import com.gregjandl.raytracer.rtlib.Color;
import com.gregjandl.raytracer.rtlib.Material;
import com.gregjandl.raytracer.rtlib.Point;
import com.gregjandl.raytracer.rtlib.PointLight;
import com.gregjandl.raytracer.rtlib.Ray;
import com.gregjandl.raytracer.rtlib.Sphere;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class App {
  static final Point rayOrigin = new Point(0, 0, -5);
  static final float wallZ = 12;
  static final float wallSize = 10;
  static final float half = wallSize / 2;

  final int canvasSize;
  final BufferedImage image;
  final Sphere shape;
  final PointLight light;
  final float pixelSize;

  public App(int canvasSize) {
    this.canvasSize = canvasSize;
    pixelSize = wallSize / canvasSize;

    image = new BufferedImage(canvasSize, canvasSize, BufferedImage.TYPE_INT_RGB);

    light = new PointLight(new Point(-10, 10, -10));
    shape = new Sphere();
    shape.setMaterial(new Material.Builder().shininess(50).build());
  }

  public static void main(String[] args) throws IOException {
    int imageSize = 400;
    var app = new App(imageSize);

    var start = System.nanoTime();
    app.render();
    var endRendering = System.nanoTime();
    app.writePng(new File("raytrace.png"));
    var endWriting = System.nanoTime();

    System.out.println(
        "Rendering of " + imageSize + " x " + imageSize + " image took: "
            + ((endRendering - start) / 1000000) + "ms.");
    System.out.println(
        "Writing of the resulting PNG took: " + ((endWriting - endRendering) / 1000000)
            + "ms.");
  }

  public void render() {

    for (int y = 0; y < canvasSize; ++y) {
      var worldY = half - pixelSize * y;

      for (int x = 0; x < canvasSize; ++x) {
        var worldX = -half + pixelSize * x;

        var target = new Point(worldX, worldY, wallZ);

        var ray = new Ray(rayOrigin, target.subtract(rayOrigin).normalize());
        var xs = shape.intersects(ray);

        if (xs.hit().isPresent()) {
          var hit = xs.hit().get();
          var hitPoint = ray.getPosition(hit.getT());
          var normal = hit.getObject().normalAt(hitPoint);
          var eyeVec = ray.getDirection().negate();
          var color = shape.getMaterial().lighting(light, hitPoint, eyeVec, normal);
          image.setRGB(x, y, color.toAwtColor().getRGB());
        }
      }
    }
  }

  public void writePng(File file) throws IOException {
    ImageIO.write(image, "PNG", file);
  }
}
