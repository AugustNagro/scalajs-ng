package ng.macros

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.meta._

/**
  * Applies the annotations required by angular (and generated by the other macros).
  *
  * Decorate your launcher (that bootstraps angular and extends js.JSApp) with this macro
  *
  * Implementation Details: Uses reflection to add the required ".annotations" and ".parameters" properties to each function object whose scala class was decorated by a macro (ex. @Component, @Directive)
  */
@compileTimeOnly("@Bootstrap not expanded")
class Bootstrap extends StaticAnnotation {
  inline def apply(defn: Any): Any = meta {

    val q"object $tname extends ..$parents { ..$stats }" = defn

    // should evaluate something like:
    // app.AppModule.annotations = app.AppModule_().annotations
    // for every class that extends NGAnnotations
    val propertyApplication =
      q"""
         be.doeraene.sjsreflect.Reflect
         .getClassAndDescendants("ng.macros.NGAnnotation")
         // gets the fully qualified class-name
         .map(_._1)
         .withFilter(_ != "ng.macros.NGAnnotation")
         .foreach(classType => {
           val objName = "annots." + classType.split('.').last + "_()"

           val expr =
           s"$$classType.annotations = $$objName.annotations; $$classType.parameters = $$objName.parameters"

           scalajs.js.eval(expr)
         })
       """


    q"object $tname extends ..$parents { $propertyApplication; ..$stats }"
  }
}