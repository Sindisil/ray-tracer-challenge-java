package com.gregjandl.raytracer.rtlib;

import java.util.ArrayList;
import java.util.List;

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

  Color shadeHit(PreComps comps) {
    var material = comps.getObject().getMaterial();
    return lights.stream()
        .reduce(Color.BLACK,
            (color, light) -> material.lighting(light, comps.getPoint(), comps.getEyeVec(),
                comps.getNormal()),
            Color::add);
  }

  void setLights(List<PointLight> pointLights) {
    lights.clear();
    lights.addAll(pointLights);
  }

  Color colorAt(Ray r) {
    var xs = intersect(r);
    var hit = xs.hit();
    if (hit.isEmpty()) { return Color.BLACK;}

    var comps = new PreComps(hit.get(), r);
    return shadeHit(comps);
  }

  /**
   * Utility class providing precomputed values for items related to an intersection that will be
   * used in the shader.
   */
  static class PreComps {
    private final float t;
    private final Sphere object;
    private final Point point;
    private final Vector3 eyeVec;
    private final Vector3 normal;
    private final boolean inside;

    PreComps(IntersectionList.Intersection intersection, Ray ray) {
      t = intersection.getT();
      object = intersection.getObject();
      point = ray.getPosition(intersection.getT());
      eyeVec = ray.getDirection().negate();
      var norm = intersection.getObject().normalAt(point);
      if (norm.dot(eyeVec) < 0) {
        inside = true;
        norm = norm.negate();
      } else {
        inside = false;
      }
      normal = norm;
    }

    float getT() { return t; }

    Sphere getObject() { return object; }

    Point getPoint() { return point; }

    Vector3 getEyeVec() { return eyeVec; }

    Vector3 getNormal() { return normal; }

    public boolean isInside() { return inside;}
  }
}
