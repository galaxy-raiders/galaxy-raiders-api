/**
 * @file: Point2D.kt
 * This file contains the definition and operations of a Point2D class,
 * which represents a point in a 2-dimensional space.
 */

package galaxyraiders.core.physics

/**
 * A point in the 2-dimensional space.
 *
 * @property x The x-coordinate of the point.
 * @property y The y-coordinate of the point.
 */
data class Point2D(val x: Double, val y: Double) {

  /**
   * Adds a Point2D to this Point2D.
   *
   * @param p The Point2D to be added.
   * @return A new Point2D that is the result of the addition.
   */
  operator fun plus(p: Point2D): Point2D {
    return Point2D(x + p.x, y + p.y)
  }

  /**
   * Adds a Vector2D to this Point2D.
   *
   * @param v The Vector2D to be added.
   * @return A new Point2D that is the result of the addition.
   */
  operator fun plus(v: Vector2D): Point2D {
    return Point2D(x + v.dx, y + v.dy)
  }

  /**
   * String representation of this Point2D.
   *
   * @return A string representing this Point2D.
   */
  override fun toString(): String {
    return "Point2D(x=$x, y=$y)"
  }

  /**
   * Calculates the Vector2D from origin to this Point2D.
   *
   * @return A Vector2D that represents this Point2D.
   */
  fun toVector(): Vector2D {
    return Vector2D(x, y)
  }

  /**
   * Calculates the impact vector at this Point2D.
   *
   * @param p Another Point2D object.
   * @return A Vector2D object that represents the impact vector.
   */
  fun impactVector(p: Point2D): Vector2D {
    // return Vector2D(p.x - x, p.y - y)
    return p.toVector() - this.toVector()
  }

  /**
   * Calculates the impact vector direction, which is the unit vector of the impact vector.
   *
   * @param p Another Point2D object.
   * @return A Vector2D object that represents the impact vector direction.
   */
  fun impactDirection(p: Point2D): Vector2D {
    return impactVector(p).unit
  }

  /**
   * Calculates the contact vector by using the impact vector. As per the tests, it should be unitarian.
   *
   * @param p Another Point2D object.
   * @return A Vector2D object that represents the contact vector.
   */
  fun contactVector(p: Point2D): Vector2D {
    return impactVector(p).normal
  }

  /**
   * Calculates the contact vector direction, which is simply the contact vector because it is unitarian.
   *
   * @param p Another Point2D object.
   * @return A Vector2D object that represents the contact vector direction.
   */
  fun contactDirection(p: Point2D): Vector2D {
    return contactVector(p)
  }

  /**
   * Calculates the Euclidean distance from this Point2D to another Point2D.
   *
   * @param p Another Point2D object.
   * @return The Euclidean distance with Double precision.
   */
  fun distance(p: Point2D): Double {
    val dx = x - p.x
    val dy = y - p.y
    return Math.sqrt(dx * dx + dy * dy)
  }
}
