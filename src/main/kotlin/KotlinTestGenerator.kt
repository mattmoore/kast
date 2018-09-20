import kastree.ast.Node

class KotlinTestGenerator {
    fun generateStubs(source: String) : String {
        val ast = AbstractSyntaxTree().parseAST(source)
        val nodes = AbstractSyntaxTree().nodesByType(ast.decls, AbstractSyntaxTree.KotlinType.Function)
        val names = nodes.map { (it as Node.Decl.Func).name }
        val sourceStubs = names.map { it }
        return sourceStubs.joinToString("\n")
    }
}