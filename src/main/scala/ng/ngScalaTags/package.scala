package ng
import scalatags.Text.all._

package object ngScalaTags {

  // Router
  def routerLink(url: String, isActive: Boolean = true) =
    tag("a")(attr("routerLink") := url, attr("routerLinkActive") := isActive)
  val routerOutlet = tag("router-outlet")

  // Data-binding

  def ngRef(id: String) = attr(s"ref-$id") := ""

  /**
    * One-way binding from data-source to view target
    *
    * Use `ngBind("target") := "expression"`
    * In-place of `[target]="expression"`
    * @param target the data-source
    * @return
    */
  def ngBind(target: String) = attr(s"bind-$target")

  /**
    * One-way binding from view-target to data-source
    *
    * Use `ngOn("target") := "statement"`
    * In-place of `(target)="statement"`
    * @param target the view-target
    * @return
    */
  def ngOn(target: String) = attr(s"on-$target")

  /**
    * One-way binding from view-target to data-source
    *
    * Use `ngOn(target) := "statement"`
    * In-place of `(target)="statement"`
    * @param event Must be a recognized browser event (ex. onmousemove)
    * @return
    */
  def ngOn(event: Attr) = {
    val attrName = event.name.replaceFirst("on", "on-")
    attr(attrName)
  }

  /** Two way data-binding
    * Note: FormsModule is required
    *
    * Use `ngBindOn("target")("expression")`
    * In-place of `[(target)]="expression"`
    */
  def ngBindOn(target: String) = attr(s"bindon-$target")

  // Built-in Directives
  def ngSwitch(expr: String) = ngBind("ngSwitch") := expr
  def ngSwitchCase(expr: String) = ngBind("ngSwitchCase") := expr
  def ngSwitchDefault(expr: String) = ngBind("ngSwitchCase") := expr

  // has to be a template attribute or angular will reject the template
  def ngIf(expr: String) = attr("template") := s"ngIf $expr"
  def ngFor(expr: String) = attr("template") := s"ngFor $expr"

}
