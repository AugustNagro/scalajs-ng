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

## Limitations with current inline macros:
- whitebox functionality
    - Cannot get the fully qualified name of an annotee. Therefore all companion objects are thrown under "annots.itsName_" which will cause namespace collisions for same-named components in different pacakges.
    - No TypeTags, etc
- Classes can only expand into themselves and an eponymous companion. Angulate2's `@Data` annotation cannot be currently implemented due to this restriction.
- No named params allowed when annotating (ex. `@Component(selector = "gg")` doesn't compile)

## Benefits
- Huge simplification + readability improvements. Should be easier to add features + maintain
- Issues with inline macros are well known and will be fixed/implemented
- I expect that the current experimental macros will be deprecated soon in favor of the new design

## Comparison to [angulate2](https://github.com/jokade/angulate2)
- Some syntax was borrowed. 
- Macro logic is simplified and in a single library dependency + plugin (as opposed to spread out across 3 repos)
- Reflection removes need for separate `annotations.js` file

## Documentation
### Annotations
Use the `@Component()`, `@Directive()`, and `@NGModule()` macro annotations similarly to typescript decorators. The annotations expect a parameter list of tuples, where the first element is the metadata's name, and the second is its value, of type `String` or `js.Array[js.Any]` 

Consider a Component annotation:

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
  "imports" -> @@(classOf[BrowserModule]) 
  ???
)
class AppModule
```

Annotate any service class with `@Injectable` to enable dependency injection. You'll still need to add it as a `provider` on an NgModule.

### Writing Templates

There are three ways html templates can be made. 

#### Inline (inside component annotation)
Angular's authors recommend keeping templates small and defined by the `template` key of its component. Templates can be expressed as plain strings:

```
    "template" ->
      """
        |<h1>{{title}}</h1>
        |<nav>
        |  <a routerLink="/dashboard" routerLinkActive="active">Dashboard</a>
        |  <a routerLink="/heroes" routerLinkActive="active">Heros</a>
        |</nav>
        |<router-outlet></router-outlet>
        |""".stripMargin
```

Or, using [ScalaTags](http://www.lihaoyi.com/scalatags/#GettingStarted), a fully-typed XML/HTML/CSS construction library:

```
import scalatags.Text.all._
import ng.ngScalaTags._

...

    "template" ->
      div(
        h1("{{title}}"),
        tag("nav")(
          routerLink("/dashboard")("Dashboard "),
          routerLink("/heroes")("Heroes")
        ),
        routerOutlet
      ).toString
  )
```
 
Include `"com.lihaoyi" %%% "scalatags" % "0.6.0"` in the project's library dependencies, and import `scalatags.Text.all._` in the component's file. `ng.ngScalaTags._` contains helpers specific to angular. 

By default, IntelliJ underlines implicit operations, which makes reading component-defined html hard to read. You can change the highlighting by going to Settings->Editor->Colors & Fonts->Scala, and finding the `implicit conversion` row. The author finds that changing the annotation type to "boxed", with color #E6E6E6 works well. 

#### External
Write html in an external file, and reference it's path with `"templateUrl" -> "insert template path"`. The same goes for the `styleUrls` array: `"styleUrls" -> @@("styleUrlOne", "styleUrl2")`.

### Routing

Routes can be defined with similar syntax to that used in Angular's [Routing Guide](https://angular.io/docs/ts/latest/guide/router.html). 

It's generally recommended to create distinct routing modules, which are imported into the main module.

Example: 

```
import ng.macros.NgModule
import ng.macros._
import ng.router.{Route, RouterModule, Routes}

@NgModule(
  "imports" -> @@(
    RouterModule.forRoot(
      Routes(
        Route(path = "", redirectTo = "/dashboard", pathMatch = "full"),
        Route(path = "dashboard", component = classOf[DashboardComponent]),
        Route(path = "detail/:id", component = classOf[HeroDetailComponent]),
        Route(path = "heroes", component = classOf[HeroesComponent])
      )
  )),

  "exports" -> @@(RouterModule)
)
class AppRoutingModule 
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
- Questions / Requests for ScalaJS team
    - `static` variable for classes, similar to typescript
    - Use case classes as JS literals (with pattern matching)
    - ES6 modules. Will each file be output as own JS file?
    - Release new scalajs-reflect
- Figure out how to better work with observables
    - ex. ActivatedRoute.params returns an Observable, but [rxscala-js](https://github.com/LukaJCB/rxscala-js) facades throw a runtime error, possibly due to Rx version differences
- `@Inject`, `@Optional`, `@Pipe` and others. Should be pretty easy.
- Testing
- Documentation
    - Should Angular 2 docs be included?
- Facades for all of angular's public api
- AOT Compilation? Lazy loading? Bundling? Static typing of elements in template defns?
    - An issue was made for AOT compilation [here](https://github.com/angular/angular/issues/11700)
    - Support for module.id?
- How to version
