package galaxyraiders.adapters.web

import io.javalin.apibuilder.EndpointGroup

interface Router {
  val path: String
  val endpoints: EndpointGroup
}
