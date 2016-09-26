package ng

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

package object macros {

  // TODO: Replace with Def macros when they become available?

  /**
    * Returns an array of js.Any representing the supplied items.
    * @param classes
    * @param jsVals Any additional js values
    */
  def @@(classes: Class[_]*)(jsVals: js.Any*): js.Array[js.Any] = (classes.map(@#) ++ jsVals).toJSArray

  /**
    * The singular of `@@`, accepting only class defs
    * @param clazz
    * @return a js.Any representing this class in the global namespace. w
    */
  def @#(clazz: Class[_]): js.Any = {
    clazz.getName.split('.').foldLeft(js.Dynamic.global)(_.selectDynamic(_))
  }

}
