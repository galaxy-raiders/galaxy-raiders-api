package galaxyraiders.adapters.tui

import galaxyraiders.ports.ui.Controller
import galaxyraiders.ports.ui.Controller.PlayerCommand
import java.util.LinkedList
import java.util.Queue
import java.util.Scanner

class KeyboardReader : Controller {
  var playerCommands: Queue<PlayerCommand> =
    LinkedList<Controller.PlayerCommand>()

  val scanner = Scanner(System.`in`).useDelimiter("")

  fun readPlayerCommands() {
    while (this.scanner.hasNext()) {
      this.readPlayerCommand()
    }
  }

  fun readPlayerCommand() {
    val key = this.scanner.next().single()
    val command = this.characterToCommand(key)
    command?.let { playerCommands.add(it) }
  }

  private fun characterToCommand(key: Char) = when (key) {
    'w' -> PlayerCommand.MOVE_SHIP_UP
    'a' -> PlayerCommand.MOVE_SHIP_LEFT
    's' -> PlayerCommand.MOVE_SHIP_DOWN
    'd' -> PlayerCommand.MOVE_SHIP_RIGHT
    'p' -> PlayerCommand.PAUSE_GAME
    ' ' -> PlayerCommand.LAUNCH_MISSILE
    else -> null
  }

  override fun nextPlayerCommand(): PlayerCommand? {
    if (playerCommands.isEmpty()) return null
    return playerCommands.remove()
  }
}
