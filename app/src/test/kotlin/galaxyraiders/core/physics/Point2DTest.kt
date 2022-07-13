package galaxyraiders.core.physics

import galaxyraiders.helpers.DELTA
import galaxyraiders.helpers.listPairPoints
import galaxyraiders.helpers.listPairsXAndY
import galaxyraiders.helpers.listPoints
import galaxyraiders.helpers.toStreamOfArguments
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DisplayName("Given a 2D point")
class Point2DTest {
  @ParameterizedTest(name = "({0}, {1})")
  @MethodSource("provideXAndYArguments")
  fun `it should be possible to create it for coordinates `(x: Double, y: Double) {
    val point = Point2D(x, y)

    assertAll(
      "Point should initialize all initial parameters correctly",
      { assertNotNull(point) },
      { assertEquals(x, point.x) },
      { assertEquals(y, point.y) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("providePointArguments")
  fun `it can be added to another point `(point: Point2D) {
    val anotherPoint = Point2D(1.0, 2.0)
    val resultPoint: Point2D = point + anotherPoint

    assertAll(
      "Points should be equal",
      { assertEquals(point.x + 1.0, resultPoint.x) },
      { assertEquals(point.y + 2.0, resultPoint.y) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("providePointArguments")
  fun `it can be added to a vector `(point: Point2D) {
    val vector = Vector2D(1.0, 2.0)
    val resultPoint: Point2D = point + vector

    assertAll(
      "Points should be equal",
      { assertEquals(point.x + 1.0, resultPoint.x) },
      { assertEquals(point.y + 2.0, resultPoint.y) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("providePointArguments")
  fun `it can be converted to a vector `(point: Point2D) {
    val vector: Vector2D = point.toVector()

    assertAll(
      "Points should be equal",
      { assertEquals(point.x, vector.dx) },
      { assertEquals(point.y, vector.dy) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("providePointArguments")
  fun `it can calculate an impact vector to another point `(point: Point2D) {
    val anotherPoint = Point2D(3.0, 4.0)
    val impactVector: Vector2D = point.impactVector(anotherPoint)

    assertAll(
      "Points should be equal",
      { assertEquals(Math.abs(point.x - 3.0), impactVector.dx, DELTA) },
      { assertEquals(Math.abs(point.y - 4.0), impactVector.dy, DELTA) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("providePointArguments")
  fun `it can calculate a contact vector to another point `(point: Point2D) {
    val anotherPoint = Point2D(3.0, 4.0)
    val contactVector: Vector2D = point.contactVector(anotherPoint)
    val impactVector: Vector2D = point.impactVector(anotherPoint)

    assertAll(
      "Points should be equal",
      { assertEquals(impactVector.normal.dx, contactVector.dx, DELTA) },
      { assertEquals(impactVector.normal.dy, contactVector.dy, DELTA) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("providePointArguments")
  fun `it can calculate a contact direction to another point `(point: Point2D) {
    val anotherPoint = Point2D(3.0, 4.0)
    val contactVersor: Vector2D = point.contactDirection(anotherPoint)
    val impactVersor: Vector2D = point.impactDirection(anotherPoint)

    assertAll(
      "Points should be equal",
      { assertEquals(impactVersor.normal.dx, contactVersor.dx, DELTA) },
      { assertEquals(impactVersor.normal.dy, contactVersor.dy, DELTA) },
    )
  }

  @ParameterizedTest(name = "({0}, {1}, {2})")
  @MethodSource("provideTwoPointArgumentsAndDistance")
  fun `it can have its distance calculated to another point `(p1: Point2D, p2: Point2D, distance: Double) {
    val calculatedDistance: Double = p1.distance(p2)

    assertEquals(distance, calculatedDistance, DELTA)
  }

  private companion object {
    @JvmStatic
    fun provideXAndYArguments(): Stream<Arguments> {
      return listPairsXAndY().toStreamOfArguments()
    }

    @JvmStatic
    fun providePointArguments(): Stream<Arguments> {
      return listPoints().toStreamOfArguments()
    }

    @JvmStatic
    fun provideTwoPointArgumentsAndDistance(): Stream<Arguments> {
      val distances = listOf(
        0.0, 2.24, 2.24, 2.24, 2.24,
        2.24, 0.0, 2.0, 4.47, 4.0,
        2.24, 2.0, 0.0, 4.0, 4.47,
        2.24, 4.47, 4.0, 0.0, 2.0,
        2.24, 4.0, 4.47, 2.0, 0.0,
      )

      return listPairPoints().zip(distances) {
          (p1, p2), distance ->
        Triple(p1, p2, distance)
      }.toStreamOfArguments()
    }
  }
}
