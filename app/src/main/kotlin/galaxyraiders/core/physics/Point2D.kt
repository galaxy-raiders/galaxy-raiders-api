@file:Suppress("UNUSED_PARAMETER") // <- REMOVE
package galaxyraiders.core.physics

data class Point2D(val x: Double, val y: Double) {
  operator fun plus(p: Point2D): Point2D {
    return INVALID_POINT
  }

  operator fun plus(v: Vector2D): Point2D {
    return INVALID_POINT
  }

  override fun toString(): String {
    return "Point2D(x=$x, y=$y)"
  }

  fun toVector(): Vector2D {
    return INVALID_VECTOR
  }

  fun impactVector(p: Point2D): Vector2D {
    return INVALID_VECTOR
  }

  fun impactDirection(p: Point2D): Vector2D {
    return INVALID_VECTOR
  }

  fun contactVector(p: Point2D): Vector2D {
    return INVALID_VECTOR
  }

  fun contactDirection(p: Point2D): Vector2D {
    return INVALID_VECTOR
  }

  fun distance(p: Point2D): Double {
    return INVALID_DOUBLE
  }
}
