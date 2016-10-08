package ng.router

import ng.core.ModuleWithProviders
import ng.router.Routes.Routes

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("ng.router.RouterModule")
@js.native
object RouterModule extends js.Object {
  def forRoot(routes: Routes, config: ExtraOptions = js.native): ModuleWithProviders = js.native
  def forChild(routes: Routes): ModuleWithProviders = js.native
}
