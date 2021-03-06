package ng.macros

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.meta.inline
import scala.meta._

/** Angular's @Input Annotation */
@compileTimeOnly("@Input not expanded")
class Input(inputAs: String = null) extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn
  }
}

