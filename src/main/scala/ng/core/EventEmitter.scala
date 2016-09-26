package ng.core

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("ng.core.EventEmitter")
@js.native
class EventEmitter(isAsync: Boolean = js.native) extends js.Object {
  def emit(value: js.Any = js.native): Unit = js.native
  def subscribe(generatorOrNext: js.Any = js.native,
                error: js.Any = js.native,
                complete: js.Any = js.native): js.Any = js.native

}
