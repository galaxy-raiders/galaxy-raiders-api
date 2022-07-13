package galaxyraiders.core.game

import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("Given an asteroid")
class AsteroidTest {
  private val asteroid = Asteroid(
    initialPosition = Point2D(1.0, 1.0),
    initialVelocity = Vector2D(1.0, 0.0),
    radius = 1.0,
    mass = 1.0
  )

  @Test
  fun `it has a type Asteroid `() {
    assertEquals("Asteroid", asteroid.type)
  }

  @Test
  fun `it has a symbol dot `() {
    assertEquals('.', asteroid.symbol)
  }

  @Test
  fun `it shows the type Asteroid when converted to String `() {
    assertTrue(asteroid.toString().contains("Asteroid"))
  }
}
