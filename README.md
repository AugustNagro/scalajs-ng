# scalajs-ng

Experimental Angular 2 facades for scalajs using [inline macros](https://github.com/scalameta/paradise).

A re-implementation of Angular's Tour of Heroes demo is hosted [here](https://github.com/augustnagro/toh), and live [here](https://augustnagro.com/tourofheroes). Edit a hero by clicking on its name.

- [Installing](#installing)
- [Current Issues](#Issues-with-current-edition-of-inline-macros:)
- [Benefits](#Benefits)
- [Comparison to angulate2](#Comparison-to-angulate2)
- [Documentation](#documentation)
- [Todo](#todo)

## Installing

Include the following to your plugins.sbt:
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
To your build.sbt. Look at the tour of heroes demo for an example build. 

## Issues with current edition of inline macros:
- limited whitebox functionality
    - Cannot get the fully qualified name of an annotee. Therefore all companion objects are thrown under "annots.[ObjName]_" which will cause namespace collisions for same-named components in different pacakges.
    - No TypeTags, etc
- Classes can only expand into themselves and an eponymous companion. The `@Data` annotation cannot be currently implemented due to this restriction.
- No named params allowed when annotating (ex. `@Component(selector = "gg")` doesn't compile)
- No Def macros

## Benefits
- Huge simplification + readability improvements. Should be easier to add features + maintain
- Issues with inline macros are well known and will be fixed/implemented
- I expect that the current experimental macros will be deprecated soon in favor of the new design

## Comparison to [angulate2](https://github.com/jokade/angulate2)
- A lot of syntax was borrowed. 
- Macro logic is simplified and in a single lib dep and plugin (as opposed to spread out across 3 repos)
- Reflection removes need for seperate `annotations.js` file

## Documentation
### Annotations
Use the `@Component()`, `@Directive()`, and `@NGModule()` macro annotations similarly to typescript decorators. The annotations expect parameters to be a tuple of type (String, js.Any), where the first element is a defined angular metadata. For example, 

```
@Component("selector" -> "my-app", "template" -> "Hi")
class AppComponent
```

### `@@()()` and `@#()`
Often it's required to include class references and variables in annotations.

The included `@@(classes: Class[_]*)(jsVals: js.Any*): js.Array[js.Any]` takes an arbitrary number of classes and js values and returns an array with the classes resolved to their javascript instances. 
  
`@#(class: Class[_]): js.Any` accepts a single class.

To use these methods, import `ng.macros._`. For an example, consider declaring an `NgModule` that imports some common angular modules, and the app's routing config: 
 
```
@NgModule(
  "imports" -> @@(classOf[BrowserModule],
                  classOf[FormsModule],
                  classOf[HttpModule])(BaseRoutes.routing),
       ???           
)
``` 

### Routing

To define some routes, create an object extending `RouteDeclaration`. 

To use the routes, override the `routing` variable and import it in an NgModule. 

To define custom routing providers, override `routingProviders`, and include it in an NgModule's providers.  

Example: 

```
object BaseRoutes extends RouteDeclaration {

  override val routes: Routes = js.Array(
    Route("", redirectTo = "/dashboard", pathMatch = "full"),
    Route("dashboard", component = @#(classOf[DashboardComponent])),
    Route("detail/:id", component = @#(classOf[HeroDetailComponent])),
    Route("heroes", component = @#(classOf[HeroesComponent]))
  )

  override val routing: ModuleWithProviders = RouterModule.forRoot(routes)

}
```
### Bootstrapping
Make sure that `persistLauncher := true` in your build.sbt, and annotate your launcher object with `@Bootstrap`. Within its main method, call `PlatformBrowserDynamic.platformBrowserDynamic().bootstrapModule()`

Example: 

```
@Bootstrap
object Launcher extends JSApp {
  def main(): Unit = {

    PlatformBrowserDynamic
      .platformBrowserDynamic()
      .bootstrapModule(@#(classOf[AppModule]))

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