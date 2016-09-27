package ng.macros

import ng.core.{ComponentFacade, DirectiveFacade}

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.meta._
import scala.collection.immutable.Seq

@compileTimeOnly("@Component not expanded")
class Component(decoratorArgs: (MetadataName, MetadataValue)*) extends StaticAnnotation {

  inline def apply(defn: Any): Any = meta {
    val q"new $_(..${annotationArgs: Seq[Term.Arg]})" = this

    val annotation = Annotation(classOf[ComponentFacade].getName, annotationArgs)

    NgMacroTools.build(annotation = Some(annotation), defn = defn)

  }
}

