package com.gregjandl.raytracer.rtlib;

import java.util.ArrayList;

public class World {
  private final ArrayList<Sphere> objects;
  private final ArrayList<PointLight> lights;

  public World() {
    objects = new ArrayList<>();
    lights = new ArrayList<>(1);
  }

  public static World getDefault() {
    var w = new World();
    var l1 = new PointLight(new Point(-10, 10, -10), Color.WHITE);
    var s1 = new Sphere();
    s1.setMaterial(
        new Material.Builder().color(new Color(.8f, 1, .6f)).diffuse(.7f).specular(.2f).build());
    var s2 = new Sphere();
    s2.setTransform(Matrix4x4.scaling(.5f, .5f, .5f));
    w.lights.add(l1);
    w.objects.add(s1);
    w.objects.add(s2);
    return w;
  }

  public int getObjectCount() {
    return objects.size();
  }

  public int getLightCount() {
    return lights.size();
  }

  public PointLight getLight(int i) {
    return lights.get(i);
  }

  public Sphere getObject(int i) {
    return objects.get(i);
  }

  public IntersectionList intersect(Ray r) {
    var xs = new IntersectionList();
    for (var obj : objects) {
      obj.intersects(r, xs);
    }
    return xs;
  }
}
