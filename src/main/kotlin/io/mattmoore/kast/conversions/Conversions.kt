package io.mattmoore.kast.conversions

import io.mattmoore.kast.*
import io.mattmoore.kast.Function
import org.jetbrains.kotlin.psi.*
import org.jetbrains.kotlin.psi.psiUtil.plainContent

fun convertNode(node: KtDeclaration): Node {
    return when (node) {
        is KtNamedFunction -> convertFunction(node)
        is KtParameter     -> convertParameter(node)
        is KtProperty      -> convertProperty(node)
        is KtClass         -> convertClass(node)
        is KtExpression    -> convertExpression(node)
        else               -> error("Unable to convert node.")
    }
}

fun convertFunction(node: KtNamedFunction): Function {
    return Function(
            name = node.name ?: "",
            parameters = node.valueParameters.map { convertParameter(it) },
            accessLevel = AccessLevel.Public,
            returnType = convertReturnType(node),
            children = convertExpressions(node)
    )
}

fun convertExpressions(node: KtNamedFunction): List<Node> {
    return node.bodyBlockExpression?.statements?.map { convertExpression(it) } ?: emptyList()
}

fun convertExpression(node: KtExpression): Statement {
    return when (node) {
        is KtReturnExpression -> convertReturnStatement(node)
        else -> convertAssignmentStatement(node)
    }
}

fun convertAssignmentStatement(node: KtExpression): AssignmentStatement {
    return AssignmentStatement(left = node.name ?: "", right = "")
}

fun convertReturnStatement(node: KtExpression): ReturnStatement {
    return ReturnStatement(value = "")
}

fun convertReturnType(node: KtNamedFunction): String {
    return (node.typeReference?.typeElement as KtUserType).referencedName ?: "Unit"
}

fun convertParameter(node: KtParameter): Parameter {
    return Parameter(
            name = node.name ?: "",
            dataType = node.typeReference?.text ?: ""
    )
}

fun convertProperty(node: KtProperty): Property {
    return Property(
            name = node.name ?: "",
            value = convertPropertyValue(node),
            type = convertPropertyType(node),
            readOnly = !node.isVar
    )
}

fun convertPropertyValue(node: KtProperty): String {
    return when (node.children[0].node.psi) {
        is KtStringTemplateExpression -> (node.children[0].node.psi as KtStringTemplateExpression).plainContent
        is KtConstantExpression -> convertConstantExpression(node)
        else -> ""
    }
}

fun convertConstantExpression(node: KtProperty): String {
    return when ((node.children[0].node.psi as KtConstantExpression).elementType.toString()) {
        "INTEGER_CONSTANT" -> node.children[0].node.psi.text
        else -> node.children[0].node.psi.text
    }
}

fun convertPropertyType(node: KtProperty): String {
    return node.children[0].node.elementType.toString()
}

fun convertClass(node: KtClass): Class {
    return Class(
            name = node.name ?: "",
            children = listOf(
                    node.getProperties().map { convertProperty(it) },
                    node.declarations.filterIsInstance<KtNamedFunction>().map { convertFunction(it) }
            ).flatten()
    )
}
