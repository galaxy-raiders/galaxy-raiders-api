package galaxyraiders.core.game

import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D
import galaxyraiders.helpers.DELTA
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("Given an ship")
class SpaceShipTest {
  private val ship = SpaceShip(
    initialPosition = Point2D(1.0, 1.0),
    initialVelocity = Vector2D(1.0, 0.0),
    radius = 1.0,
    mass = 1.0
  )

  @Test
  fun `it has a type SpaceShip `() {
    assertEquals("SpaceShip", ship.type)
  }

  @Test
  fun `it has a symbol at `() {
    assertEquals('@', ship.symbol)
  }

  @Test
  fun `it shows the type SpaceShip when converted to String `() {
    assertTrue(ship.toString().contains("SpaceShip"))
  }

  @Test
  fun `it can boost its speed to the front `() {
    val initialVelocity = ship.velocity

    ship.boostUp()

    assertAll(
      "Ship velocity changes correctly",
      { assertEquals(initialVelocity.dx, ship.velocity.dx, DELTA) },
      { assertEquals(initialVelocity.dy + SpaceShipConfig.boost, ship.velocity.dy, DELTA) },
    )
  }

  @Test
  fun `it can boost its speed to the back `() {
    val initialVelocity = ship.velocity

    ship.boostDown()

    assertAll(
      "Ship velocity changes correctly",
      { assertEquals(initialVelocity.dx, ship.velocity.dx, DELTA) },
      { assertEquals(initialVelocity.dy - SpaceShipConfig.boost, ship.velocity.dy, DELTA) },
    )
  }

  @Test
  fun `it can boost its speed to the left `() {
    val initialVelocity = ship.velocity

    ship.boostLeft()

    assertAll(
      "Ship velocity changes correctly",
      { assertEquals(initialVelocity.dx - SpaceShipConfig.boost, ship.velocity.dx, DELTA) },
      { assertEquals(initialVelocity.dy, ship.velocity.dy, DELTA) },
    )
  }

  @Test
  fun `it can boost its speed to the right `() {
    val initialVelocity = ship.velocity

    ship.boostRight()

    assertAll(
      "Ship velocity changes correctly",
      { assertEquals(initialVelocity.dx + SpaceShipConfig.boost, ship.velocity.dx, DELTA) },
      { assertEquals(initialVelocity.dy, ship.velocity.dy, DELTA) },
    )
  }
}
