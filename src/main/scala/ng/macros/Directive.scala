package ng.macros

import ng.core.{ComponentFacade, DirectiveFacade, NgModuleFacade}

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.meta._
import scala.collection.immutable.Seq

/** Angular's @Directive Annotation */
@compileTimeOnly("@Directive not expanded")
class Directive(decoratorArgs: (MetadataName, MetadataValue)*) extends StaticAnnotation {

  inline def apply(defn: Any): Any = meta {
    val q"new $_(..${annotationArgs: Seq[Term.Arg]})" = this

    val annotation = Annotation(classOf[DirectiveFacade].getName, annotationArgs)

    NgMacroTools.build(annotation = Some(annotation), defn = defn)

  }
}
