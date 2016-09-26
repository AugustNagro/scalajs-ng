package ng.router

import ng.core.ModuleWithProviders

import scala.scalajs.js

trait RouteDeclaration {
  val routes: Routes
  val routingProviders: js.Array[js.Any] = js.Array()
  val routing: ModuleWithProviders
}