# TagD - Dependency Injection

#### Q. Do we really really need a DI framework?
- Before using DI framework, think twice do you really really need a DI container?. Are you using
a DI framework, just because it is trending/popular/recommended by some 1st/2nd/3rd?

At core, DI is nothing but an **object composition**. Prefer object composition over DI container 
driven approach. DI containers must be a plan B, rather than the first class approach. The reason is
DI containers creates objects and over the course of application execution time, they try to inject
the stuff. It is against the encapsulation principle. Somebody(DI container) sneak peaks in to 
what your class is actually doing. 

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

Bottom line is, DI containers are anti pattern w.r.t encapsulation.

For some reason, let's say the call-site/usage logic, doesn't have the enough information to
create the required dependency, then first try to solve from the Dependency's design, let it expose
a builder, this is how the android, iOS or in general the first party code bases comes up / offers.
But if at all if we are looking for one, then, the **trade off** approaches which adhere to 
encapsulation are

1. Limit the DI container logic only to the service layer, Let's not pollute the business logic
with these proprietary annotations.
2. Using testable, locator (framework's independent). -- A testable locator is very much possible. 
In this way the dependencies are located.

The TagDi -- can be used for both the above mentioned approaches.

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