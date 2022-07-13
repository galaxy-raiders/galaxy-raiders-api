package galaxyraiders.ports.ui

import galaxyraiders.core.game.SpaceField

interface Visualizer {
  fun renderSpaceField(field: SpaceField)
}
