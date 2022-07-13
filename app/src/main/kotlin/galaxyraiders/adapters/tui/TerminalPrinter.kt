package galaxyraiders.adapters.tui

import galaxyraiders.core.game.SpaceField
import galaxyraiders.core.physics.Point2D
import galaxyraiders.ports.ui.Visualizer
import java.io.PrintStream

class TerminalPrinter(
  val output: PrintStream,
  val resolution: Int
) : Visualizer {
  override fun renderSpaceField(field: SpaceField) {
    val canvasHeight = field.height * resolution
    val canvasWidth = field.width * resolution

    for (i in 0..canvasHeight) {
      for (j in 0..canvasWidth) {
        val currentPoint = Point2D(
          x = j.toDouble() / resolution,
          y = (canvasHeight - i).toDouble() / resolution
        )

        val impacted = field.spaceObjects.filter { it.impacts(currentPoint) }

        val characer = when (impacted.size) {
          0 -> " "
          1 -> impacted.first().symbol
          else -> "#"
        }

        output.print(characer)
      }

      output.println()
    }

    output.println("======")
  }
}
