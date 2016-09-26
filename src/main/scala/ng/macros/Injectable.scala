package ng.macros

import scala.annotation.StaticAnnotation
import scala.collection.immutable.Seq
import scala.meta._

class Injectable extends StaticAnnotation {

  inline def apply(defn: Any): Any = meta {

    NgMacroTools.build(annotation = None, defn = defn)

  }

}
