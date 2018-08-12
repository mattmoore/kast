# Kotlin Abstract Syntax Tree Library

Library that parses Kotlin code into an abstract syntax tree.

## Usage:

```kotlin
import com.github.mattmoore.kast

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

val ast = parse(source)
