package ng.core

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

@ScalaJSDefined
trait OnInit extends js.Object {
  def ngOnInit(): Unit
}

@ScalaJSDefined
trait OnDestroy extends js.Object {
  def ngOnDestroy(): Unit
}

@ScalaJSDefined
trait AfterContentChecked extends js.Object {
  def ngAfterContentChecked(): Unit
}

@ScalaJSDefined
trait AfterContentInit extends js.Object {
  def ngAfterContentInit(): Unit
}

@ScalaJSDefined
trait OnChanges extends js.Object {
  def ngOnChanges(): Unit
}