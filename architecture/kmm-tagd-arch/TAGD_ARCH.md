# TagD - Architecture
- **TagD - arch** -- is a plain simple definition and implementation of mobile development design
patterns. It covers full stack mobile development patterns.
- The primary objective for _tagd-arch_ is -- "separate the **concept** from **usage**". In more
concrete sense, "separate **architecture** from its **application**". 

Some characteristics are 
- **It's modular** 
  - TagD encourages modularity using `Module` and `Library`. The modularity unit
  either it can be an application module or a library module. In a very abstract sense they are
  referred as `Scopable`(s).
  - Supports Out-of-the-box On demand inject chain using `WithinScopableInjectionChain`.
  - `Scopable`(s) are initialized using `WithinScopableInitializer`.

- **Domain Driven Design**
  - TagD recommends to visualise and model business/product/problem by using Domain Driven Design. 
  This is nothing but a Problem Domain Modeling using DDD in conjunction with Clean architecture
  layering.
  - The primary objective here, "separate infra from logic". In more concrete sense, 
  "separate **problem domain** from **solution domain**".
  - both the 
    - problem domain (`domain`) - which is nothing but concepts + biz logic and 
    - solution domain (`infra` | `service` ring in Uncle Bob's clean arch diagram) are layered. 
  These layers are same, one contains the concepts + biz logic and other contains implementations +
  infra logic.
  - **datatypes** -- talks about some abstract fundamental domain elements. 
  - **data** -- talks about the data layer. The data is accessible from cache, dao, gateway which
  are encapsulated through repository. The data is transmittable as Dtos.
  - **domain** -- at core domain contains domain usecases, domain services and domain entities.
  - **crosscutting** -- any helper stuff which are needed across all biz layers will go here.
  - **infra** -- defines the abstract infra/solution domain + agnostic resource abstraction.
  - **present** -- contains the various MV* patterns and presentation services.
  - **control** -- contains the MVC pattern controllable services.
  - **access** -- dependency injection stuff.

- **app** -- The entry point of a tagd mobile application. The application(`IApplication`) comes
with `ApplicationController` which defines a platform agnostic mobile application life cycle.
- The x-factor is, the client application can have N-no of custom loading steps, which acts as
sub states within loading state.