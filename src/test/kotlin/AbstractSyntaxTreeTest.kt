import kastree.ast.Node
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.DisplayName

public class AbstractSyntaxTreeTest {
    @Test
    @DisplayName("should build AST from code")
    fun parseASTTest() {
        val ast = AbstractSyntaxTree().parseAST(sourceCode())
        assert(ast is Node.File)
    }

    @Test
    @DisplayName("should get functions")
    fun nodesByTypeFunctionTest() {
        val ast = AbstractSyntaxTree().parseAST(sourceCode())
        val nodes = AbstractSyntaxTree().nodesByType(ast.decls, AbstractSyntaxTree.KotlinType.Function)
        val functions = nodes.map { (it as Node.Decl.Func).name }
        assert(functions == listOf("bar", "baz"))
    }

    @Test
    @DisplayName("should get classes")
    fun nodesByTypeClassTest() {
        val ast = AbstractSyntaxTree().parseAST(sourceCode())
        val nodes = AbstractSyntaxTree().nodesByType(ast.decls, AbstractSyntaxTree.KotlinType.Class)
        val classes = nodes.map { (it as Node.Decl.Structured).name }
        assert(classes == listOf("Person"))
    }

    @Test
    @DisplayName("should get class member properties")
    fun classPropertiesTest() {
        val ast = AbstractSyntaxTree().parseAST(sourceCode())
        val nodes = AbstractSyntaxTree().nodesByType(ast.decls, AbstractSyntaxTree.KotlinType.Class)
        val personClass = nodes.find { (it as Node.Decl.Structured).name == "Person" }
        val propertyNames = AbstractSyntaxTree().classProperties(personClass as Node.Decl.Structured)
                .map { (it.expr as Node.Expr.Name).name }
        assert(propertyNames == listOf("firstName", "lastName"))
    }

    @Test
    @DisplayName("should get class member functions")
    fun classFunctionsTest() {
        val ast = AbstractSyntaxTree().parseAST(sourceCode())
        val nodes = AbstractSyntaxTree().nodesByType(ast.decls, AbstractSyntaxTree.KotlinType.Class)
        val personClass = nodes.find { (it as Node.Decl.Structured).name == "Person" }
        val functions = AbstractSyntaxTree().classFunctions(personClass as Node.Decl.Structured)

        assert(functions == listOf(
                Function(name = "fullName", type = "String", params = emptyList()),
                Function(name = "sampleFunc", type = "Int", params = listOf(
                        Parameter(name = "sampleArg", type = "String"),
                        Parameter(name = "sampleArg2", type = "Int")))
        ))
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

                fun sampleFunc(sampleArg: String, sampleArg2: Int) : Int {
                    return sampleArgs2
                }
            }

            var p = Person()
        """.trimIndent()
    }
}
