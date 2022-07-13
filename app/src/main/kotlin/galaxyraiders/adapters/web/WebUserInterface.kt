package galaxyraiders.adapters.web

import galaxyraiders.Config
import galaxyraiders.ports.ui.Controller
import galaxyraiders.ports.ui.UserInterface
import galaxyraiders.ports.ui.Visualizer
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path

object WebUserInterfaceConfig {
  private val config = Config(
    prefix = "GR__ADAPTERS__WEB__WEB_USER_INTERFACE__"
  )

  val port = config.get<Int>("PORT")
  val allowedOrigins = config.get<String>("ALLOWED_ORIGINS")
}

class WebUserInterface : UserInterface {
  private val controller = SpaceShipRouter()
  private val visualizer = SpaceFieldRouter()

  private val routers: List<Router>
    get() = listOf(this.controller, this.visualizer)

  override fun build(): Pair<Controller, Visualizer> {
    return Pair(controller, visualizer)
  }

  override fun start() {
    Javalin.create {
      it.enableCorsForOrigin(WebUserInterfaceConfig.allowedOrigins)
    }.routes {
      get("/exit") { System.exit(0) }

      // Register routers
      this.routers.forEach { path("v1/" + it.path, it.endpoints) }
    }.start(WebUserInterfaceConfig.port)
  }
}
