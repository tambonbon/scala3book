## Background
**Implicits** in Scala 2 were a major distinguishing design feature. They are the fundamental way to abstract over context. They represent a unified paradigm with a great variety of use cases, among them:

-    Implementing type classes
-    Establishing context
-    Dependency injection
-    Expressing capabilities
-    Computing new types, and proving relationships between them

Since then, other languages have followed suit, e.g., Rust’s traits or Swift’s protocol extensions. Design proposals are also on the table for Kotlin as compile time dependency resolution, for C# as Shapes and Extensions or for F# as Traits. Implicits are also a common feature of theorem provers such as Coq or Agda.

Even though these designs use different terminology, they’re all variants of the core idea of **term inference**: *Given a type, the compiler synthesizes a “canonical” term that has that type.*

## Redesign
The design of Scala 3 focuses on **intent** rather than **mechanism**. Instead of offering one very powerful feature of implicits, Scala 3 offers several use-case oriented features:


-    **Abtracting over contextual information**. `using` clauses allow programmers to abstract over information that is available in the calling context and should be passed implicitly. As an improvement over Scala 2 implicits, `using` clauses can be specified by type, freeing function signatures from term variable names that are never explicitly referred to.

-    **Providing Type-class instances**. `given` instances allow programmers to define the canonical value of a certain type. This makes programming with type-classes more straightforward without leaking implementation details.

-   **Retroactively extending classes.** In Scala 2, extension methods had to be encoded using implicit conversions or implicit classes. In contrast, in Scala 3 extension methods are now directly built into the language, leading to better error messages and improved type inference.

-    **Viewing one type as another**. Implicit conversion have been redesigned from the ground up as instances of a type-class Conversion.

-    **Higher-order contextual abstractions**. The all-new feature of context functions makes contextual abstractions a first-class citizen. They are an important tool for library authors and allow to express concise domain specific languages.

-    **Actionable feedback from the compiler**. In case an implicit parameter can not be resolved by the compiler, it now provides you import suggestions that may fix the problem.
