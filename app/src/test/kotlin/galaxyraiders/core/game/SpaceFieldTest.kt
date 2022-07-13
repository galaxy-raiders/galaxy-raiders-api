package galaxyraiders.core.game

import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D
import galaxyraiders.helpers.AverageValueGeneratorStub
import galaxyraiders.helpers.DELTA
import galaxyraiders.helpers.getMinMaxAverageValueGeneratorStubs
import galaxyraiders.helpers.toStreamOfArguments
import galaxyraiders.ports.RandomGenerator
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@DisplayName("Given a space field")
class SpaceFieldTest {
  private val generator: RandomGenerator = AverageValueGeneratorStub()

  private val spaceField = SpaceField(
    width = 12,
    height = 8,
    generator = generator,
  )

  @Test
  fun `it has its parameters initialized correctly `() {
    assertAll(
      "SpaceField should initialize all initial parameters correctly",
      { assertNotNull(spaceField) },
      { assertEquals(12, spaceField.width) },
      { assertEquals(8, spaceField.height) },
      { assertEquals(generator, spaceField.generator) },
    )
  }

  @Test
  fun `it initializes the player's SpaceShip`() {
    assertAll(
      "SpaceField should initialize a ship with standard parameters",
      { assertNotNull(spaceField.ship) },
      { assertEquals(Point2D(6.0, 1.0), spaceField.ship.center) },
      { assertEquals(Vector2D(0.0, 0.0), spaceField.ship.velocity,) },
      { assertEquals(1.0, spaceField.ship.radius) },
      { assertEquals(10.0, spaceField.ship.mass) },
    )
  }

  @Test
  fun `it starts with no asteroids`() {
    assertAll(
      "SpaceField should initialize an empty list of asteroids",
      { assertNotNull(spaceField.asteroids) },
      { assertEquals(0, spaceField.asteroids.size) },
    )
  }

  @Test
  fun `it starts with no missiles`() {
    assertAll(
      "SpaceField should initialize an empty list of missiles",
      { assertNotNull(spaceField.missiles) },
      { assertEquals(0, spaceField.missiles.size) },
    )
  }

  @Test
  fun `it has a list of objects with ship, asteroids and missiles`() {
    val ship = spaceField.ship

    spaceField.generateAsteroid()
    val asteroid = spaceField.asteroids.last()

    spaceField.generateMissile()
    val missile = spaceField.missiles.last()

    val expectedSpaceObjects = listOf<SpaceObject>(
      ship, asteroid, missile
    )

    assertEquals(expectedSpaceObjects, spaceField.spaceObjects)
  }

  @Test
  fun `it can generate a new missile`() {
    val numMissiles = spaceField.missiles.size
    spaceField.generateMissile()

    assertEquals(numMissiles + 1, spaceField.missiles.size)
  }

  @Test
  fun `it generates a missile in the top of the ship`() {
    spaceField.generateMissile()

    val ship = spaceField.ship
    val missile = spaceField.missiles.last()

    assertAll(
      "SpaceField creates a new missile with restrictions",
      { assertEquals(ship.center.x, missile.center.x, DELTA) },
      { assertEquals(ship.center.y + ship.radius + missile.radius + 0.1, missile.center.y, DELTA) },
    )
  }

  @Test
  fun `it generates a missile with a fixed velocity`() {
    spaceField.generateMissile()

    val missile = spaceField.missiles.last()

    assertAll(
      "SpaceField creates a new missile with restrictions",
      { assertEquals(0.0, missile.velocity.dx, DELTA) },
      { assertEquals(1.0, missile.velocity.dy, DELTA) },
    )
  }

  @Test
  fun `it can generate a new asteroid`() {
    val numAsteroids = spaceField.asteroids.size
    spaceField.generateAsteroid()

    assertEquals(numAsteroids + 1, spaceField.asteroids.size)
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideSpaceFieldWithCornerCaseGeneratorArguments")
  fun `it generates a new asteroid in a random horizontal position at the top of the field `(spaceField: SpaceField) {
    spaceField.generateAsteroid()
    val asteroid = spaceField.asteroids.last()

    assertAll(
      "SpaceField creates a new asteroid with restrictions",
      { assertTrue(asteroid.center.x >= 0.0) },
      { assertTrue(asteroid.center.x <= spaceField.width) },
      { assertEquals(asteroid.center.y, spaceField.height.toDouble(), DELTA) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideSpaceFieldWithCornerCaseGeneratorArguments")
  fun `it generates a new asteroid with a random velocity downwards`(spaceField: SpaceField) {
    spaceField.generateAsteroid()
    val asteroid = spaceField.asteroids.last()

    assertAll(
      "SpaceField creates a new asteroid with restrictions",
      { assertTrue(asteroid.velocity.dx >= -0.5) },
      { assertTrue(asteroid.velocity.dx <= 0.5) },
      { assertTrue(asteroid.velocity.dy >= -2.0) },
      { assertTrue(asteroid.velocity.dy <= -1.0) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideSpaceFieldWithCornerCaseGeneratorArguments")
  fun `it generates a new asteroid with a random radius`(spaceField: SpaceField) {
    spaceField.generateAsteroid()
    val asteroid = spaceField.asteroids.last()

    assertAll(
      "SpaceField creates a new asteroid with restrictions",
      { assertTrue(asteroid.radius >= 0.5) },
      { assertTrue(asteroid.radius <= 2.0) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideSpaceFieldWithCornerCaseGeneratorArguments")
  fun `it generates a new asteroid with a random mass`(spaceField: SpaceField) {
    spaceField.generateAsteroid()
    val asteroid = spaceField.asteroids.last()

    assertAll(
      "SpaceField creates a new asteroid with restrictions",
      { assertTrue(asteroid.mass >= 500) },
      { assertTrue(asteroid.mass <= 1000) },
    )
  }

  private companion object {
    @JvmStatic
    fun provideSpaceFieldWithCornerCaseGeneratorArguments(): Stream<Arguments> {
      return getMinMaxAverageValueGeneratorStubs().map({
          generator ->
        SpaceField(width = 12, height = 8, generator = generator)
      }).toStreamOfArguments()
    }
  }
}
