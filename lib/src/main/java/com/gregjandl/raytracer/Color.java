package com.gregjandl.raytracer;

import java.util.Objects;

public class Color {
  private final float r;
  private final float g;
  private final float b;

  @SuppressWarnings("unused")
  public static final Color BLACK = new Color(0, 0, 0);
  @SuppressWarnings("unused")
  public static final Color WHITE = new Color(1, 1, 1);
  @SuppressWarnings("unused")
  public static final Color RED = new Color(1, 0, 0);
  @SuppressWarnings("unused")
  public static final Color GREEN = new Color(0, 1, 0);
  @SuppressWarnings("unused")
  public static final Color BLUE = new Color(0, 0, 1);
  @SuppressWarnings("unused")
  public static final Color YELLOW = new Color(1, 1, 0);
  @SuppressWarnings("unused")
  public static final Color MAGENTA = new Color(1, 0, 1);
  @SuppressWarnings("unused")
  public static final Color CYAN = new Color(0, 1, 1);
  @SuppressWarnings("unused")
  public static final Color LT_GRAY = new Color(.25f, .25f, .25f);
  @SuppressWarnings("unused")
  public static final Color GRAY = new Color(.5f, .5f, .5f);
  @SuppressWarnings("unused")
  public static final Color DK_GRAY = new Color(.75f, .75f, .75f);

  public Color(float r, float g, float b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  public float getR() { return r;}

  public float getG() { return g;}

  public float getB() { return b;}

  private float clamp(float val) {
    return val < 0 ? 0 : val > 1 ? 1 : val;
  }

  public java.awt.Color toAwtColor() {
    return new java.awt.Color(clamp(r), clamp(g), clamp(b));
  }

  @Override
  public String toString() {
    return "Color{r=" + r + ", g=" + g + ", b=" + b + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    Color color = (Color) o;
    return Utils.aboutEqual(color.r, r) && Utils.aboutEqual(color.g, g)
        && Utils.aboutEqual(color.b, b);
  }

  @Override
  public int hashCode() {
    return Objects.hash(r, g, b);
  }

  public Color add(Color c2) {
    return new Color(r + c2.r, g + c2.g, b + c2.b);
  }

  public Color subtract(Color c2) {
    return new Color(r - c2.r, g - c2.g, b - c2.b);
  }

  public Color multiply(float n) {
    return new Color(r * n, g * n, b * n);
  }

  public Color multiply(Color c2) {
    return new Color(r * c2.r, g * c2.g, b * c2.b);
  }
}
