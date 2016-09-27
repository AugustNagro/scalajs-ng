package ng.macros

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.meta._

@compileTimeOnly("@Output not expanded")
class Output(outputAs: String = null) extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn
  }
}
