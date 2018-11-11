package io.mattmoore.kast

enum class AccessLevel {
    Public,
    Private,
    Protected,
}

data class AbstractSyntaxTree(val nodes: List<Node>)

sealed class Node

data class Class(val name: String, val properties: List<Node>, val methods: List<Node>) : Node()

data class Property(val name: String, val readOnly: Boolean = true) : Node()

data class Function(
        val name: String,
        val accessLevel: AccessLevel = AccessLevel.Public,
        val returnType: String,
        val parameters: List<Parameter>
) : Node()

data class Parameter(val name: String, val dataType: String) : Node()
