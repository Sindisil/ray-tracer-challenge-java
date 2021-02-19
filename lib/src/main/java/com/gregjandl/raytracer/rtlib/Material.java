package com.gregjandl.raytracer.rtlib;

import java.util.Objects;

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
   * Returns the color of this material.
   *
   * @return the color
   */
  public Color getColor() {
    return color;
  }

  /**
   * Returns the ambient reflectivity (i.e., background lighting) of this material.
   * <p>Typically in the range [0.0,1.0].</p>
   *
   * @return the ambient reflectivity
   */
  public float getAmbient() {
    return ambient;
  }

  /**
   * Returns the diffuse reflectivity (i.e., matte surface reflection) of this material.
   * <p>Typically in the range [0.0,1.0].</p>
   *
   * @return the diffuse reflectivity
   */
  public float getDiffuse() {
    return diffuse;
  }

  /**
   * Returns the specular reflectivity of this material.
   * <p>Typically in the range [0.0,1.0].</p>
   *
   * @return the specular reflectivity
   */
  public float getSpecular() {
    return specular;
  }

  /**
   * Returns the shininess component of this material, controlling the size of the specular
   * highlight.
   * <p>
   * Typically in the range [10.0,200.0], with smaller values resulting in a larger highlight.
   * </p>
   *
   * @return the shininess value
   */
  public float getShininess() {
    return shininess;
  }

  /**
   * Compares this {@code Material} to the specified {@code object} for equality.
   * @param o the object to compare
   * @return {@code true} if and only if the object is a {@code Material} with the same Color
   * and with reflection fields differing by less than {@link Utils#EPSILON} from those of this
   * object.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) { return true; }
    if (o == null || getClass() != o.getClass()) { return false; }
    Material material = (Material) o;
    return Utils.aboutEqual(material.ambient, ambient)
        && Utils.aboutEqual(material.diffuse, diffuse)
        && Utils.aboutEqual(material.specular, specular)
        && Utils.aboutEqual(material.shininess, shininess)
        && color.equals(material.color);
  }

  @Override
  public int hashCode() {
    return Objects.hash(color, ambient, diffuse, specular, shininess);
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
