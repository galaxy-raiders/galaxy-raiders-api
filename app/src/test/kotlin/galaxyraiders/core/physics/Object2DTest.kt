package galaxyraiders.core.physics

import galaxyraiders.helpers.DELTA
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@DisplayName("Given a 2D object")
class Object2DTest {
  private val o1 = Object2D(
    initialPosition = Point2D(1.0, 1.0),
    initialVelocity = Vector2D(1.0, 0.0),
    radius = 1.0,
    mass = 1.0
  )

  private val o2 = Object2D(
    initialPosition = Point2D(9.0, 1.0),
    initialVelocity = Vector2D(-1.0, 0.0),
    radius = 1.0,
    mass = 1.0
  )

  private val o3 = Object2D(
    initialPosition = Point2D(3.0, 1.0),
    initialVelocity = Vector2D(-1.0, 0.0),
    radius = 1.0,
    mass = 1.0
  )

  private val o4 = Object2D(
    initialPosition = Point2D(2.0, 1.0),
    initialVelocity = Vector2D(-1.0, 0.0),
    radius = 1.0,
    mass = 1.0
  )

  @Test
  fun `it has its parameters initialized correctly `() {
    assertAll(
      "Object2D should initialize all initial parameters correctly",
      { assertNotNull(o1) },
      { assertEquals(Point2D(1.0, 1.0), o1.center) },
      { assertEquals(Vector2D(1.0, 0.0), o1.velocity) },
      { assertEquals(1.0, o1.radius) },
      { assertEquals(1.0, o1.mass) },
    )
  }

  @Test
  fun `it can be moved from its initial position `() {
    o1.move()

    assertEquals(Point2D(2.0, 1.0), o1.center)
  }

  @Test
  fun `it can be moved from its initial position twice `() {
    o1.move()
    o1.move()

    assertEquals(Point2D(3.0, 1.0), o1.center)
  }

  @Test
  fun `it can change its velocity `() {
    val impulse = Vector2D(0.0, 1.0)
    o1.shift(impulse)

    assertEquals(Vector2D(1.0, 1.0), o1.velocity)
  }

  @Test
  fun `it can change its velocity and move accordingly `() {
    val force = Vector2D(0.0, 1.0)
    o1.shift(force)
    o1.move()

    assertEquals(Point2D(2.0, 2.0), o1.center)
  }

  @Test
  fun `it can determine its distance from a Point2D `() {
    val point = Point2D(1.0, 2.0)
    assertEquals(1.0, o1.distance(point), DELTA)
  }

  @Test
  fun `it can determine if it impacts a point within range `() {
    val pointWithinRange = Point2D(1.5, 1.5)
    assertTrue(o1.impacts(pointWithinRange))
  }

  @Test
  fun `it can determine if it impacts a point outside range `() {
    val pointOutsideRange = Point2D(2.5, 2.5)
    assertFalse(o1.impacts(pointOutsideRange))
  }

  @Test
  fun `it has distance zero from itself `() {
    assertEquals(0.0, o1.distance(o1), DELTA)
  }

  @Test
  fun `it can determine its distance from another Object2D inside range `() {
    assertEquals(0.0, o1.distance(o4), DELTA)
  }

  @Test
  fun `it can determine its distance from another Object2D in the limit range `() {
    assertEquals(0.0, o1.distance(o3), DELTA)
  }

  @Test
  fun `it can determine its distance from another Object2D outside range `() {
    assertEquals(6.0, o1.distance(o2), DELTA)
  }

  @Test
  fun `it never impacts itself `() {
    assertFalse(o1.impacts(o1))
  }

  @Test
  fun `it can determine if it impacts a Object2D inside range `() {
    assertTrue(o1.impacts(o4))
  }

  @Test
  fun `it can determine if it impacts a Object2D in the limit range `() {
    assertTrue(o1.impacts(o3))
  }

  @Test
  fun `it can determine if it impacts a Object2D outside range `() {
    assertFalse(o1.impacts(o2))
  }

  @Test
  fun `it shows it is a Object2D when converted to string `() {
    assertTrue(o1.toString().contains("Object2D"))
  }

  @Test
  fun `it fails if trying to collide with another Object2D with a coefficient of restitution smaller than zero `() {
    val invalidCoefficientRestitution = -1.0

    assertFailsWith<AssertionError>() {
      o1.collideWith(o3, invalidCoefficientRestitution)
    }
  }

  @Test
  fun `it fails if trying to collide with another Object2D with a coefficient of restitution larger than one `() {
    val invalidCoefficientRestitution = 2.0

    assertFailsWith<AssertionError>() {
      o1.collideWith(o3, invalidCoefficientRestitution)
    }
  }

  @Test
  fun `it changes its velocity in an ellastic collision with another Object2D `() {
    val ellasticCoefficientRestitution = 1.0
    o1.collideWith(o3, ellasticCoefficientRestitution)

    assertAll(
      "Object2D should change the velocity of both space objects",
      { assertEquals(Vector2D(-1.0, 0.0), o1.velocity) },
      { assertEquals(Vector2D(1.0, 0.0), o3.velocity) },
    )
  }

  @Test
  fun `it changes its velocity in an perfectly inellastic collision with another Object2D `() {
    val perfectlyInellasticCoefficientRestitution = 0.0
    o1.collideWith(o3, perfectlyInellasticCoefficientRestitution)

    assertAll(
      "Object2D should change the velocity of both space objects",
      { assertEquals(Vector2D(0.0, 0.0), o1.velocity) },
      { assertEquals(Vector2D(0.0, 0.0), o3.velocity) },
    )
  }

  @Test
  fun `it changes its velocity in an inellastic collision with another Object2D `() {
    val inellasticCoefficientRestitution = 0.5
    o1.collideWith(o3, inellasticCoefficientRestitution)

    assertAll(
      "Object2D should change the velocity of both space objects",
      { assertEquals(Vector2D(-0.5, 0.0), o1.velocity) },
      { assertEquals(Vector2D(0.5, 0.0), o3.velocity) },
    )
  }
}
