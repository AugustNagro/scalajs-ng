package ng.macros

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.collection.immutable.Seq
import scala.meta._

/** Angular's @Injectable Annotation */
@compileTimeOnly("@Injectable not expanded")
class Injectable extends StaticAnnotation {

  inline def apply(defn: Any): Any = meta {

    NgMacroTools.build(annotation = None, defn = defn)

  }

}
