package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class WorldTest {
  @Test
  @DisplayName("Creating an empty World")
  void testCreateEmpty() {
    var w = new World();
    assertEquals(0, w.getObjectCount());
    assertEquals(0, w.getLightCount());
  }

  @Test
  @DisplayName("Creating the default World")
  void testCreateDefault() {
    var l1 = new PointLight(new Point(-10, 10, -10), Color.WHITE);
    var s1 = new Sphere();
    s1.setMaterial(
        new Material.Builder().color(new Color(.8f, 1, .6f)).diffuse(.7f).specular(.2f).build());
    var s2 = new Sphere();
    s2.setTransform(Matrix4x4.scaling(.5f, .5f, .5f));

    var w = World.getDefault();
    assertEquals(l1, w.getLight(0));
    assertEquals(s1, w.getObject(0));
    assertEquals(s2, w.getObject(1));
  }

  @Test
  @DisplayName("Intersect a world with a ray")
  void testIntersectRay() {
    var w = World.getDefault();
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var xs = w.intersect(r);
    assertEquals(4, xs.size());
    assertEquals(4, xs.get(0).getT());
    assertEquals(4.5f, xs.get(1).getT());
    assertEquals(5.5, xs.get(2).getT());
    assertEquals(6, xs.get(3).getT());
  }

  @Test
  @DisplayName("Precomputing the state of an intersection")
  void testPreComps() {
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = new Sphere();
    var i = new IntersectionList.Intersection(4, s);
    var comps = new World.PreComps(i, r);
    assertEquals(i.getT(), comps.getT());
    assertEquals(i.getObject(), comps.getObject());
    assertEquals(new Point(0, 0, -1), comps.getPoint());
    assertEquals(new Vector3(0, 0, -1), comps.getEyeVec());
    assertEquals(new Vector3(0, 0, -1), comps.getNormal());
  }

  @Test
  @DisplayName("Shading an intersection")
  void testShadingIntersection() {
    var w = World.getDefault();
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = w.getObject(0);
    var i = new IntersectionList.Intersection(4, s);
    var comps = new World.PreComps(i, r);
    var c = w.shadeHit(comps);
    assertEquals(new Color(0.38066f, 0.47583f, 0.2855f), c);
  }

  @Test
  @DisplayName("Shading an intersection from the inside")
  void testShadingInsideIntersection() {
    var w = World.getDefault();
    w.setLights(List.of(new PointLight(new Point(0, 0.25f, 0))));
    var r = new Ray(new Point(0, 0, 0), new Vector3(0, 0, 1));
    var s = w.getObject(1);
    var i = new IntersectionList.Intersection(0.5f, s);
    var comps = new World.PreComps(i, r);
    var c = w.shadeHit(comps);
    assertEquals(new Color(0.90498f, 0.90498f, 0.90498f), c);
  }

  @Test
  @DisplayName("The color when a ray misses")
  void testColorWhenRayMisses() {
    var w = World.getDefault();
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 1, 0));
    var c = w.colorAt(r);
    assertEquals(Color.BLACK, c);
  }

  @Test
  @DisplayName("The color when a ray hits")
  void testColorWhenRayHits() {
    var w = World.getDefault();
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var c = w.colorAt(r);
    assertEquals(new Color(0.38066f, 0.47583f, 0.2855f), c);
  }

  @Test
  @DisplayName("The color with an intersection behind the ray")
  void testColorWithHitBehindRay() {
    var w = World.getDefault();
    var outer = w.getObject(0);
    outer.setMaterial(new Material.Builder(outer.getMaterial()).ambient(1).build());
    var inner = w.getObject(1);
    inner.setMaterial(new Material.Builder(inner.getMaterial()).ambient(1).build());
    var r = new Ray(new Point(0, 0, .75f), new Vector3(0, 0, -1));
    var c = w.colorAt(r);
    assertEquals(inner.getMaterial().getColor(), c);
  }

}
