package ng.router

import ng.core.ErrorHandler
import ng.macros.OptionsObject

import scala.scalajs
import scala.scalajs.js

@js.native
class ExtraOptions extends js.Object {
  val enableTracing: Boolean = js.native
  val useHash: Boolean = js.native
  val initialNavigation: Boolean = js.native
  val errorHandler: ErrorHandler = js.native
  val preloadingStrategy: js.Any = js.native
}
@OptionsObject
object ExtraOptions {
  def apply(enableTracing: Boolean = false, useHash: Boolean = false, initialNavigation: Boolean = false, errorHandler: ErrorHandler = null, preloadingStrategy: js.Any = null): ExtraOptions = null
}
