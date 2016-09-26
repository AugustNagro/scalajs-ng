package ng.macros

import be.doeraene.sjsreflect.Reflect
import ng.AnnotationFacade

import scala.collection.immutable.Seq
import scala.meta.{Term, _}

/**
  * Processes the macros
  *
  */
private[ng] object NgMacroTools {

  /**
    * @param defn Initial Tree
    * @return meta.Stat replacing the original definition
    */
  def build(annotation: Option[Annotation], defn: Tree): Stat = {

    val q"""
         class ${ tname: Type.Name }[..$tparams] ..$ctorMods (..${params: Seq[Term.Param]}) extends ..${ctorcalls: Seq[Ctor.Call]} { ..${stats: Seq[Stat]} }
       """ = defn

    val className = tname.value
    val newCtorCalls = buildCtorCalls(ctorcalls)

    val objName = Term.Name(className)
    val annotations = buildAnnotations(annotation, stats)
    val parameters = buildParameters(params)
    val objExportName = "annots." + className + "_"

    q"""{
         @scalajs.js.annotation.JSExport
         @scalajs.js.annotation.ScalaJSDefined
         class $tname[..$tparams] ..$ctorMods (..$params) extends ..$newCtorCalls { ..$stats }

         @scalajs.js.annotation.JSExport($objExportName)
         @scalajs.js.annotation.ScalaJSDefined
         object $objName extends scalajs.js.Object {
           def annotations = $annotations
           def parameters = $parameters
         }
        }
       """
  }

  /**
    * Adds `scalajs.js.Object` and `ng.macros.NGAnnotation` to the ctor calls
    * @param oldCalls
    * @return
    */
  private def buildCtorCalls(oldCalls: Seq[Ctor.Call]): Seq[Ctor.Call] = {

    val jsObjCall = "scalajs.js.Object".parse[Ctor.Call].get
    val ngAnnotsCall = "ng.macros.NGAnnotation".parse[Ctor.Call].get

    jsObjCall +: oldCalls :+ ngAnnotsCall
  }


  //TODO check body for input/output annotations
  private def buildAnnotations(annotation: Option[Annotation], stats: Seq[Stat]): Term = {
    if(annotation.isEmpty) return s"scalajs.js.Array()".parse[Term].get


    val annotTypeName = annotation.get.annotationType
    val args = annotation.get.annotationArgs

    val mapping = args
      .map{arg =>

        // args should look like:
        // "test".->(some js.Any)
        val q"${key: Term}.->(${value: Term.Arg})" = arg

        val keySyntax = key.syntax
        // removes quotes from key
        val keyWithoutQuotes = keySyntax.substring(1, keySyntax.length - 1)

        keyWithoutQuotes + " = " + value.syntax
      }
      .mkString(",")

    val annot = s"scalajs.js.Array(new $annotTypeName(scalajs.js.Dynamic.literal($mapping)))"

    annot.parse[Term].get
  }

  private def buildParameters(params: Seq[Term.Param]): Term = {
    if(params.isEmpty) return s"scalajs.js.Array()".parse[Term].get

    val paramList = params.map(param => {
      val paramType = param.decltpe.get.toString()
      s"ng.macros.@#(classOf[$paramType])"
    }).mkString(",")

    s"scalajs.js.Array($paramList)".parse[Term].get
  }
}