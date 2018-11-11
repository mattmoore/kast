package io.mattmoore.kast

import io.mattmoore.kast.conversions.*

import org.jetbrains.kotlin.cli.jvm.compiler.EnvironmentConfigFiles
import org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment
import org.jetbrains.kotlin.com.intellij.openapi.util.Disposer
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement
import org.jetbrains.kotlin.com.intellij.psi.PsiManager
import org.jetbrains.kotlin.com.intellij.testFramework.LightVirtualFile
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.idea.KotlinFileType
import org.jetbrains.kotlin.psi.psiUtil.collectDescendantsOfType
import org.jetbrains.kotlin.com.intellij.openapi.project.Project
import org.jetbrains.kotlin.psi.KtFile

fun parse(source: String): AbstractSyntaxTree {
    val ast = parsePsiFile(source).also { file ->
        file.collectDescendantsOfType<PsiErrorElement>()
    }
    val nodes = ast.declarations.map { convertNode(it) }
    return AbstractSyntaxTree(nodes)
}

private fun project(): Project {
    return KotlinCoreEnvironment.createForProduction(
            Disposer.newDisposable(),
            CompilerConfiguration(),
            EnvironmentConfigFiles.JVM_CONFIG_FILES
    ).project
}

private fun parsePsiFile(code: String): KtFile {
    return PsiManager.getInstance(project())
            .findFile(LightVirtualFile("temp.kt", KotlinFileType.INSTANCE, code)) as KtFile
}
