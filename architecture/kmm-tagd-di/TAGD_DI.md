# TagD - Dependency Injection

#### Q. Do we really need a DI framework?
- Before using any DI framework, think twice do you really need a DI container?. What is the motive
behind it?. Are you using a DI framework, just because it is trending/popular/recommended by some 
1st/2nd/3rd party API providers?

One must have the clear answer why do one need a DI framework?

In nutshell, Dependency Injection(DI) is nothing but an **object composition** to satisfy 
Inversion of Control(IoC). At first, try to prefer object composition over DI container driven 
approach and DI containers must be a plan B, rather than the first preferred choice. 

The reason is DI containers creates objects and over the course of application execution time, 
they try to inject the stuff at the cost of introspecting the target classes to bind and inject. 
It is against the encapsulation principle. Somebody(DI container) sneak peaking into what a class 
is nothing but breaking encapsulation.

For example take a look at the below code,
```
  import javax.inject.Inject;
  public class Budget {
    private final DB db;
    @Inject
    public Budget(DB data) {
    this.db = data;
    }
    // same methods as above
  }
```

The DI framework's annotation processor introspects the classes which are marked with respective
annotations, they even expect you to deal with instance variables access modifier, to make them
discoverable by the framework.

The best practice here by respecting the encapsulation must be,

```
  public class Budget {
    private final DB db;

    public Budget(DB data) {
      this.db = data;
    }

    public long total() {
      return this.db.cell(
        "SELECT SUM(cost) FROM ledger"
      );
    }
  }
```

Here `Budget` class depends on `DB` instance and it doesn't know how to create it. The component
which needs `Budget` can do the plain object composition then use Budget instance.
```
  public class SomeClass {
  
    public void someMethod {
      Budget b = new Budget(new DB(..)); // This is all about the DI in a nutshell.
    }
  }  
```

Bottom line is, DI containers are anti pattern w.r.t encapsulation, because they introspect and
expects the target classes to reveal the state more than the required visibility. Even if the
visibility problem is solved, there is someone who is sneak peaking into a class, who does an 
annotation loop up.

For some reason, let's say the call-site/usage logic, doesn't have the enough information to
create the required dependency, then first try to solve from the Dependency's design, let it expose
a builder/accessor, this is how the android, iOS or in general the first party code bases comes 
up / offers.

Q. What if there is a DI container, which doesn't introspect and respects encapsulation?, which
let's to access the desired dependencies? That would be an idle choice and that is how one can
effectively address the Separation of Concerns(SoCs).

To ans that, I can think of 3 possible trade-off ways, as far as I'm aware of --

1. Limit the DI container logic only to the service layer (A service/infra layer is nothing the 
outermost ring in the Uncle Bob's Clean Architecture diagram) and let's not pollute the business 
logic with these proprietary annotations. -- **Separate Business Logic from DI Containers**.
Take away -- In this approach, you will be limiting the dependencies composition only to the 
service layer, your problem domain doesn't get impacted by the proprietary DI framework choices.

2. **Context Aware** -- Let the dependencies are accessible through a Context -- Android `Context`
and how it let's to access the System Services, starting activities etc are great examples here.
Here the `Context` acts as a Facade to access state and behaviour. 
-- If you need dependencies to be accessible throughout the domain work flows, then consider
   a context driven approach, it is quite elegant, which comes at TC O(1). because everything you
   need is accessible as just `x.y` format, where x is the context obj and y is state/behaviour.
   Ex - `context.getLoginService().doLogin(...)` or `context.loginUser`. 
If 1 & 2 are failed / quite difficult to mode, then there is an easy alternative, That is,

3. Using a testable, mockable locator (framework's independent). -- A testable locator is very
   much possible(no need to make it as a singleton). In this way the dependencies are located.

The TagDi -- can be used as the approach 1 and 3.

- The core purpose is - Having an IoC mechanism which is 
  - technology agnostic, 
  - magic free(annotation free), 
  - scope-based,
  - simple,
  - in built - 100% code control, because it's your code. We really don't need fancy framework
induced annotations, learning curve etc.
  - Ease the object creation/setup. 
  - while accessing, let your classes doesn't aware that there is someone called DI container. 
For this, TagDi leverages kotlin's delegates.

- From the DI responsibilities perspective it offers --
  - bind class A to its instance.
  - bind class A to its creator.
  - get class A instance
  - create class A instance if the caller can supply required args to create class A.
  - differentiate generic types
  - modularize / scope the dependencies.
  - let the DI resolve the best to get instance/creator to provide the required dependency.
  - notify dependents if the dependency is available.

- The TagDi is a multi modular, multi layer service locator library, which leverages the concepts 
of Service, Layer, Locator and Scope. It is designed to well suit with clean architecture.
- Any dependency(`Service`) is accessible at the logarithmic distance (scope>locator>layer>service)
which is at worst case having a TC of O(3).
- Any dependency(`Service`) can be bind/get using a `Key`.
- Leverage `DependentService` if `Service A` depends on `Service B, C..Z`.

# Learn more about how DI Containers are Anti Pattern
  - https://blog.cleancoder.com/uncle-bob/2015/08/06/LetTheMagicDie.html
  - https://www.yegor256.com/2014/10/03/di-containers-are-evil.html