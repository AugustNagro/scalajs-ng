package ng.common

import scala.scalajs
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("ng.common.LocationStrategy")
@js.native
class LocationStrategy extends js.Object {
  def path(includesHash: Boolean = false): String = js.native
  def prepareExternalUrl(internal: String): String = js.native
  def pushState(state: js.Any, title: String, queryParams: String): Unit = js.native
  def replaceState(state: js.Any, title: String, queryParams: String): Unit = js.native
  def forward(): Unit = js.native
  def back(): Unit = js.native
  def onPopState(fn: LocationChangeListener): Unit = js.native
  def getBaseHref(): Unit = js.native
}
