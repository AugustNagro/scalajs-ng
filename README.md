# scalajs-ng

Angular 2 facades for scalajs using [inline macros](https://github.com/scalameta/paradise)

## Issues with current implementation of inline macros:
- limited/no whitebox functionality
    - Cannot get the fully qualified name of an annotee. Therefore all companion objects are thrown under "annots.<ObjName>_" which will cause namespace collisions for same-named components in different pacakges.
    - No TypeTags, etc
- Classes can only expand into themselves and an eponymous companion. The `@Data` annotation cannot be currently implemented due to this restriction.
- No named params allowed when annotating (ex. `@Component(selector = "gg")` doesn't compile)
- No Def macros

## Benefits
- Huge simplification + readability improvements. Should be easier to add features + maintain
- Issues with inline macros are well known and will be fixed/implemented
- I expect that the current experimental macros will be deprecated soon in favor of the new design

## Comparison to angulate2
- A lot of syntax was borrowed. 
- Pretty much a full rewrite
    - Macro logic is simplified and in a single package (as opposed to spread out across 3 repos)
    - Reflection plugin removes need for seperate `annotations.js` file
- [Example](https://augustnagro.com/tourofheros), [Source](https://github.com/augustnagro/toh)

## Todo
- Figure out how to better work with observables
    - ex. ActivatedRoute.params returns an Observable, but [rxscala-js](https://github.com/LukaJCB/rxscala-js) facades throw a runtime error, possibly due to Rx version differences
- Testing
- `@Inject`, `@Optional`, `@Pipe` and others
- Decide whether the facades should require `ng` in the global namespace, or use es6 modules and require a bundling tool like SystemJS. 
- Facades for all of angular's public api
- Figure out how to do ahead of time compilation for javascript sources. 
