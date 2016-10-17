package ng.macros

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.meta._

/** Angular's @Inject Annotation */
@compileTimeOnly("@Inject not expanded")
class Inject(name: String = null) extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {
    defn
  }
}
