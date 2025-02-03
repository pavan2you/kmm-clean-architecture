# TagD - Core
**TagD Core** serves as the foundation in TagD architecture suit. It talks about the 
1. Traits, ex - Immutability, Cancellability, Releasability etc.   
2. Very fundamental architecture elements  ex - Service, Factory, Validatable etc. 
3. Building blocks ex - Service, State etc.
4. And loosely addresses any language concepts which are dependents on other TagD core traits. 
For ex - `AsyncStrategy`. 

**Service** -- `Service` is the core building block, A service is anything which offers either
behaviour or state as the offered service to its consumers. Check in depth definition [here](https://github.com/pavan2you/kmm-clean-architecture/blob/master/architecture/kmm-tagd-core/src/commonMain/kotlin/io/tagd/core/Service.kt) 

**Async Strategy** -- Concurrency frameworks and its deep roots to domain logic is a very 
questionable design decision made, Like the DI frameworks, the next big domain logic polluter is a
concurrent framework choice made. The domain logic, to that matter, the any application logic must
be concurrent framework free. What it means is -- **Separate triggers from logic**.
Concurrent frameworks / APIs must limit to just as logic execution triggers, nothing beyond that.

Imagine a codebase which has humongous amount of code which is spread across 100s of modules and
assume concurrent flows are coupled to a concurrent framework A, after some months/years, there is
new concurrent framework(let's say framework B) in the market, which appears like a wow and 
fascinating then switching from framework A to B comes up with lot of cost, otherwise one must
find a way to patch it through some inter operable adapters.

The very question to ask is, can not we design the logic without these magical frameworks? and 
limit frameworks polluting the domain/infra logic?

The ans to this is the `AsyncStrategy`. It encourages to obscure the what framework choice you made.
Your domain/infra logic is free. You can switch from framework A/B, through a matter of setter.
Wouldn't that be cool? The cost to refactor from 1000s of lines a single line of setter and by not
even touching the single logic.

Take a look at other fundamental elements [here](https://github.com/pavan2you/kmm-clean-architecture/blob/master/architecture/kmm-tagd-core/src/commonMain/kotlin/io/tagd/core)