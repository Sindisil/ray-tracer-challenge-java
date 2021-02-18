package com.gregjandl.raytracer.rtlib;

/**
 * Represents a point light source.
 */
public class PointLight {
  private final Point position;
  private final Color color;

  /**
   * Create a new {@code PointLight} with the specified position and color/intensity.
   *
   * @param position position of the light in 3D space
   * @param color    color of the light
   */
  public PointLight(Point position, Color color) {
    this.position = position;
    this.color = color;
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
}
