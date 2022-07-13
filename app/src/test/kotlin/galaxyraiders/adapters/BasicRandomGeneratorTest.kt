package galaxyraiders.adapters

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.assertAll
import kotlin.random.Random
import kotlin.test.assertTrue

@DisplayName("Given a basic random generator")
class BasicRandomGeneratorTest {
  private val rng = Random(seed = 42)

  private val generator = BasicRandomGenerator(rng)

  @RepeatedTest(10)
  fun `it generates a double within an interval`() {
    val min = 1.0
    val max = 2.0

    val generated: Double = generator.generateDoubleInInterval(min, max)

    assertAll(
      "BasicRandomGenerator creates a double with restrictions",
      { assertTrue(generated >= min) },
      { assertTrue(generated <= max) },
    )
  }

  @RepeatedTest(10)
  fun `it generates an integer within a range`() {
    val min = 1
    val max = 10

    val generated: Int = generator.generateIntegerInRange(min, max)

    assertAll(
      "BasicRandomGenerator creates an integer with restrictions",
      { assertTrue(generated >= min) },
      { assertTrue(generated <= max) },
    )
  }
}
