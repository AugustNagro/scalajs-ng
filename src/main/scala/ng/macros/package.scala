package ng

import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.|

package object macros {

  private[macros] type MetadataName = String
  // the right-hand side of a metadata like `"imports" -> ???`
  private[macros] type MetadataValue = js.Array[js.Any] | String

  /** Create a js.Array[js.Any] with less boilerplate */
  def @@(values: js.Any*): js.Array[js.Any] = js.Array[js.Any](values: _*)

  /** Implicitly converts a scala class to its javascript instance */
  implicit def toJS(clazz: Class[_]): js.Any =
    clazz.getName.split('.').foldLeft(js.Dynamic.global)(_.selectDynamic(_))

}
