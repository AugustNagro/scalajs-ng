package ng.macros

import scala.annotation.{StaticAnnotation, compileTimeOnly}

@compileTimeOnly("@Input not expanded")
class Input(inputAs: String = null) extends StaticAnnotation

