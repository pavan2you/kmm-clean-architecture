# kmm-clean-architecture
Kotlin Multiplatform Technology Agnostic Clean Architecture - A predecessor approach to generic
[openUL](https://github.com/pavan2you/openul/wiki/Why-openUL%3F). 

Why is it a predecessor?, well, the ans is - it is coupled to kotlin multiplatform's 
expect-actual mechanism. Solving that would make it pure technology agnostic implementation.
Today the best possible multi language build chain is supported by KMP, so taking that trade-off
approach.

How it is structured?
- #### Separate platform apis and language apis from its application
  - Language extensions goes in at language/kmm-langx.
  - Kotlin extensions goes in at language/kmm-kotlinx.
  - Android extensions goes in at language/kmm-androidx.
  - Android extensions goes in at language/kmm-swiftx.
  - Tomorrow any other platform/language extension can be placed here
  - [more](https://github.com/pavan2you/kmm-clean-architecture/tree/master/language/TAGD_LANGUAGE.md).

- #### Separate architecture from its application
  - It is very natural to have base/core layer/module, which is stuffed in application(s). Instead
    of that, tagd clearly separates the reusable architecture concepts and implementations from
    its usage.
  - [more](https://github.com/pavan2you/kmm-clean-architecture/tree/master/architecture/TAGD_ARCHITECTURE.md).

- #### Applications
  - Use tagd - architecture suit and language extensions to develop sample applications.

- #### Build Logic
  - Leverages the Convention Plugins and Version Catalog to follow DRY and easy access of
  dependencies. 

- #### How to test?
  - Run [publish to maven local](https://github.com/pavan2you/kmm-clean-architecture/blob/master/build_and_publish_to_maven_local.sh)