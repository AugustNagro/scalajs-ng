package ng.http

import ng.http.Headers.{Header, Name, Values}

import scala.scalajs
import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

@JSName("ng.http.Headers")
@scalajs.js.native
class Headers(headers: Headers | js.Object = js.native) extends js.Object {
  def fromResponseHeaderString(headersString: String): Headers = js.native
  def append(name: String, value: String): Unit = js.native
  def delete(name: String, value: String): Unit = js.native
  def forEach(fn: js.Function3[Values, Name, Header, Unit]): Unit = js.native
  def get(header: String): String = js.native
  def has(header: String): Boolean = js.native
  def keys(): js.Array[String] = js.native
  def set(header: String, value: String | js.Array[String]): Unit = js.native
  def values(): js.Array[js.Array[String]] = js.native
  def toJSON(): js.Object = js.native
  def getAll(header: String): js.Array[String] = js.native
}

object Headers {
  private type Values = js.Array[String]
  private type Name = String
  private type Header = js.Dictionary[js.Array[String]]
}
