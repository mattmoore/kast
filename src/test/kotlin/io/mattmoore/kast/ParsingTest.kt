package io.mattmoore.kast

import kotlin.test.*

internal class ParsingTest {
    @Test
    fun `parses a string val`() {
        val source = """
        val test = "TEST"
        """.trimIndent()
        val ast = parse(source)
        val expected = listOf(Property(name = "test", type = "STRING_TEMPLATE", value = "TEST", readOnly = true))
        assertEquals(expected, ast.children)
    }

    @Test
    fun `parses an int val`() {
        val source = """
        val test = 1
        """.trimIndent()
        val ast = parse(source)
        val expected = listOf(Property(name = "test", type = "INTEGER_CONSTANT", value = "1", readOnly = true))
        assertEquals(expected, ast.children)
    }

    @Test
    fun `parses a function`() {
        val source = """
        fun sampleFunc(sampleArg: String, sampleArg2: Int) : String {
            val test = "TEST"
            return test
        }
        """.trimIndent()
        val ast = parse(source)
        val expected = listOf(
                Function(name = "sampleFunc", accessLevel = AccessLevel.Public, returnType = "String",
                        parameters = listOf(
                                Parameter(name = "sampleArg", dataType = "String"),
                                Parameter(name = "sampleArg2", dataType = "Int")
                        ),
                        children = listOf(
                                AssignmentStatement(left = "test", right = ""),
                                ReturnStatement(value = "")
                        )
                )
        )

        assertEquals(expected, ast.children)
    }

    @Test
    fun `parses a property`() {
        val source = """
            package foo
            class Person(firstName: String, lastName: String)
            val person = Person("Matt", "Moore")
        """.trimIndent()
        val ast = parse(source)
        val expected = node(
                listOf(
                    Class(name = "Person", children = emptyList()),
                    Property(name = "person", type = "CALL_EXPRESSION", value = "", readOnly = true)
            )
        )

        assertEquals(expected, ast)
    }

    @Test
    fun `parses a class`() {
        val source = """
        package foo
        class Person(firstName: String, lastName: String) {
            val firstName : String = firstName
            val lastName : String = lastName

            fun fullName(arg: String) : String {
                return ""
            }
        }
        """.trimIndent()
        val ast = parse(source)
        val expected = node(
                listOf(
                    Class(name = "Person", children = listOf(
                                    Property(name = "firstName", type = "TYPE_REFERENCE", value = "", readOnly = true),
                                    Property(name = "lastName", type = "TYPE_REFERENCE", value = "", readOnly = true),
                                    Function(name = "fullName", returnType = "String", parameters = listOf(
                                                        Parameter(name = "arg", dataType = "String")
                                            ),
                                            children = listOf(
                                                    ReturnStatement(value = "")
                                            )
                                    )
                            )
                    )
            )
        )
        assertEquals(expected, ast)
    }

    @Test
    fun `parses an AST`() {
        val source = """
        package foo
        class Person(firstName: String, lastName: String) {
            val firstName : String = firstName
            val lastName : String = lastName

            fun fullName(arg: String) : String {
                return ""
            }
        }
        """.trimIndent()
    }
}
