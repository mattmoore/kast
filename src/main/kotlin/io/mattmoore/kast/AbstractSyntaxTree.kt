package io.mattmoore.kast

enum class AccessLevel {
    Public,
    Private,
    Protected,
}

sealed class Node(open var children: List<Node>)

data class ASTNode(override var children: List<Node> = emptyList()) : Node(children)

fun node(children: List<Node>): Node {
    return ASTNode(children)
}

data class Class(
        override var children: List<Node> = emptyList(),
        val name: String
) : Node(children)

data class Property(
        override var children: List<Node> = emptyList(),
        val name: String,
        val value: String,
        val type: String,
        val readOnly: Boolean = true
) : Node(children)

data class Function(
        override var children: List<Node> = emptyList(),
        val name: String,
        val accessLevel: AccessLevel = AccessLevel.Public,
        val returnType: String,
        val parameters: List<Parameter>
) : Node(children)

sealed class Statement(
        children: List<Node> = emptyList()
) : Node(children)

data class AssignmentStatement(
        override var children: List<Node> = emptyList(),
        val left: String,
        val right: String
) : Statement(children)

data class ReturnStatement(
        override var children: List<Node> = emptyList(),
        val value: String
) : Statement(children)

data class Parameter(
        override var children: List<Node> = emptyList(),
        val name: String,
        val dataType: String
) : Node(children)
