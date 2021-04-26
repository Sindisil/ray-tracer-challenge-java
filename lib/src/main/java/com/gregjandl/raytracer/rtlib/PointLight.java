package com.gregjandl.raytracer.rtlib;

import java.util.Objects;

/** Represents a point light source. */
public class PointLight {
  private final Point position;
  private final Color color;

  /**
   * Create a {@code PointLight} with the specified position and color/intensity.
   *
   * @param position position of the light
   * @param color color of the light
   */
  public PointLight(Point position, Color color) {
    this.position = position;
    this.color = color;
  }

  /**
   * Create a white {@code PointLight} with the specified position.
   *
   * @param position position of the light
   */
  public PointLight(Point position) {
    this(position, Color.WHITE);
  }

  /**
   * Return this {@code PointLight}'s position.
   *
   * @return the light's position
   */
  public Point getPosition() {
    return position;
  }

  /**
   * Return this {@code PointLight}'s color
   *
   * @return the light's color
   */
  public Color getColor() {
    return color;
  }

  /**
   * Returns a string representation of this {@code PointLight}. This method is intended to be used
   * for debugging purposes; the representation may change, but will not be {@code null}.
   *
   * @return a string representation of this {@code PointLight}
   */
  @Override
  public String toString() {
    return "PointLight{position=" + position + ", color=" + color + '}';
  }

  /**
   * Compares this {@code PointLight} with the specified {@code Object} for equality.
   *
   * @param o {@code Object} to which this {@code PointLight} is to be compared.
   * @return {@code true} if and only if the specified {@code Object} is a {@code PointLight} whose
   *     position and color are equal to this {@code PointLight}'s
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PointLight that = (PointLight) o;
    return Objects.equals(position, that.position) && Objects.equals(color, that.color);
  }

  /**
   * Returns the hash code for this {@code PointLight}
   *
   * @return hash code for this {@code PointLight}
   */
  @Override
  public int hashCode() {
    return Objects.hash(position, color);
  }
}
