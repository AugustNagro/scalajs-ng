package ng

import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.|

package object macros {

  // the right-hand side of a metadata like `"imports" -> ???`
  // can be any of the two below types.
  private[macros] type MetadataValue = js.Array[js.Any] | String
  private[macros] type MetadataName = String

  /**
    * Create a js.Array[js.Any] with less boilerplate
    * @param values
    * @return
    */
  def @@(values: js.Any*): js.Array[js.Any] = js.Array[js.Any](values: _*)

  /**
    * Implicitly converts a scala class to its javascript instance
    * @param clazz
    * @return a js.Any representing this class in the global namespace.
    */
  implicit def toJS(clazz: Class[_]): js.Any =
    clazz.getName.split('.').foldLeft(js.Dynamic.global)(_.selectDynamic(_))

}
