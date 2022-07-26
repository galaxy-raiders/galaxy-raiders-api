@file:Suppress("MatchingDeclarationName")
package galaxyraiders

import galaxyraiders.adapters.BasicRandomGenerator
import galaxyraiders.adapters.tui.TextUserInterface
import galaxyraiders.adapters.web.WebUserInterface
import galaxyraiders.core.game.GameEngine
import kotlin.concurrent.thread
import kotlin.random.Random

object AppConfig {
  val config = Config("GR__APP__")

  val randomSeed = config.get<Int>("RANDOM_SEED")
  val operationMode = config.get<OperationMode>("OPERATION_MODE")
}

fun main() {
  val generator = BasicRandomGenerator(
    rng = Random(seed = AppConfig.randomSeed)
  )

  val ui = when (AppConfig.operationMode) {
    OperationMode.Text -> TextUserInterface()
    OperationMode.Web -> WebUserInterface()
  }

  println(ui)

  val (controller, visualizer) = ui.build()

  println(ui)
  val gameEngine = GameEngine(
    generator, controller, visualizer
  )
  println(ui)

  thread { gameEngine.execute() }

  ui.start()
}
