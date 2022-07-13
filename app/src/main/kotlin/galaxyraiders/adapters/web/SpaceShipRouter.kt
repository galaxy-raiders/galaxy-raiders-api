package galaxyraiders.adapters.web

import galaxyraiders.ports.ui.Controller
import galaxyraiders.ports.ui.Controller.PlayerCommand
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.apibuilder.EndpointGroup
import io.javalin.http.Context
import io.javalin.http.HttpCode
import java.util.LinkedList
import java.util.Queue

class SpaceShipRouter : Router, Controller {
  var playerCommands: Queue<Controller.PlayerCommand> =
    LinkedList<Controller.PlayerCommand>()

  override val path = "/ship"

  override val endpoints = EndpointGroup {
    post("/commands", ::postPlayerCommand)
  }

  private fun postPlayerCommand(ctx: Context) {
    data class PlayerCommandRequest(val command: PlayerCommand)

    val request = ctx.bodyAsClass<PlayerCommandRequest>()

    playerCommands.add(request.command)
    ctx.status(HttpCode.OK)
  }

  override fun nextPlayerCommand(): Controller.PlayerCommand? {
    if (playerCommands.isEmpty()) return null
    return playerCommands.remove()
  }
}
