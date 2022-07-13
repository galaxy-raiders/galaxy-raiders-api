package galaxyraiders.core.physics

import galaxyraiders.helpers.DELTA
import galaxyraiders.helpers.listPairsDxAndDy
import galaxyraiders.helpers.listVectors
import galaxyraiders.helpers.toStreamOfArguments
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@DisplayName("Given a 2D vector")
class Vector2DTest {
  @ParameterizedTest(name = "({0}, {1})")
  @MethodSource("provideDxAndDyArguments")
  fun `it can be created for components `(dx: Double, dy: Double) {
    val vector = Vector2D(dx, dy)
    assertAll(
      "Vector should initialize all initial parameters correctly",
      { assertNotNull(vector) },
      { assertEquals(dx, vector.dx) },
      { assertEquals(dy, vector.dy) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideVectorArguments")
  fun `it can be multiplied by a scalar value `(vector: Vector2D) {
    val scalar: Double = 2.0
    val resultVector: Vector2D = vector * scalar

    assertEquals(Vector2D(vector.dx * 2.0, vector.dy * 2.0), resultVector)
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideVectorArguments")
  fun `it can be divided by a scalar value `(vector: Vector2D) {
    val scalar: Double = 2.0
    val resultVector: Vector2D = vector / scalar

    assertEquals(Vector2D(vector.dx * 0.5, vector.dy * 0.5), resultVector)
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideVectorArguments")
  fun `it can multiply a scalar value `(vector: Vector2D) {
    val scalar: Double = 2.0
    val resultVector: Vector2D = scalar * vector

    assertEquals(Vector2D(vector.dx * 2.0, vector.dy * 2.0), resultVector)
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideVectorArguments")
  fun `it can be added to another vector `(vector: Vector2D) {
    val anotherVector = Vector2D(dx = 1.0, dy = 2.0)
    val resultVector: Vector2D = vector + anotherVector

    assertEquals(Vector2D(vector.dx + 1.0, vector.dy + 2.0), resultVector)
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideVectorArguments")
  fun `it can be added to a point `(vector: Vector2D) {
    val point = Point2D(x = 1.0, y = 2.0)
    val resultPoint: Point2D = vector + point

    assertEquals(Point2D(vector.dx + 1.0, vector.dy + 2.0), resultPoint)
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideVectorArguments")
  fun `it can be inverted `(vector: Vector2D) {
    val resultVector: Vector2D = -vector

    assertEquals(Vector2D(-vector.dx, -vector.dy), resultVector)
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideVectorArguments")
  fun `it can be subtracted from another vector `(vector: Vector2D) {
    val anotherVector = Vector2D(dx = 1.0, dy = 2.0)
    val resultVector: Vector2D = vector - anotherVector

    assertEquals(Vector2D(vector.dx - 1.0, vector.dy - 2.0), resultVector)
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideVectorArguments")
  fun `it can be dot multiplied by another vector `(vector: Vector2D) {
    val anotherVector = Vector2D(dx = 1.0, dy = 2.0)
    val result: Double = vector * anotherVector

    assertEquals(vector.dx * 1.0 + vector.dy * 2.0, result)
  }

  @ParameterizedTest(name = "({0}, {1})")
  @MethodSource("provideVectorAndMagnitudeArguments")
  fun `it has a magnitude `(vector: Vector2D, magnitude: Double) {
    assertEquals(magnitude, vector.magnitude)
    assertTrue(vector.magnitude >= 0.0)
  }

  @ParameterizedTest(name = "({0}, {1})")
  @MethodSource("provideVectorAndRadiantArguments")
  fun `it has a radiant angle `(vector: Vector2D, radiant: Double) {
    assertAll(
      "Vector should have a radiant angle",
      { assertEquals(radiant, vector.radiant, DELTA) },
      { assertTrue(vector.radiant >= -Math.PI) },
      { assertTrue(vector.radiant <= Math.PI) },
    )
  }

  @ParameterizedTest(name = "({0}, {1})")
  @MethodSource("provideVectorAndDegreesArguments")
  fun `it has a degree angle `(vector: Vector2D, degree: Double) {
    assertAll(
      "Vector should have a degree angle",
      { assertEquals(degree, vector.degree, DELTA) },
      { assertTrue(vector.degree >= -180.0) },
      { assertTrue(vector.degree <= 180.0) },
    )
  }

  @ParameterizedTest(name = "({0}, {1})")
  @MethodSource("provideVectorAndVersorArguments")
  fun `it has a unit vector `(vector: Vector2D, versor: Vector2D) {
    assertAll(
      "Vectors should be equal",
      { assertEquals(versor.dx, vector.unit.dx, DELTA) },
      { assertEquals(versor.dy, vector.unit.dy, DELTA) },
    )
  }

  @ParameterizedTest(name = "({0}, {1})")
  @MethodSource("provideVectorAndNormalArguments")
  fun `it has a normal vector `(vector: Vector2D, normal: Vector2D) {
    assertAll(
      "Vectors should be equal",
      { assertEquals(normal.dx, vector.normal.dx, DELTA) },
      { assertEquals(normal.dy, vector.normal.dy, DELTA) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideVectorArguments")
  fun `it can be scalar projected into X axis `(vector: Vector2D) {
    val target = Vector2D(0.0, 25.0)
    val resultProjection: Double = vector.scalarProject(target)

    assertEquals(vector.dy, resultProjection)
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideVectorArguments")
  fun `it can be vector projected into X axis `(vector: Vector2D) {
    val target = Vector2D(0.0, 25.0)
    val resultProjection: Vector2D = vector.vectorProject(target)

    assertAll(
      "Vectors should be equal",
      // DELTA is required to check if equals 0.0 or -0.0
      { assertEquals(0.0, resultProjection.dx, DELTA) },
      { assertEquals(vector.dy, resultProjection.dy) },
    )
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideVectorArguments")
  fun `it can be scalar projected into Y axis `(vector: Vector2D) {
    val target = Vector2D(25.0, 0.0)
    val resultProjection: Double = vector.scalarProject(target)

    assertEquals(vector.dx, resultProjection)
  }

  @ParameterizedTest(name = "({0})")
  @MethodSource("provideVectorArguments")
  fun `it can be vector projected into Y axis `(vector: Vector2D) {
    val target = Vector2D(25.0, 0.0)
    val resultProjection: Vector2D = vector.vectorProject(target)

    assertAll(
      "Vectors should be equal",
      { assertEquals(vector.dx, resultProjection.dx) },
      // DELTA is required to check if equals 0.0 or -0.0
      { assertEquals(0.0, resultProjection.dy, DELTA) },
    )
  }

  @ParameterizedTest(name = "({0}, {1})")
  @MethodSource("provideVectorAndScalarProjectionArguments")
  fun `it can be scalar projected into transversal vector `(vector: Vector2D, projection: Double) {
    val target = Vector2D(25.0, 25.0)
    val resultProjection: Double = vector.scalarProject(target)

    assertEquals(projection, resultProjection, DELTA)
  }

  @ParameterizedTest(name = "({0}, {1})")
  @MethodSource("provideVectorAndVector2DProjectionArguments")
  fun `it can be scalar projected into transversal vector `(vector: Vector2D, projection: Vector2D) {
    val target = Vector2D(25.0, 25.0)
    val resultProjection: Vector2D = vector.vectorProject(target)

    assertAll(
      "Vectors should be equal",
      { assertEquals(projection.dx, resultProjection.dx, DELTA) },
      { assertEquals(projection.dy, resultProjection.dy, DELTA) },
    )
  }

  private companion object {
    @JvmStatic
    fun provideDxAndDyArguments(): Stream<Arguments> {
      return listPairsDxAndDy().toStreamOfArguments()
    }

    @JvmStatic
    fun provideVectorArguments(): Stream<Arguments> {
      return listVectors().toStreamOfArguments()
    }

    @JvmStatic
    fun provideVectorAndMagnitudeArguments(): Stream<Arguments> {
      val magnitudes = listOf(5.0, 13.0, 17.0, 25.0)

      return listVectors().zip(magnitudes).toStreamOfArguments()
    }

    @JvmStatic
    fun provideVectorAndRadiantArguments(): Stream<Arguments> {
      val radiants = listOf(0.93, 1.97, -2.06, -1.29)

      return listVectors().zip(radiants).toStreamOfArguments()
    }

    @JvmStatic
    fun provideVectorAndDegreesArguments(): Stream<Arguments> {
      val degrees = listOf(53.13, 112.62, -118.07, -73.74)

      return listVectors().zip(degrees).toStreamOfArguments()
    }

    @JvmStatic
    fun provideVectorAndVersorArguments(): Stream<Arguments> {
      val versors = listOf(
        Vector2D(dx = 0.6, dy = 0.8),
        Vector2D(dx = -0.38, dy = 0.92),
        Vector2D(dx = -0.47, dy = -0.88),
        Vector2D(dx = 0.28, dy = -0.96),
      )

      return listVectors().zip(versors).toStreamOfArguments()
    }

    @JvmStatic
    fun provideVectorAndNormalArguments(): Stream<Arguments> {
      val normals = listOf(
        Vector2D(dx = 0.8, dy = -0.6),
        Vector2D(dx = 0.92, dy = 0.38),
        Vector2D(dx = -0.88, dy = 0.47),
        Vector2D(dx = -0.96, dy = -0.28),
      )

      return listVectors().zip(normals).toStreamOfArguments()
    }

    @JvmStatic
    fun provideVectorAndScalarProjectionArguments(): Stream<Arguments> {
      val projections = listOf(4.95, 4.95, -16.26, -12.02)
      // (3.5, 3.5) (3.5, 3.5) (-11.5, -11.5) (-8.5, -8.5)
      return listVectors().zip(projections).toStreamOfArguments()
    }

    @JvmStatic
    fun provideVectorAndVector2DProjectionArguments(): Stream<Arguments> {
      val projections = listOf(
        Vector2D(dx = 3.5, dy = 3.5),
        Vector2D(dx = 3.5, dy = 3.5),
        Vector2D(dx = -11.5, dy = -11.5),
        Vector2D(dx = -8.5, dy = -8.5),
      )

      return listVectors().zip(projections).toStreamOfArguments()
    }
  }
}
