package galaxyraiders.helpers

import galaxyraiders.core.game.SpaceField
import galaxyraiders.ports.RandomGenerator
import galaxyraiders.ports.ui.Controller
import galaxyraiders.ports.ui.Controller.PlayerCommand
import galaxyraiders.ports.ui.Visualizer
import java.util.LinkedList
import java.util.Queue
import kotlin.math.roundToInt

class MinValueGeneratorStub : RandomGenerator {
  override fun generateProbability(): Double {
    return 0.0
  }

  override fun generateDoubleInInterval(min: Double, max: Double): Double {
    return min
  }

  override fun generateIntegerInRange(min: Int, max: Int): Int {
    return min
  }
}

class MaxValueGeneratorStub : RandomGenerator {
  override fun generateProbability(): Double {
    return 1.0
  }

  override fun generateDoubleInInterval(min: Double, max: Double): Double {
    return max
  }

  override fun generateIntegerInRange(min: Int, max: Int): Int {
    return max
  }
}

class AverageValueGeneratorStub : RandomGenerator {
  override fun generateProbability(): Double {
    return 0.5
  }

  override fun generateDoubleInInterval(min: Double, max: Double): Double {
    return min + (max - min) / 2.0
  }

  override fun generateIntegerInRange(min: Int, max: Int): Int {
    return min + ((max - min) / 2.0).roundToInt()
  }
}

fun getMinMaxAverageValueGeneratorStubs(): List<RandomGenerator> {
  return listOf<RandomGenerator>(
    MinValueGeneratorStub(),
    MaxValueGeneratorStub(),
    AverageValueGeneratorStub(),
  )
}

class VisualizerSpy : Visualizer {
  var numRenders = 0
    private set

  override fun renderSpaceField(field: SpaceField) {
    this.numRenders++
  }
}

class ControllerSpy : Controller {
  var playerCommands: Queue<PlayerCommand> =
    LinkedList<PlayerCommand>(
      listOf(
        PlayerCommand.MOVE_SHIP_LEFT,
        PlayerCommand.MOVE_SHIP_RIGHT,
        PlayerCommand.LAUNCH_MISSILE,
        PlayerCommand.PAUSE_GAME,
        PlayerCommand.PAUSE_GAME,
        PlayerCommand.MOVE_SHIP_UP,
        PlayerCommand.MOVE_SHIP_DOWN,
      )
    )

  override fun nextPlayerCommand(): PlayerCommand? {
    if (playerCommands.isEmpty()) return null
    return playerCommands.remove()
  }
}
