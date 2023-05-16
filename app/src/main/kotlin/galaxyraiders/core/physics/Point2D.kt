package galaxyraiders.core.physics

import kotlin.math.hypot

data class Point2D(val x: Double, val y: Double) {

  operator fun plus(p: Point2D): Point2D {
    return Point2D(x + p.x, y + p.y)
  }

  operator fun plus(v: Vector2D): Point2D {
    return Point2D(x + v.dx, y + v.dy)
  }

  override fun toString(): String {
    return "Point2D(x=$x, y=$y)"
  }

  fun toVector(): Vector2D {
    return Vector2D(x, y)
  }

  fun impactVector(p: Point2D): Vector2D {
    return p.toVector() - toVector ()
  }

  fun impactDirection(p: Point2D): Vector2D {
    return impactVector(p).unit
  }

  fun contactVector(p: Point2D): Vector2D {
    return impactVector(p).normal
  }

  fun contactDirection(p: Point2D): Vector2D {
    return contactVector(p).unit
  }

  fun distance(p: Point2D): Double {
    return hypot(x - p.x, y - p.y)
  }
}
