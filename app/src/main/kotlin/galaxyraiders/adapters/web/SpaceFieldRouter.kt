package galaxyraiders.adapters.web

import galaxyraiders.core.game.Asteroid
import galaxyraiders.core.game.Missile
import galaxyraiders.core.game.SpaceField
import galaxyraiders.core.game.SpaceShip
import galaxyraiders.ports.ui.Visualizer
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.EndpointGroup
import io.javalin.http.Context

class SpaceFieldRouter : Router, Visualizer {
  data class SpaceFieldDTO(
    val ship: SpaceShip,
    val asteroids: List<Asteroid>,
    val missiles: List<Missile>,
  )

  var dto: SpaceFieldDTO? = null
    private set

  override val path = "/space-field"

  override val endpoints = EndpointGroup {
    get("/", ::getSpaceField)
  }

  private fun getSpaceField(ctx: Context) {
    ctx.json(this.dto ?: "{}")
  }

  override fun renderSpaceField(field: SpaceField) {
    this.dto = SpaceFieldDTO(
      ship = field.ship,
      asteroids = field.asteroids,
      missiles = field.missiles,
    )
  }
}
