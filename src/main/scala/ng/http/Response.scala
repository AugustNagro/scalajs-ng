package ng.http

import org.scalajs.dom.experimental.ResponseType

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("ng.http.Response")
@js.native
class Response extends js.Object {
  val `type`: ResponseType = js.native
  val ok: Boolean = js.native
  val url: String = js.native
  val status: Int = js.native
  val statusText: String = js.native
  val bytesLoaded: Int = js.native
  val totalBytes: Int = js.native
  val headers: Headers = js.native
  val json: js.Dynamic = js.native

  override def toString: String = js.native

}
