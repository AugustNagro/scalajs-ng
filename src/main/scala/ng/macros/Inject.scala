package ng.macros

import scala.annotation.StaticAnnotation
import scala.meta._

class Inject(name: String = null) extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn
  }
}
