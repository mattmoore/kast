import kastree.ast.Node
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName

public class KotlinTestGeneratorTest {
    @Test
    @DisplayName("should generate test stubs from source code")
    fun parseASTTest() {
        val stubSourceCode = KotlinTestGenerator().generateStubs(sourceCode())
        assert(stubSourceCode == "bar\nbaz")
    }

    private fun sourceCode() : String {
        return """
            package foo

            fun bar() {
                // Print hello
                println("Hello, World!")
            }

            fun baz() = println("Hello, again!")

            class Person(firstName: String, lastName: String) {
                var firstName : String = firstName
                var lastName : String = lastName

                fun fullName() : String {
                    return ""
                }
            }

            val p = Person()
        """.trimIndent()
    }
}
