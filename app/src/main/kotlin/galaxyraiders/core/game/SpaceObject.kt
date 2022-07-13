package galaxyraiders.core.game

import galaxyraiders.core.physics.Object2D
import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D

open class SpaceObject(
  val type: String,
  val symbol: Char,
  initialPosition: Point2D,
  initialVelocity: Vector2D,
  radius: Double,
  mass: Double
) :
  Object2D(initialPosition, initialVelocity, radius, mass) {
  override fun toString(): String {
    return "${this.type} at ${this.center}"
  }

  fun inBoundaries(
    boundaryX: ClosedFloatingPointRange<Double>,
    boundaryY: ClosedFloatingPointRange<Double>,
  ): Boolean {
    return boundaryX.contains(this.center.x) && boundaryY.contains(this.center.y)
  }
}
