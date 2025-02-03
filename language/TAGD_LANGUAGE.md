# tagd-language-x
- The solo objective is - to abstract the language offerings in concept driven way by leveraging
the Kotlin Multi Platform's expect-actual mechanism.
- This approach serves as a predecessor to [openUL](https://github.com/pavan2you/openul/wiki/Why-openUL%3F).
- Also, provides additions to kotlin, android as feature extensions just like 
javax/kotlinx/androidx. Also, it is quite common to develop util or helper classes in any project, 
by following this extension approacho, one could avoid such utility classes.

Think about it like, growing a language feature for ex - adding a `sublist` method to a `list` 
interface or some new collection like `ConcurrentBag`. These things are not project specific, the
engineer would dream to consume the richer APIs, so that he/she can focus on the project specific
stuff. 

So bottom line is - The TagD language's some projects are meant to solve such challenges.