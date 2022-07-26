package galaxyraiders.core.physics

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlin.math.atan2
import kotlin.math.sqrt

// linter estupido
const val FULL = 180

@JsonIgnoreProperties("unit", "normal", "degree", "magnitude")
data class Vector2D(val dx: Double, val dy: Double) {
  override fun toString(): String {
    return "Vector2D(dx=$dx, dy=$dy)"
  }

  val magnitude: Double
    get() = sqrt((this.dx * this.dx) + (this.dy * this.dy))

  val radiant: Double
    get() = atan2(dy, dx)

  val unit: Vector2D
    get() = Vector2D(this.dx / magnitude, this.dy / magnitude)

  val degree: Double
    get() = (radiant * FULL / Math.PI)

  val normal: Vector2D
    get() = Vector2D(unit.dy, -unit.dx)

  operator fun times(scalar: Double): Vector2D {
    return Vector2D(scalar * this.dx, scalar * this.dy)
  }

  operator fun div(scalar: Double): Vector2D {
    return Vector2D(this.dx / scalar, this.dy / scalar)
  }

  operator fun times(v: Vector2D): Double {
    return (v.dx * this.dx + v.dy * this.dy)
  }

  operator fun plus(v: Vector2D): Vector2D {
    return Vector2D(this.dx + v.dx, this.dy + v.dy)
  }

  operator fun plus(p: Point2D): Point2D {
    return Point2D(this.dx + p.x, this.dy + p.y)
  }

  operator fun unaryMinus(): Vector2D {
    return Vector2D(-this.dx, -this.dy)
  }

  operator fun minus(v: Vector2D): Vector2D {
    return Vector2D(this.dx - v.dx, this.dy - v.dy)
  }

  fun scalarProject(target: Vector2D): Double {
    return this * target.unit
  }

  fun vectorProject(target: Vector2D): Vector2D {
    return scalarProject(target) * (target.unit)
  }
}

operator fun Double.times(v: Vector2D): Vector2D {
  return Vector2D(this * v.dx, this * v.dy)
}
