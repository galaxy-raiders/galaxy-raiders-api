/**
 * @file: Vector2D.kt
 * This file contains the definition and operations of a Vector2D class,
 * which represents a vector in a 2-dimensional space.
 */

package galaxyraiders.core.physics

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * A vector in 2-dimensional space.
 *
 * @property dx The change in x of this vector.
 * @property dy The change in y of this vector.
 */
@JsonIgnoreProperties("unit", "normal", "degree", "magnitude")
data class Vector2D(val dx: Double, val dy: Double) {

  /**
   * String representation of this Vector2D.
   *
   * @return A string in the format "Vector2D(dx=..., dy=...)".
   */
  override fun toString(): String {
    return "Vector2D(dx=$dx, dy=$dy)"
  }

  /**
   * Calculates the magnitude of this Vector2D.
   */
  val magnitude: Double
    get() = Math.sqrt(dx * dx + dy * dy)

  /**
   * Calculates the angle of this Vector2D in radians.
   */
  val radiant: Double
    get() = Math.atan2(dy, dx)

  /**
   * Calculates the angle of this Vector2D in degrees.
   */
  val degree: Double
    get() = radiant * 180.0 / Math.PI

  /**
   * Returns a unit vector of this Vector2D.
   */
  val unit: Vector2D
    get() = this / magnitude

  /**
   * Returns a normal Vector2D to this Vector2D.
   */
  val normal: Vector2D
    get() = Vector2D(dy, -dx).unit

  /**
   * Multiplies this Vector2D by a scalar.
   *
   * @param scalar The scalar, represented as a Double precision number.
   * @return A Vector2D object with the result.
   */
  operator fun times(scalar: Double): Vector2D {
    return Vector2D(dx * scalar, dy * scalar)
  }

  /**
   * Divides this Vector2D by a scalar.
   *
   * @param scalar The scalar, represented as a Double precision number.
   * @return A Vector2D object with the result.
   */
  operator fun div(scalar: Double): Vector2D {
    return (1/scalar) * this
  }

  /**
   * Calculates the dot product of this Vector2D and another Vector2D.
   *
   * @param v A Vector2D object.
   * @return A Vector2D object with the result.
   */
  operator fun times(v: Vector2D): Double {
    return dx * v.dx + dy * v.dy
  }

  /**
   * Adds this Vector2D to another Vector2D.
   *
   * @param v A Vector2D object.
   * @return A Vector2D object with the result.
   */
  operator fun plus(v: Vector2D): Vector2D {
    return Vector2D(dx + v.dx, dy + v.dy)
  }

  /**
   * Adds this Vector2D to a Point2D.
   *
   * @param p A Point2D object.
   * @return A Point2D object with the result.
   */
  operator fun plus(p: Point2D): Point2D {
    return p + this
  }

  /**
   * Reflects this Vector2D object in both axis.
   *
   * @return A Vector2D object with the result.
   */
  operator fun unaryMinus(): Vector2D {
    return -1.0 * this
  }

  /**
   * Subtracts another Vector2D from this Vector2D.
   *
   * @param v A Vector2D object.
   * @return A Vector2D object with the result.
   */
    operator fun minus(v: Vector2D): Vector2D {
    return this + (-v)
  }

  /**
   * Calculates the project this Vector2D onto the target Vector2D and returns the scalar value of the projection.
   *
   * @param target The target Vector2D onto which this Vector2D is projected.
   * @return The scalar projection of this vector onto the target vector.
   */
  fun scalarProject(target: Vector2D): Double {
    return this * target.unit
  }

  /**
   * Calculates the project of this Vector2D onto the target Vector2D and returns the resultant Vector2D.
   *
   * @param target The target Vector2D onto which this Vector2D is projected.
   * @return The Vector2D projection of this Vector2D onto the target Vector2D.
   */
  fun vectorProject(target: Vector2D): Vector2D {
    return target.unit * scalarProject(target)
  }
}

/**
 * This auxiliary function multiplies a scalar with a vector.
 *
 * @param v The Vector2D to be multiplied.
 * @return The resulting Vector2D.
 */
operator fun Double.times(v: Vector2D): Vector2D {
  return v * this
}