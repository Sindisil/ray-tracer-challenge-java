package com.gregjandl.raytracer;

import com.gregjandl.raytracer.rtlib.Camera;
import com.gregjandl.raytracer.rtlib.Color;
import com.gregjandl.raytracer.rtlib.Material;
import com.gregjandl.raytracer.rtlib.Matrix4x4;
import com.gregjandl.raytracer.rtlib.Point;
import com.gregjandl.raytracer.rtlib.PointLight;
import com.gregjandl.raytracer.rtlib.Scene;
import com.gregjandl.raytracer.rtlib.Sphere;
import com.gregjandl.raytracer.rtlib.Vector3;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class App {
  final Scene scene;
  final Camera camera;

  public App(int hSize, int vSize) {
    var eyePoint = new Point(0, 1.5f, -5);
    var lookPoint = new Point(0, 1, 0);
    var upVector = new Vector3(0, 1, 0);
    camera = new Camera(hSize, vSize, Math.PI / 3);
    camera.setViewTransform(eyePoint, lookPoint, upVector);

    scene = new Scene();

    var floor = new Sphere();
    floor.setTransform(Matrix4x4.scaling(10, 0.01f, 10));
    floor.setMaterial(new Material.Builder().color(new Color(1, .9f, .9f)).specular(0).build());
    scene.addObject(floor);

    var leftWall = new Sphere();
    leftWall.setTransform(
        Matrix4x4.scaling(10, 0.01f, 10)
            .rotateOnX(Math.PI / 2)
            .rotateOnY(-Math.PI / 4)
            .translate(0, 0, 5));
    leftWall.setMaterial(floor.getMaterial());
    scene.addObject(leftWall);

    var rightWall = new Sphere();
    rightWall.setTransform(
        Matrix4x4.scaling(10, 0.01f, 10)
            .rotateOnX(Math.PI / 2)
            .rotateOnY(Math.PI / 4)
            .translate(0, 0, 5));
    rightWall.setMaterial(floor.getMaterial());
    scene.addObject(rightWall);

    var largeSphere = new Sphere();
    largeSphere.setTransform(Matrix4x4.translation(-0.5f, 1, 0.5f));
    largeSphere.setMaterial(
        new Material.Builder()
            .color(new Color(0.1f, 1, 0.5f))
            .diffuse(0.7f)
            .specular(0.3f)
            .build());
    scene.addObject(largeSphere);

    var mediumSphere = new Sphere();
    mediumSphere.setTransform(Matrix4x4.scaling(0.5f, 0.5f, 0.5f).translate(1.5f, 0.5f, -0.5f));
    mediumSphere.setMaterial(
        new Material.Builder()
            .color(new Color(0.5f, 1, 0.1f))
            .diffuse(0.7f)
            .specular(0.3f)
            .build());
    scene.addObject(mediumSphere);

    var smallSphere = new Sphere();
    smallSphere.setTransform(
        Matrix4x4.scaling(0.33f, 0.33f, 0.33f).translate(-1.5f, 0.33f, -0.75f));
    smallSphere.setMaterial(
        new Material.Builder()
            .color(new Color(1, 0.8f, 0.1f))
            .diffuse(0.7f)
            .specular(0.3f)
            .build());
    scene.addObject(smallSphere);

    scene.addLight(new PointLight(new Point(-10, 10, -10)));
  }

  public BufferedImage render() {
    return camera.render(scene);
  }

  public static void main(String[] args) throws IOException {
    int hSize;
    int vSize;

    switch (args.length) {
        // no size specified, use default
      case 0 -> {
        hSize = 200;
        vSize = 100;
      }
        // square image of specified size
      case 1 -> hSize = vSize = Integer.parseInt(args[0]);
        // rectangular image with specified width and height, in that order
      case 2 -> {
        hSize = Integer.parseInt(args[0]);
        vSize = Integer.parseInt(args[1]);
      }
        // unexpected number of arguments, show usage and exit
      default -> {
        System.err.println(
            """
            Unexpected number of arguments.

            [size] for square image of specified size
            [width] [height] for rectangular image of specified width and height

            """);
        return;
      }
    }

    var app = new App(hSize, vSize);

    var start = System.nanoTime();

    BufferedImage image = app.render();

    var endRendering = System.nanoTime();

    ImageIO.write(image, "PNG", new File("raytrace.png"));

    var endWriting = System.nanoTime();

    System.out.println(
        "Rendering of "
            + hSize
            + " x "
            + vSize
            + " image took: "
            + ((endRendering - start) / 1000000)
            + "ms.");
    System.out.println(
        "Writing of the resulting PNG took: " + ((endWriting - endRendering) / 1000000) + "ms.");
  }
}
