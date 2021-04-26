package com.gregjandl.raytracer.rtlib;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SceneTest {
  @Test
  @DisplayName("Creating an empty World")
  void testCreateEmpty() {
    var scene = new Scene();
    assertEquals(0, scene.getObjectCount());
    assertEquals(0, scene.getLightCount());
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

    var scene = Scene.getDefault();
    assertEquals(l1, scene.getLight(0));
    assertEquals(s1, scene.getObject(0));
    assertEquals(s2, scene.getObject(1));
  }

  @Test
  @DisplayName("Adding a light to a scene")
  void testAddLight() {
    var light = new PointLight(Point.ORIGIN);
    var scene = new Scene();
    assertEquals(0, scene.getLightCount());
    scene.addLight(light);
    assertEquals(1, scene.getLightCount());
    assertEquals(light, scene.getLight(0));
  }

  @Test
  @DisplayName("Adding an object to a scene")
  void testAddObject() {
    var sphere = new Sphere().setMaterial(new Material.Builder().color(Color.BLUE).build());
    var scene = new Scene();
    assertEquals(0, scene.getObjectCount());
    scene.addObject(sphere);
    assertEquals(1, scene.getObjectCount());
    assertEquals(sphere, scene.getObject(0));
  }

  @Test
  @DisplayName("Intersect a world with a ray")
  void testIntersectRay() {
    var scene = Scene.getDefault();
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var xs = scene.intersect(r);
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
    var comps = new Scene.PreComps(i, r);
    assertEquals(i.getT(), comps.t);
    assertEquals(i.getObject(), comps.object);
    assertEquals(new Point(0, 0, -1), comps.point);
    assertEquals(new Vector3(0, 0, -1), comps.eyeVec);
    assertEquals(new Vector3(0, 0, -1), comps.normal);
  }

  @Test
  @DisplayName("Shading an intersection")
  void testShadingIntersection() {
    var scene = Scene.getDefault();
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var s = scene.getObject(0);
    var i = new IntersectionList.Intersection(4, s);
    var comps = new Scene.PreComps(i, r);
    var c = scene.shadeHit(comps);
    assertEquals(new Color(0.38066f, 0.47583f, 0.2855f), c);
  }

  @Test
  @DisplayName("Shading an intersection from the inside")
  void testShadingInsideIntersection() {
    var scene = Scene.getDefault();
    scene.setLights(List.of(new PointLight(new Point(0, 0.25f, 0))));
    var r = new Ray(new Point(0, 0, 0), new Vector3(0, 0, 1));
    var s = scene.getObject(1);
    var i = new IntersectionList.Intersection(0.5f, s);
    var comps = new Scene.PreComps(i, r);
    var c = scene.shadeHit(comps);
    assertEquals(new Color(0.90498f, 0.90498f, 0.90498f), c);
  }

  @Test
  @DisplayName("Shading an intersection in shadow")
  void testShadingIntersectionInShadow() {
    var scene = new Scene();
    scene.addLight(new PointLight(new Point(0, 0, -10)));
    scene.addObject(new Sphere());

    var s2 = new Sphere().setTransform(Matrix4x4.translation(0, 0, 10));
    scene.addObject(s2);
    var r = new Ray(new Point(0, 0, 5), new Vector3(0, 0, 1));
    var i = new IntersectionList.Intersection(4, s2);
    var c = scene.shadeHit(new Scene.PreComps(i, r));
    assertEquals(new Color(0.1f, 0.1f, 0.1f), c);
  }

  @Test
  @DisplayName("The color when a ray misses")
  void testColorWhenRayMisses() {
    var scene = Scene.getDefault();
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 1, 0));
    var c = scene.colorAt(r);
    assertEquals(Color.BLACK, c);
  }

  @Test
  @DisplayName("The color when a ray hits")
  void testColorWhenRayHits() {
    var scene = Scene.getDefault();
    var r = new Ray(new Point(0, 0, -5), new Vector3(0, 0, 1));
    var c = scene.colorAt(r);
    assertEquals(new Color(0.38066f, 0.47583f, 0.2855f), c);
  }

  @Test
  @DisplayName("The color with an intersection behind the ray")
  void testColorWithHitBehindRay() {
    var scene = Scene.getDefault();
    var outer = scene.getObject(0);
    outer.setMaterial(new Material.Builder(outer.getMaterial()).ambient(1).build());
    var inner = scene.getObject(1);
    inner.setMaterial(new Material.Builder(inner.getMaterial()).ambient(1).build());
    var r = new Ray(new Point(0, 0, .75f), new Vector3(0, 0, -1));
    var c = scene.colorAt(r);
    assertEquals(inner.getMaterial().getColor(), c);
  }

  @Nested
  @DisplayName("Shadows")
  class ShadowsTest {
    final Scene scene = Scene.getDefault();

    @Test
    @DisplayName("There is no shadow when nothing is collinear with the point and light")
    void testUnobstructed() {
      var point = new Point(0, 10, 0);
      assertFalse(scene.isShadowed(point, scene.getLight(0)));
    }

    @Test
    @DisplayName("There is a shadow when an object is between the point and the light")
    void testBetweenPointAndLight() {
      var point = new Point(10, -10, 10);
      assertTrue(scene.isShadowed(point, scene.getLight(0)));
    }

    @Test
    @DisplayName("There is no shadow when an object is behind the light")
    void testBehindLight() {
      var point = new Point(-20, 20, -20);
      assertFalse(scene.isShadowed(point, scene.getLight(0)));
    }

    @Test
    @DisplayName("There is no shadow when an object is behind the point")
    void testBehindPoint() {
      var point = new Point(-2, 2, -2);
      assertFalse(scene.isShadowed(point, scene.getLight(0)));
    }
  }
}
