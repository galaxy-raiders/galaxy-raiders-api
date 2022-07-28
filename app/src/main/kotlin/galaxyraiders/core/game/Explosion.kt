package galaxyraiders.core.game

import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D

class Explosion(
  val initialPosition: Point2D,
  initialVelocity: Vector2D,
  radius: Double,
  mass: Double,
  var counter: Double
) :
  SpaceObject("Explosion", '*', initialPosition, initialVelocity, radius, mass) {

    fun counterUp(): Boolean {
      if (this.counter > 0) return true
      return false
    }

    fun counterDown() {
      this.counter--
    }
  }
