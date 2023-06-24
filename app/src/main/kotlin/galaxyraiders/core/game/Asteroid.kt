package galaxyraiders.core.game

import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D

class Asteroid(
  initialPosition: Point2D,
  initialVelocity: Vector2D,
  radius: Double,
  mass: Double
) :
  SpaceObject("Asteroid", '.', initialPosition, initialVelocity, radius, mass) {
  fun explosion(): Explosion {
    return Explosion(
      initialPosition = Point2D(this.center.x, this.center.y),
      initialVelocity = Vector2D(0.0, 0.0),
      radius = this.radius,
      mass = 0.0,
    )
  }
}
