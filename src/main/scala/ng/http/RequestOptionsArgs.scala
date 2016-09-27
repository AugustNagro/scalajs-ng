package ng.http

import ng.macros.OptionsObject

import scala.scalajs.js

@js.native
trait RequestOptionsArgs extends js.Object {
  val url: String = js.native
  val method: String = js.native
  val search: String = js.native
  val headers: Headers = js.native
  val body: js.Any = js.native
  val withCredentials: Boolean = js.native
  val responseType: String = js.native
}

  @OptionsObject
object RequestOptionsArgs {
  def apply(
      url: String = null,
      method: String = null,
      search: String = null,
      headers: Headers = null,
      body: js.Any = null,
      withCredentials: Boolean = false,
      responseType: String = null
  ): RequestOptionsArgs = null
}
