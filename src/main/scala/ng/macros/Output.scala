package ng.macros

import scala.annotation.{StaticAnnotation, compileTimeOnly}

@compileTimeOnly("@Output not expanded")
class Output(outputAs: String = null) extends StaticAnnotation
