package galaxyraiders.ports

interface RandomGenerator {
  fun generateProbability(): Double
  fun generateDoubleInInterval(min: Double, max: Double): Double
  fun generateIntegerInRange(min: Int, max: Int): Int
}
