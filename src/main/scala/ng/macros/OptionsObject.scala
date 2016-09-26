package ng.macros

import scala.annotation.{StaticAnnotation, compileTimeOnly}
import scala.meta._
import scala.collection.immutable.Seq
import scala.scalajs.js.|

/**
  * For internal use only; reduces boilerplate for building options object facades.
  * This should be replaced by something like angulate2's `@Data` annotation when
  * scalameta/paradise macros allow expansion of a case class into a trait + object
  *
  * false and null should be the defaults for apply method params
  */
@compileTimeOnly("@OptionsObject not expanded")
private[ng] class OptionsObject extends StaticAnnotation {

  inline def apply(defn: Any): Any = meta {
    val q"object ${objName: Term.Name} extends ${template: Template}" = defn

    val traitName = Type.Name(objName.value)

    val applyStat = template.stats.get.head

    val q"def apply(..${params: Seq[Term.Param]}): $tpt = $body" = applyStat

    val applyParams = params.map(param => {
      val paramName = param.name.value
      val paramType = param.decltpe.get.syntax
      paramType match {
        case "Boolean" => s"obj.$paramName = $paramName"
        case _ => s"if($paramName != null)(obj.$paramName = $paramName)"
      }
    }).mkString(";")

    val defBody =
      s"""
         {
           val obj = scalajs.js.Dynamic.literal()
           $applyParams
           obj.asInstanceOf[$traitName]
         }
       """.parse[Term].get

    q"""
       object $objName {
         def apply(..$params): $traitName = $defBody
       }
     """
  }
}
