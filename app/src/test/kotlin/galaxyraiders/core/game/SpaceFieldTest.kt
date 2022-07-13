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
import kotlin.test.assertNotEquals
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
  fun `it has its parameters initialized correctly`() {
    assertAll(
      "SpaceField should initialize all initial parameters correctly",
      { assertNotNull(spaceField) },
      { assertEquals(12, spaceField.width) },
      { assertEquals(8, spaceField.height) },
      { assertEquals(generator, spaceField.generator) },
    )
  }

  @Test
  fun `it defines its boundaries`() {
    assertAll(
      "SpaceField should initialize all initial parameters correctly",
      { assertNotNull(spaceField) },
      { assertEquals(0.0..12.0, spaceField.boundaryX) },
      { assertEquals(0.0..8.0, spaceField.boundaryY) },
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
  fun `it has a list of objects with ship, missiles and asteroids`() {
    val ship = spaceField.ship

    spaceField.generateMissile()
    val missile = spaceField.missiles.last()

    spaceField.generateAsteroid()
    val asteroid = spaceField.asteroids.last()

    val expectedSpaceObjects = listOf<SpaceObject>(
      ship, missile, asteroid
    )

    assertEquals(expectedSpaceObjects, spaceField.spaceObjects)
  }

  @Test
  fun `it can move its ship`() {
    val ship = spaceField.ship
    repeat(2) { ship.boostRight() }

    val expectedShipPosition = ship.center + ship.velocity

    spaceField.moveShip()

    assertEquals(expectedShipPosition, ship.center)
  }

  @Test
  fun `it does not move the ship outside its boundaries upward`() {
    val ship = spaceField.ship
    repeat(10) { ship.boostUp() }

    val expectedShipPosition = Point2D(
      x = ship.center.x,
      y = spaceField.boundaryY.endInclusive,
    )

    val distanceToTopBorder = spaceField.boundaryY.endInclusive - ship.center.y

    val repetitionsToGetOutOfMap = Math.ceil(
      distanceToTopBorder / Math.abs(ship.velocity.dy)
    ).toInt()

    repeat(repetitionsToGetOutOfMap) { spaceField.moveShip() }

    assertEquals(expectedShipPosition, ship.center)
  }

  @Test
  fun `it does not move the ship outside its boundaries downward`() {
    val ship = spaceField.ship
    repeat(10) { ship.boostDown() }

    val expectedShipPosition = Point2D(
      x = ship.center.x,
      y = spaceField.boundaryY.start,
    )

    val distanceToBottomBorder = ship.center.y - spaceField.boundaryY.start

    val repetitionsToGetOutOfMap = Math.ceil(
      distanceToBottomBorder / Math.abs(ship.velocity.dy)
    ).toInt()

    repeat(repetitionsToGetOutOfMap) { spaceField.moveShip() }

    assertEquals(expectedShipPosition, ship.center)
  }

  @Test
  fun `it does not move the ship outside its boundaries rightward`() {
    val ship = spaceField.ship
    repeat(10) { ship.boostRight() }

    val expectedShipPosition = Point2D(
      x = spaceField.boundaryX.endInclusive,
      y = ship.center.y,
    )

    val distanceToRightBorder = spaceField.boundaryX.endInclusive - ship.center.x

    val repetitionsToGetOutOfMap = Math.ceil(
      distanceToRightBorder / Math.abs(ship.velocity.dx)
    ).toInt()

    repeat(repetitionsToGetOutOfMap) { spaceField.moveShip() }

    assertEquals(expectedShipPosition, ship.center)
  }

  @Test
  fun `it does not move the ship outside its boundaries leftward`() {
    val ship = spaceField.ship
    repeat(10) { ship.boostLeft() }

    val expectedShipPosition = Point2D(
      x = spaceField.boundaryX.start,
      y = ship.center.y,
    )

    val distanceToLeftBorder = ship.center.x - spaceField.boundaryX.start

    val repetitionsToGetOutOfMap = Math.ceil(
      distanceToLeftBorder / Math.abs(ship.velocity.dx)
    ).toInt()

    repeat(repetitionsToGetOutOfMap) { spaceField.moveShip() }

    assertEquals(expectedShipPosition, ship.center)
  }

  @Test
  fun `it can move its missiles`() {
    spaceField.generateMissile()

    val missile = spaceField.missiles.last()
    val expectedMissilePosition = missile.center + missile.velocity

    spaceField.moveMissiles()

    assertEquals(expectedMissilePosition, missile.center)
  }

  @Test
  fun `it can move its asteroids`() {
    spaceField.generateAsteroid()

    val asteroid = spaceField.asteroids.last()
    val expectedAsteroidPosition = asteroid.center + asteroid.velocity

    spaceField.moveAsteroids()

    assertEquals(expectedAsteroidPosition, asteroid.center)
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

  @Test
  fun `it can remove missiles outside its boundary`() {
    spaceField.generateMissile()

    val missile = spaceField.missiles.last()

    val distanceToTopBorder = spaceField.boundaryY.endInclusive - missile.center.y
    val repetitionsToGetOutSpaceField = Math.ceil(
      distanceToTopBorder / Math.abs(missile.velocity.dy)
    ).toInt()

    repeat(repetitionsToGetOutSpaceField) { missile.move() }

    spaceField.trimMissiles()

    assertEquals(-1, spaceField.missiles.indexOf(missile))
  }

  @Test
  fun `it does not remove missiles inside its boundary`() {
    spaceField.generateMissile()

    val missile = spaceField.missiles.last()

    missile.move()

    spaceField.trimMissiles()

    assertNotEquals(-1, spaceField.missiles.indexOf(missile))
  }

  @Test
  fun `it can remove asteroids outside its boundary`() {
    spaceField.generateAsteroid()

    val asteroid = spaceField.asteroids.last()

    val distanceToBottomBorder = asteroid.center.y - spaceField.boundaryY.start
    val repetitionsToGetOutSpaceField = Math.ceil(
      distanceToBottomBorder / Math.abs(asteroid.velocity.dy)
    ).toInt()

    repeat(repetitionsToGetOutSpaceField) { asteroid.move() }

    spaceField.trimAsteroids()

    assertEquals(-1, spaceField.asteroids.indexOf(asteroid))
  }

  @Test
  fun `it does not remove asteroids inside its boundary`() {
    spaceField.generateAsteroid()

    val asteroid = spaceField.asteroids.last()

    asteroid.move()

    spaceField.trimAsteroids()

    assertNotEquals(-1, spaceField.asteroids.indexOf(asteroid))
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
