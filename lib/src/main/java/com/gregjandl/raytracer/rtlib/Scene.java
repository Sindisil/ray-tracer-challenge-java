package com.gregjandl.raytracer.rtlib;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a scene to be rendered, made up of objects to be rendered lit by some number of
 * lights.
 */
public class Scene {
  private final ArrayList<Sphere> objects;
  private final ArrayList<PointLight> lights;

  /**
   * Construct an empty scene.
   */
  public Scene() {
    objects = new ArrayList<>();
    lights = new ArrayList<>(1);
  }

  /**
   * Construct and return the default scene.
   *
   * The default scene consists of:
   * <ul>
   *   <li>One white <code>PointLight</code> @ (-10, 10, -10)</li>
   *   <li>Two concentric spheres at the origin:
   *   <ul>
   *     <li>Color(0.8, 1.0, 0.6), diffuse(0.7), specular(0.2)</li>
   *     <li>Default material, scaled by .5 in each direction</li>
   *   </ul>
   *   </li>
   * </ul>
   *
   * @return a default scene
   */
  public static Scene getDefault() {
    var w = new Scene();
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

  /**
   * Return number of objects in this scene
   *
   * @return number of objects
   */
  public int getObjectCount() {
    return objects.size();
  }

  /**
   * Return number of lights in this scene
   *
   * @return number of lights
   */
  public int getLightCount() {
    return lights.size();
  }

  /**
   * Return the light indicated by the specified index
   *
   * @param i the index of the light to return
   * @return the light
   * @throws IndexOutOfBoundsException if the index is out of range ({@code i < 0 || i >=
   *                                   getLightCount()})
   */
  public PointLight getLight(int i) {
    return lights.get(i);
  }

  /**
   * Return the object indicated by the specified index
   *
   * @param i the index of the object to return
   * @return the object
   * @throws IndexOutOfBoundsException if the index is out of range ({@code i < 0 || i >=
   *                                   getObjectCount()})
   */
  public Sphere getObject(int i) {
    return objects.get(i);
  }

  IntersectionList intersect(Ray r) {
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

  /**
   * Return the shaded color at the point intersected by the specified {@code Ray}
   *
   * @param r the {@code Ray} to be traced into the scene
   * @return the {@code Color} of the point the ray intersects
   */
  public Color colorAt(Ray r) {
    var xs = intersect(r);
    var hit = xs.hit();
    if (hit.isEmpty()) { return Color.BLACK;}

    var comps = new PreComps(hit.get(), r);
    return shadeHit(comps);
  }

  public void addLight(PointLight light) {
    Objects.requireNonNull(light, "Added lights must be non-null");
    lights.add(light);
  }

  public void addObject(Sphere object) {
    Objects.requireNonNull(object, "Added objects must be non-null");
    objects.add(object);
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
