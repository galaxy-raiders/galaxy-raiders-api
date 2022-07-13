@file:Suppress("UNUSED_PARAMETER") // <- REMOVE
package galaxyraiders.core.physics

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties("unit", "normal", "degree", "magnitude")
data class Vector2D(val dx: Double, val dy: Double) {
  override fun toString(): String {
    return "Vector2D(dx=$dx, dy=$dy)"
  }

  val magnitude: Double
    get() = INVALID_DOUBLE

  val radiant: Double
    get() = INVALID_DOUBLE

  val degree: Double
    get() = INVALID_DOUBLE

  val unit: Vector2D
    get() = INVALID_VECTOR

  val normal: Vector2D
    get() = INVALID_VECTOR

  operator fun times(scalar: Double): Vector2D {
    return INVALID_VECTOR
  }

  operator fun div(scalar: Double): Vector2D {
    return INVALID_VECTOR
  }

  operator fun times(v: Vector2D): Double {
    return INVALID_DOUBLE
  }

  operator fun plus(v: Vector2D): Vector2D {
    return INVALID_VECTOR
  }

  operator fun plus(p: Point2D): Point2D {
    return INVALID_POINT
  }

  operator fun unaryMinus(): Vector2D {
    return INVALID_VECTOR
  }

  operator fun minus(v: Vector2D): Vector2D {
    return INVALID_VECTOR
  }

  fun scalarProject(target: Vector2D): Double {
    return INVALID_DOUBLE
  }

  fun vectorProject(target: Vector2D): Vector2D {
    return INVALID_VECTOR
  }
}

operator fun Double.times(v: Vector2D): Vector2D {
  return INVALID_VECTOR
}
