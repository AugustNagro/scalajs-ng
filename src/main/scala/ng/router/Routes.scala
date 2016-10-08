package ng.router

import scala.scalajs
import scala.scalajs.js

object Routes {
  type Routes = js.Array[js.Any]
  def apply(items: js.Any*): Routes = js.Array(items: _*)
}
