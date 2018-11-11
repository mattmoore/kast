package io.mattmoore.kast

import org.junit.jupiter.api.Test

internal class ParsingTest {
    @Test
    fun parseFunctionTest() {
        val source = """
        fun sampleFunc(sampleArg: String, sampleArg2: Int) : String {
            return "TEST"
        }
        """.trimIndent()
        val ast = parse(source)
        val expected = listOf(
                Function(name = "sampleFunc", accessLevel = AccessLevel.Public, returnType = "String", parameters = listOf(
                        Parameter(name = "sampleArg", dataType = "String"),
                        Parameter(name = "sampleArg2", dataType = "Int")))
        )

        assert(ast.nodes == expected)
    }

    @Test
    fun parsePropertyTest() {
        val source = """
            package foo
            class Person(firstName: String, lastName: String)
            val person = Person("Matt", "Moore")
        """.trimIndent()
        val ast = parse(source)
        val expected = listOf(
                Class(name = "Person", properties = emptyList(), methods = emptyList()),
                Property(name = "person", readOnly = false)
        )

        assert(ast.nodes == expected)
    }

    @Test
    fun parseClassTest() {
        val source = """
        package foo
        class Person(firstName: String, lastName: String) {
            var firstName : String = firstName
            var lastName : String = lastName

            fun fullName(arg: String) : String {
                return ""
            }
        }
        """.trimIndent()
        val ast = parse(source)
        val expected = listOf(
            Class(name = "Person",
                properties = listOf(
                    Property(name = "firstName", readOnly = true),
                    Property(name = "lastName", readOnly = true)),
                methods = listOf(
                    Function(name = "fullName", returnType = "String", parameters = listOf(
                            Parameter(name = "arg", dataType = "String")
                    )))))

        assert(ast.nodes == expected)
    }
}
