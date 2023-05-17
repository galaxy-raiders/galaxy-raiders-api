@file:Suppress("UNUSED_PARAMETER") // <- REMOVE
package galaxyraiders.core.physics

import kotlin.math.pow
import kotlin.math.sqrt

data class Point2D(val x: Double, val y: Double) {
  operator fun plus(p: Point2D): Point2D {
    return Point2D(x+p.x, y+p.y)
  }

  operator fun plus(v: Vector2D): Point2D {
    return Point2D(x+v.dx, y+v.dy)
  }

  override fun toString(): String {
    return "Point2D(x=$x, y=$y)"
  }

  fun toVector(): Vector2D {
    return Vector2D(x,y)
  }

  fun impactVector(p: Point2D): Vector2D {
    return Vector2D(Math.abs(this.x - p.x), Math.abs(this.y - p.y))
  }

  fun impactDirection(p: Point2D): Vector2D {
    return Vector2D(this.x-p.x, this.y-p.y)
  }

  fun contactVector(p: Point2D): Vector2D {
    return Vector2D(impactVector(p).normal.dx, impactVector(p).normal.dy)
  }

  fun contactDirection(p: Point2D): Vector2D {
    return Vector2D(this.x-p.x, this.y-p.y).normal
  }

  fun distance(p: Point2D): Double {
    val dist: Double = sqrt((this.x-p.x).pow(2)+(this.y-p.y).pow(2))
    return dist
  }
}
