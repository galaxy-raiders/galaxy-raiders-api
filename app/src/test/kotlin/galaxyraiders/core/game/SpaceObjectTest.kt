package galaxyraiders.core.game

import galaxyraiders.core.physics.Point2D
import galaxyraiders.core.physics.Vector2D
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@DisplayName("Given an object in space")
class SpaceObjectTest {
  private val so = SpaceObject(
    type = "Dummy",
    symbol = 'd',
    initialPosition = Point2D(1.0, 1.0),
    initialVelocity = Vector2D(1.0, 0.0),
    radius = 1.0,
    mass = 1.0
  )

  @Test
  fun `it has its parameters initialized correctly `() {
    assertAll(
      "SpaceObject should initialize all its parameters correctly",
      { assertNotNull(so) },
      { assertEquals("Dummy", so.type) },
      { assertEquals('d', so.symbol) },
      { assertEquals(Point2D(1.0, 1.0), so.center) },
      { assertEquals(Vector2D(1.0, 0.0), so.velocity) },
      { assertEquals(1.0, so.radius) },
      { assertEquals(1.0, so.mass) },
    )
  }

  @Test
  fun `it shows its type when converted to string `() {
    assertTrue(so.toString().contains(so.type))
  }
}
