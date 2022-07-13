package galaxyraiders.adapters

import galaxyraiders.ports.RandomGenerator
import kotlin.random.Random

class BasicRandomGenerator(val rng: Random) : RandomGenerator {
  override fun generateProbability(): Double {
    return this.rng.nextDouble()
  }

  override fun generateDoubleInInterval(min: Double, max: Double): Double {
    return min + this.rng.nextDouble() * (max - min)
  }

  override fun generateIntegerInRange(min: Int, max: Int): Int {
    return (min..max).random(this.rng)
  }
}
