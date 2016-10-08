package ng.common

import ng.common.Location.Value
import ng.common.Location.Exception

import scala.scalajs
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("ng.common.Location")
@js.native
class Location(platformStrategy: LocationStrategy) extends js.Object {
  def path(includeHash: Boolean = false): String = js.native
  def isCurrentPathEqualTo(path: String, query: String = js.native): Boolean =
    js.native
  def normalize(url: String): String = js.native
  def prepareExternalUrl(url: String): String = js.native
  def go(path: String, query: String = js.native): Unit = js.native
  def forward(): Unit = js.native
  def back(): Unit = js.native
  def subscribe(onNext: Value => Unit,
                onThrow: Exception => Unit = js.native,
                onReturn: () => Unit = js.native): js.Any = js.native
}

@JSName("ng.common.Location")
@js.native
object Location extends js.Object {
  private type Value = js.Any
  private type Exception = js.Any
  def normalizeQueryParams(params: String): String = js.native
  def joinWithSlash(start: String, end: String): String = js.native
  def stripTrailingSlash(url: String): String = js.native
}
