package ng.http

import rxscalajs.Observable

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName
import scala.scalajs.js.|

@JSName("ng.http.Http")
@js.native
class Http extends js.Object {
  def request(url: String | Request,
              options: RequestOptionsArgs = js.native): Observable[Response] =
    js.native

  def get(url: String,
          options: RequestOptionsArgs = js.native): Observable[Response] =
    js.native

  def post(url: String,
           body: js.Any,
           options: RequestOptionsArgs = js.native): Observable[Response] =
    js.native

  def put(url: String,
          body: js.Any,
          options: RequestOptionsArgs = js.native): Observable[Response] =
    js.native

  def delete(url: String,
             body: js.Any,
             options: RequestOptionsArgs = js.native): Observable[Response] =
    js.native

  def patch(url: String,
            body: js.Any,
            options: RequestOptionsArgs = js.native): Observable[Response] =
    js.native

  def head(url: String,
           options: RequestOptionsArgs = js.native): Observable[Response] =
    js.native

  def options(url: String,
              options: RequestOptionsArgs = js.native): Observable[Response] =
    js.native
}
