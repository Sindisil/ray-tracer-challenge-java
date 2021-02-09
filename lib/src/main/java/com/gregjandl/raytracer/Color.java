package com.gregjandl.raytracer;

import java.util.Objects;

/**
 * Represents an RGB color, using floats for the color components.
 * <p>
 * For consistency with the book, I created this class to represent colors, rather than using {@code
 * java.awt.Color}. This class allows the components to assume any float value, even those outside
 * the range (0.0 - 1.0), to allow more dynamic range during color calculations.
 * <p>
 * Methods are provided to get a {@code java.awt.Color} object or to clamp the RGB components to the
 * usual range of (0.0 - 1.0)
 */
public class Color {
  /**
   * The color black.
   */
  @SuppressWarnings("unused")
  public static final Color BLACK = new Color(0, 0, 0);
  /**
   * The color white.
   */
  @SuppressWarnings("unused")
  public static final Color WHITE = new Color(1, 1, 1);
  /**
   * The color red.
   */
  @SuppressWarnings("unused")
  public static final Color RED = new Color(1, 0, 0);

  /**
   * The color green.
   */
  @SuppressWarnings("unused")
  public static final Color GREEN = new Color(0, 1, 0);

  /**
   * The color blue.
   */
  @SuppressWarnings("unused")
  public static final Color BLUE = new Color(0, 0, 1);

  /**
   * The color yellow.
   */
  @SuppressWarnings("unused")
  public static final Color YELLOW = new Color(1, 1, 0);

  /**
   * The color magenta.
   */
  @SuppressWarnings("unused")
  public static final Color MAGENTA = new Color(1, 0, 1);

  /**
   * The color cyan.
   */
  @SuppressWarnings("unused")
  public static final Color CYAN = new Color(0, 1, 1);

  /**
   * The color light gray.
   */
  @SuppressWarnings("unused")
  public static final Color LT_GRAY = new Color(.25f, .25f, .25f);

  /**
   * The color gray.
   */
  @SuppressWarnings("unused")
  public static final Color GRAY = new Color(.5f, .5f, .5f);

  /**
   * The color light gray.
   */
  @SuppressWarnings("unused")
  public static final Color DK_GRAY = new Color(.75f, .75f, .75f);

  private final float r;
  private final float g;
  private final float b;

  /**
   * Creates an RGB color, using floats for the color components.
   * <p>
   * For consistency with the book, I created this class to represent colors, rather than using
   * {@code java.awt.Color}. This class allows the components to assume any float value, even those
   * outside the range [0.0 - 1.0], to allow more dynamic range during color calculations.
   * <p>
   * Methods are provided to get a {@code java.awt.Color} object or to clamp the RGB components to
   * the usual range of [0.0 - 1.0]
   *
   * @param r the Red component
   * @param g the Green component
   * @param b the Blue component
   */
  public Color(float r, float g, float b) {
    this.r = r;
    this.g = g;
    this.b = b;
  }

  /**
   * Constrains the given {@code float} value to the usual component range of [0.0 - 1.0].
   *
   * @param val value to clamp
   * @return the specified value, clamped to the range [0.0 - 1.0]
   */
  public static float clamp(float val) {
    return val < 0 ? 0 : val > 1 ? 1 : val;
  }

  /**
   * Returns the Red component of this {@code Color}.
   *
   * @return the Red component
   */
  public float getR() { return r;}

  /**
   * Returns the Green component of this {@code Color}.
   *
   * @return the Green component
   */
  public float getG() { return g;}

  /**
   * Returns the Blue component of this {@code Color}.
   *
   * @return the Blue component
   */
  public float getB() { return b;}

  /**
   * Constructs a {@code java.awt.Color} equivalent to this {@code Color}, after clamping the color
   * components to the valid range [0.0 - 1.1].
   *
   * @return the {@code java.awt.Color} equivalent to this {@code Color}
   * @see Color#clamp(float)
   */
  public java.awt.Color toAwtColor() {
    return new java.awt.Color(clamp(r), clamp(g), clamp(b));
  }

  /**
   * Returns a string representation of this {@code Color}. This method is intended to be used for
   * debugging purposes; the representation may change, but will not be {@code null}.
   *
   * @return a string representation of this {@code Color}
   */
  @Override
  public String toString() {
    return "Color{r=" + r + ", g=" + g + ", b=" + b + '}';
  }

  /**
   * Compares this {@code Color} to the specified {@code Object}.
   *
   * @param o {@code Object} to which this {@code Color} should be compared
   * @return {@code true} if and only if the specified {@code Object} is a {@code Color} whose
   * elements differ by less than {@link Utils#EPSILON}.
   * @see Utils#aboutEqual(double, double)
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    Color color = (Color) o;
    return Utils.aboutEqual(color.r, r) && Utils.aboutEqual(color.g, g)
        && Utils.aboutEqual(color.b, b);
  }

  /**
   * Returns the hash code for this {@code Point}
   *
   * @return hash code for this {@code Point}
   */
  @Override
  public int hashCode() {
    return Objects.hash(r, g, b);
  }

  /**
   * Return the {@code Color} resulting from adding the RGB components of the specified {@code
   * Color} to those of this {@code Color}.
   *
   * @param c2 the {@code Color} to be added to this {@code Color}
   * @return this + c2
   */
  public Color add(Color c2) {
    return new Color(r + c2.r, g + c2.g, b + c2.b);
  }

  /**
   * Return the {@code Color} resulting from subtracting the RGB components of the specified {@code
   * Color} from those of this {@code Color}.
   *
   * @param c2 the {@code Color} to be subtracted from this {@code Color}
   * @return this - c2
   */
  public Color subtract(Color c2) {
    return new Color(r - c2.r, g - c2.g, b - c2.b);
  }

  /**
   * Return the {@code Color} resulting from multiplying the RGB components of this {@code Color} by
   * the specified scalar multiplier.
   *
   * @param n the multiplier
   * @return this * m
   */
  public Color multiply(float n) {
    return new Color(r * n, g * n, b * n);
  }

  /**
   * Return the {@code Color} resulting from multiplying the RGB components of the specified {@code
   * Color} by those of this {@code Color}.
   *
   * @param c2 the {@code Color} to be multiplied by this {@code Color}
   * @return this * c2
   */
  public Color multiply(Color c2) {
    return new Color(r * c2.r, g * c2.g, b * c2.b);
  }
}
