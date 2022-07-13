package galaxyraiders.adapters.tui

import galaxyraiders.Config
import galaxyraiders.ports.ui.Controller
import galaxyraiders.ports.ui.UserInterface
import galaxyraiders.ports.ui.Visualizer

object TextUserInterfaceConfig {
  private val config = Config(
    prefix = "GR__ADAPTERS__TUI__TEXT_USER_INTERFACE__"
  )

  val resolution = config.get<Int>("RESOLUTION")
}

class TextUserInterface : UserInterface {
  private val controller = KeyboardReader()

  private val visualizer = TerminalPrinter(
    output = System.out,
    resolution = TextUserInterfaceConfig.resolution,
  )

  override fun build(): Pair<Controller, Visualizer> {
    return Pair(controller, visualizer)
  }

  override fun start() {
    controller.readPlayerCommands()
  }
}
