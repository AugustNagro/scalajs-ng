package ng.router

import ng.macros.OptionsObject

import scala.scalajs.js

@js.native
trait Route extends js.Object {
  val path: String = js.native
  val component: js.Any = js.native
  val pathMatch: String = js.native
  val redirectTo: String = js.native
  val outlet: String = js.native
  val canActivate: js.Array[js.Any] = js.native
  val canActivateChild: js.Array[js.Any] = js.native
  val canDeactivate: js.Array[js.Any] = js.native
  val canLoad: js.Array[js.Any] = js.native
  val data: js.Object = js.native
  val resolve: js.Any = js.native
  val children: js.Array[js.Any] = js.native
  val loadChildren: String = js.native
}

@OptionsObject
object Route {
  def apply(path: String,
            component: js.Any = null,
            pathMatch: String = null,
            redirectTo: String = null,
            outlet: String = null,
            canActivate: js.Array[js.Any] = null,
            canActivateChild: js.Array[js.Any] = null,
            canDeactivate: js.Array[js.Any] = null,
            canLoad: js.Array[js.Any] = null,
            data: js.Object = null,
            resolve: js.Any = null,
            children: js.Array[js.Any] = null,
            loadChildren: String = null): Route = null

}
