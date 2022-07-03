package galaxyraiders.physics

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DisplayName("Given a Point")
class PointTest {
  @DisplayName("it should be possible to create it for coordinates ")
  @ParameterizedTest(name = "({0}, {1})")
  @MethodSource("argumentsXAndYProvider")
  fun `it should be possible to create it for coordinates `(x: Double, y: Double) {
    val point = Point(x, y)
    assertNotNull(point)
    assertEquals(point.x, x)
    assertEquals(point.y, y)
  }

  private companion object {
    @JvmStatic
    fun argumentsXAndYProvider(): Stream<Arguments> {
      return Stream.of(
        Arguments.of(0.0, 0.0),
        Arguments.of(1.0, 2.0),
        Arguments.of(-1.0, -2.0),
      );
    }
  }
}
