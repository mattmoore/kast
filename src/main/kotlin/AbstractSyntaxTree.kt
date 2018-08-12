import kastree.ast.Node
import kastree.ast.Visitor
import kastree.ast.psi.Parser

class AbstractSyntaxTree {
    enum class KotlinType {
        Function,
        Class
    }

    fun parseAST(code: String) : Node.File {
        return Parser.parseFile(code)
    }

    fun getNodesByType(nodes: List<Node.Decl>, type: KotlinType) : List<Node> {
        return when(type) {
            KotlinType.Function -> nodes.filter { it is Node.Decl.Func }
            KotlinType.Class    -> nodes.filter { it is Node.Decl.Structured }
            else                -> emptyList()
        }
    }

    fun classProperties(node: Node.Decl.Structured) : List<Node.Decl.Property> {
        return node.members
                .filter { it is Node.Decl.Property }
                .map { it as Node.Decl.Property }
    }

    fun classFunctions(node: Node.Decl.Structured) : List<Node.Decl.Func> {
        return node.members
                .filter { it is Node.Decl.Func }
                .map { it as Node.Decl.Func }
    }
}