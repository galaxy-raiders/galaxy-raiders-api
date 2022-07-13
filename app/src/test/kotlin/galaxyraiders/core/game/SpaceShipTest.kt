package galaxyraiders.core.game

import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D
import galaxyraiders.helpers.DELTA
import galaxyraiders.helpers.toStreamOfArguments
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@DisplayName("Given an ship")
class SpaceShipTest {
  private val ship = SpaceShip(
    initialPosition = Point2D(1.0, 1.0),
    initialVelocity = Vector2D(0.0, 0.0),
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
  fun `it can boost its speed to the right `() {
    val initialVelocity = ship.velocity

    ship.boostRight()

    assertAll(
      "Ship velocity changes correctly",
      { assertEquals(initialVelocity.dx + SpaceShipConfig.boost, ship.velocity.dx, DELTA) },
      { assertEquals(initialVelocity.dy, ship.velocity.dy, DELTA) },
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

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideBoostArguments")
  fun `it can move within boundaries `(boostCommand: (SpaceShip) -> Unit) {
    val initialPosition = ship.center
    val buffer = 2 * SpaceShipConfig.boost

    val boundaryX = (initialPosition.x - buffer)..(initialPosition.x + buffer)
    val boundaryY = (initialPosition.y - buffer)..(initialPosition.y + buffer)

    boostCommand(ship)
    ship.move(boundaryX, boundaryY)

    val expectedPosition = initialPosition + ship.velocity

    assertAll(
      "Ship position changes correctly",
      { assertEquals(expectedPosition.x, ship.center.x, DELTA) },
      { assertEquals(expectedPosition.y, ship.center.y, DELTA) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideBoostArguments")
  fun `it cannot move outside boundaries `(boostCommand: (SpaceShip) -> Unit) {
    val initialPosition = ship.center
    val buffer = 0.5 * SpaceShipConfig.boost

    val boundaryX = (initialPosition.x - buffer)..(initialPosition.x + buffer)
    val boundaryY = (initialPosition.y - buffer)..(initialPosition.y + buffer)

    boostCommand(ship)
    ship.move(boundaryX, boundaryY)

    assertAll(
      "Ship position and velocity changes correctly",
      { assertTrue(ship.center.x >= boundaryX.start) },
      { assertTrue(ship.center.x <= boundaryX.endInclusive) },
      { assertTrue(ship.center.y >= boundaryY.start) },
      { assertTrue(ship.center.y <= boundaryY.endInclusive) },
      { assertEquals(0.0, ship.velocity.dx) },
      { assertEquals(0.0, ship.velocity.dy) },
    )
  }

  private companion object {
    @JvmStatic
    fun provideBoostArguments(): Stream<Arguments> {
      val boosts = listOf(
        SpaceShip::boostRight,
        SpaceShip::boostLeft,
        SpaceShip::boostUp,
        SpaceShip::boostDown,
      )

      return boosts.toStreamOfArguments()
    }
  }
}
