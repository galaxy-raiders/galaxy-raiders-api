package galaxyraiders.core.game

import galaxyraiders.Config
import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D

object SpaceShipConfig {
  private val config = Config(prefix = "GR__CORE__GAME__SPACE_SHIP__")

  val boost = config.get<Double>("BOOST")
}

class SpaceShip(
  initialPosition: Point2D,
  initialVelocity: Vector2D,
  radius: Double,
  mass: Double
) :
  SpaceObject("SpaceShip", '@', initialPosition, initialVelocity, radius, mass) {
  fun boostUp() {
    this.velocity += Vector2D(dx = 0.0, dy = SpaceShipConfig.boost)
  }

  fun boostDown() {
    this.velocity += Vector2D(dx = 0.0, dy = -SpaceShipConfig.boost)
  }

  fun boostLeft() {
    this.velocity += Vector2D(dx = -SpaceShipConfig.boost, dy = 0.0)
  }

  fun boostRight() {
    this.velocity += Vector2D(dx = SpaceShipConfig.boost, dy = 0.0)
  }
}
