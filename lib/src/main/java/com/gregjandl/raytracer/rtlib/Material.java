package com.gregjandl.raytracer.rtlib;

/**
 * Represents the material of an object in the Phong lighting model.
 */
public class Material {
  private final Color color;
  private final float ambient;
  private final float diffuse;
  private final float specular;
  private final float shininess;

  private Material(Builder builder) {
    color = builder.color;
    ambient = builder.ambient;
    diffuse = builder.diffuse;
    specular = builder.specular;
    shininess = builder.shininess;
  }

  /**
   * Returns the color component of this material.
   *
   * @return the color
   */
  public Color getColor() {
    return color;
  }

  /**
   * Returns the ambient component of this material.
   *
   * @return the ambient value
   */
  public float getAmbient() {
    return ambient;
  }

  /**
   * Returns the diffuse component of this material.
   *
   * @return the diffuse value
   */
  public float getDiffuse() {
    return diffuse;
  }

  /**
   * Returns the specular component of this material.
   *
   * @return the specular value
   */
  public float getSpecular() {
    return specular;
  }

  /**
   * Returns the shininess component of this material.
   *
   * @return the shininess value
   */
  public float getShininess() {
    return shininess;
  }

  /**
   * Builder for constructing instances of Material.
   * <table style="width: 25%;margin-top: 1em">
   *  <caption style="text-align: left">Default component values:</caption>
   *  <tr><td>{@code color}</td>     <td>{@code Color.WHITE}</td></tr>
   *  <tr><td>{@code ambient}</td>   <td>0.1</td></tr>
   *  <tr><td>{@code diffuse}</td>   <td>0.9</td></tr>
   *  <tr><td>{@code specular}</td>  <td>0.9</td></tr>
   *  <tr><td>{@code shininess}</td> <td>200.0</td></tr>
   * </table>
   */
  public static class Builder {
    private Color color = Color.WHITE;
    private float ambient = 0.1f;
    private float diffuse = 0.9f;
    private float specular = 0.9f;
    private float shininess = 200;

    /**
     * Creates a {@code Material#Builder} with default component values.
     */
    public Builder() {}

    /**
     * Sets this builder's color component to the specified {@code Color}
     *
     * @param c the color to use when building a {@code Material}
     * @return this builder, to facilitate a fluent interface
     */
    public Builder color(Color c) {
      color = c;
      return this;
    }

    /**
     * Sets this builder's ambient component to the specified value.
     *
     * @param val the ambient value to use when building a {@code Material}
     * @return this builder, to facilitate a fluent interface
     */
    public Builder ambient(float val) {
      ambient = val;
      return this;
    }

    /**
     * Sets this builder's diffuse component to the specified value.
     *
     * @param val the diffuse value to use when building a {@code Material}
     * @return this builder, to facilitate a fluent interface
     */
    public Builder diffuse(float val) {
      diffuse = val;
      return this;
    }

    /**
     * Sets this builder's specular component to the specified value.
     *
     * @param val the specular value to use when building a {@code Material}
     * @return this builder, to facilitate a fluent interface
     */
    public Builder specular(float val) {
      specular = val;
      return this;
    }

    /**
     * Sets this builder's shininess component to the specified value.
     *
     * @param val the shininess value to use when building a {@code Material}
     * @return this builder, to facilitate a fluent interface
     */
    public Builder shininess(float val) {
      shininess = val;
      return this;
    }

    /**
     * Returns a new {@code Material} instance, using the current component values.
     *
     * @return the new object
     */
    public Material build() {
      return new Material(this);
    }
  }
}
