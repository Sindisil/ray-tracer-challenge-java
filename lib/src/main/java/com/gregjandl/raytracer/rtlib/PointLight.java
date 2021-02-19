package com.gregjandl.raytracer.rtlib;

/**
 * Represents a point light source.
 */
public class PointLight {
  private final Point position;
  private final Color color;

  /**
   * Create a {@code PointLight} with the specified position and color/intensity.
   *
   * @param position position of the light
   * @param color    color of the light
   */
  public PointLight(Point position, Color color) {
    this.position = position;
    this.color = color;
  }

  /**
   * Create a white {@code PointLight} with the specified position.
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
}
