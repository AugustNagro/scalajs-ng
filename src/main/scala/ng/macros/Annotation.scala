package ng.macros

import scala.collection.immutable.Seq
import scala.meta.Term

/** describes an annotation (eg: component, injectable) */
private[macros] case class Annotation(annotationType: String,
                                      annotationArgs: Seq[Term.Arg])
