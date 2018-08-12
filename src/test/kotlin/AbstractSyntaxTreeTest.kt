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
    @DisplayName("should return functions")
    fun getNodesByTypeFunctionTest() {
        val ast = AbstractSyntaxTree().parseAST(sourceCode())
        val nodes = AbstractSyntaxTree().getNodesByType(ast.decls, AbstractSyntaxTree.KotlinType.Function)
        val functions = nodes.map { (it as Node.Decl.Func).name }
        assert(functions == listOf("bar", "baz"))
    }

    @Test
    @DisplayName("should return classes")
    fun getNodesByTypeClassTest() {
        val ast = AbstractSyntaxTree().parseAST(sourceCode())
        val nodes = AbstractSyntaxTree().getNodesByType(ast.decls, AbstractSyntaxTree.KotlinType.Class)
        val classes = nodes.map { (it as Node.Decl.Structured).name }
        assert(classes == listOf("Person"))
    }

    @Test
    @DisplayName("should return class member properties")
    fun getClassPropertiesTest() {
        val ast = AbstractSyntaxTree().parseAST(sourceCode())
        val nodes = AbstractSyntaxTree().getNodesByType(ast.decls, AbstractSyntaxTree.KotlinType.Class)
        val personClass = nodes.find { (it as Node.Decl.Structured).name == "Person" }
        val propertyNames = AbstractSyntaxTree().classProperties(personClass as Node.Decl.Structured)
                .map { (it.expr as Node.Expr.Name).name }
        assert(propertyNames == listOf("firstName", "lastName"))
    }

    @Test
    @DisplayName("should return class member functions")
    fun getClassFunctionsTest() {
        val ast = AbstractSyntaxTree().parseAST(sourceCode())
        val nodes = AbstractSyntaxTree().getNodesByType(ast.decls, AbstractSyntaxTree.KotlinType.Class)
        val personClass = nodes.find { (it as Node.Decl.Structured).name == "Person" }
        val functionNames = AbstractSyntaxTree().classFunctions(personClass as Node.Decl.Structured)
                .map { it.name }
        assert(functionNames == listOf("fullName"))
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

            var p = Person()
        """.trimIndent()
    }
}
