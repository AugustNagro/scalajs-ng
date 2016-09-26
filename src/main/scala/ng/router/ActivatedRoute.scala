package ng.router

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

@JSName("ng.router.ActivatedRoute")
@js.native
trait ActivatedRoute extends js.Object {
  val snapshot: js.Dynamic = js.native
  val url: js.Dynamic = js.native
  val params: js.Dynamic = js.native
  val queryParams: js.Dynamic = js.native
  val fragment: js.Dynamic = js.native
  val data: js.Dynamic = js.native
  val outlet: String = js.native
  val component: js.Any | String = js.native
  val routeConfig: Route = js.native
  val root: ActivatedRoute = js.native
  val parent: ActivatedRoute = js.native
  val firstChild: ActivatedRoute = js.native
  val children: js.Array[ActivatedRoute] = js.native
  val pathFromRoot: js.Array[ActivatedRoute] = js.native
  override def toString: String = js.native
}
