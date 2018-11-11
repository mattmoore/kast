package io.mattmoore.kast.conversions

import io.mattmoore.kast.*
import io.mattmoore.kast.Function
import org.jetbrains.kotlin.psi.*

fun convertNode(node: KtDeclaration) : Node {
    return when (node) {
        is KtNamedFunction -> convertFunction(node)
        is KtParameter -> convertParameter(node)
        is KtProperty -> convertProperty(node)
        is KtClass -> convertClass(node)
        else -> error("Unable to convert node")
    }
}

fun convertFunction(node: KtNamedFunction) : Function {
    return Function(
            name = node.name!!,
            parameters = node.valueParameters.map { convertParameter(it) },
            accessLevel = AccessLevel.Public,
            returnType = convertReturnType(node)
    )
}

fun convertReturnType(node: KtNamedFunction) : String {
    return (node.typeReference?.typeElement as KtUserType).referencedName ?: "Unit"
}

fun convertParameter(node: KtParameter) : Parameter {
    return Parameter(
            name = node.name!!,
            dataType = node.typeReference!!.text
    )
}

fun convertProperty(node: KtProperty) : Property {
    return Property(
            name = node.name!!,
            readOnly = node.typeReference?.isWritable ?: false
    )
}

fun convertClass(node: KtClass) : Class {
    return Class(
            name = node.name!!,
            properties = node.getProperties().map { convertProperty(it) },
            methods = node.declarations.filterIsInstance<KtNamedFunction>().map { convertFunction(it) }
    )
}