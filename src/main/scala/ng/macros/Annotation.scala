package ng.macros

import scala.collection.immutable.Seq
import scala.meta.Term

case class Annotation(annotationType: String,
                      annotationArgs: Seq[Term.Arg])
