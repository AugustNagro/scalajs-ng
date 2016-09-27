# scalajs-ng

Experimental Angular 2 facades for scalajs using [inline macros](https://github.com/scalameta/paradise).

A re-implementation of Angular's Tour of Heroes demo is live [here](https://augustnagro.com/tourofheroes), with source code [here](https://github.com/augustnagro/toh). Edit a hero by clicking on its name.

- [Installing](#installing)
- [Current Issues](#issues-with-current-edition-of-inline-macros)
- [Benefits](#Benefits)
- [Comparison to angulate2](#comparison-to-angulate2)
- [Documentation](#documentation)
- [Todo](#todo)

## Installing

Include the following to project/plugins.sbt:
```
resolvers += Resolver.sonatypeRepo("snapshots")
addSbtPlugin("com.augustnagro" % "sbt-scalajs-ng" % "0.0.1-SNAPSHOT")
```
Rebuild, and then add

```
enablePlugins(NGPlugin)
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "com.augustnagro" %%% "scalajs-ng" "0.0.1-SNAPSHOT" 
```
To build.sbt. Look at the tour of heroes demo for an example build. 

## Issues with current inline macros:
- limited whitebox functionality
    - Cannot get the fully qualified name of an annotee. Therefore all companion objects are thrown under "annots.itsName_" which will cause namespace collisions for same-named components in different pacakges.
    - No TypeTags, etc
- Classes can only expand into themselves and an eponymous companion. The `@Data` annotation cannot be currently implemented due to this restriction.
- No named params allowed when annotating (ex. `@Component(selector = "gg")` doesn't compile)

## Benefits
- Huge simplification + readability improvements. Should be easier to add features + maintain
- Issues with inline macros are well known and will be fixed/implemented
- I expect that the current experimental macros will be deprecated soon in favor of the new design

## Comparison to [angulate2](https://github.com/jokade/angulate2)
- Some syntax was borrowed. 
- Macro logic is simplified and in a single library dependency + plugin (as opposed to spread out across 3 repos)
- Reflection removes need for seperate `annotations.js` file

## Documentation
### Annotations
Use the `@Component()`, `@Directive()`, and `@NGModule()` macro annotations similarly to typescript decorators. The annotations expect a parameter list of tuples, where the first element is the metadata's name, and the second is its value, of type `String` or `js.Array[js.Any]` 

Consider an annotation for a Component: 

```
import ng.macros.Component

@Component("selector" -> "my-app", "template" -> "Hi")
class AppComponent
```

To simplify the creation of `js.Array[js.Any]`s, use the `@@(values: js.Any*): js.Array[js.Any]` method by importing `ng.macros._`. 

Importing the `ng.macros` package object also includes an implicit conversion from type `Class[_]` to `js.Any`, allowing reference to other classes. 

Below is the declaration of an NgModule:
 
```
import ng.platformBrowser.BrowserModule
import ng.macros.NgModule
import ng.macros._

@NgModule(
  "imports" -> @@(classOf[BrowserModule], BaseRoutes.routing),
  
  ???
)
class AppModule
```

Annotate any service class with `@Injectable` to enable dependency injection. You'll still need to add it as a `provider` on an NgModule.

### Routing

To define some routes, create an object extending `RouteDeclaration`. 

To use the routes, override the `routing` variable and import it in an NgModule. 

To define custom routing providers, override `routingProviders`, and include it in an NgModule's `providers`.  

Example: 

```
import ng.core.ModuleWithProviders
import ng.macros._
import ng.router.{Route, RouteDeclaration, RouterModule, Routes}

object BaseRoutes extends RouteDeclaration {

  private val routes: Routes = js.Array(
    Route("", redirectTo = "/dashboard", pathMatch = "full"),
    Route("dashboard", component = classOf[DashboardComponent]),
    Route("detail/:id", component = classOf[HeroDetailComponent]),
    Route("heroes", component = classOf[HeroesComponent])
  )

  override val routing: ModuleWithProviders = RouterModule.forRoot(routes)

}
```
### Bootstrapping
Make sure that `persistLauncher := true` in build.sbt, and annotate the launcher object with `@Bootstrap`. Within its main method, call `PlatformBrowserDynamic.platformBrowserDynamic().bootstrapModule()`

Example: 

```
import ng.macros.Bootstrap
import ng.platformBrowserDynamic.PlatformBrowserDynamic
import scala.scalajs.js.JSApp
import ng.macros._

@Bootstrap
object Launcher extends JSApp {
  def main(): Unit = {

    PlatformBrowserDynamic
      .platformBrowserDynamic()
      .bootstrapModule(classOf[AppModule])

  }
}

```

## Todo
- Figure out how to better work with observables
    - ex. ActivatedRoute.params returns an Observable, but [rxscala-js](https://github.com/LukaJCB/rxscala-js) facades throw a runtime error, possibly due to Rx version differences
- Testing
- Documentation
- `@Inject`, `@Optional`, `@Pipe` and others. Should be pretty easy.
- Decide whether the facades should require `ng` in the global namespace, or use es6 modules and require a bundling tool like SystemJS. 
- Facades for all of angular's public api
- Figure out how to do ahead of time compilation for javascript sources. 