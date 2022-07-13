package galaxyraiders.ports.ui

interface Controller {
  enum class PlayerCommand {
    MOVE_SHIP_UP,
    MOVE_SHIP_DOWN,
    MOVE_SHIP_LEFT,
    MOVE_SHIP_RIGHT,
    LAUNCH_MISSILE,
    PAUSE_GAME,
  }

  fun nextPlayerCommand(): PlayerCommand?
}
