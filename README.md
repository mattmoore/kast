# Kotlin Test Generator

Library that scans Kotlin source code and generates junit test stubs.

## Usage:

```kotlin
val source = """
    package foo

    fun bar() {
        // Print hello
        println("Hello, World!")
    }

    fun baz() = println("Hello, again!")
    class Person(firstName: String, lastName: String)
    val p = Person()
""".trimIndent()
val sourceStubs = KotlinTestGenerator.generateStubsFromSource(source)
```

`sourceStubs` now stores a junit test in the following form:

```kotlin
@Test
@DisplayName("bar")
fun testParseBar() {
  // TODO: Implement test for bar
}

@Test
@DisplayName("baz")
fun testParseBaz() {
  // TODO: Implement test for baz
}
```

This can then be written to a junit test file:

```kotlin
KotlinTestGenerator.writeSource(sourceStubs, path: "some file path.kt")
```
