package aditogether.buildtools.jvm

import aditogether.buildtools.utils.apply
import aditogether.buildtools.utils.boolProperty
import aditogether.buildtools.utils.intProperty
import aditogether.buildtools.utils.configure
import aditogether.buildtools.utils.withType
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinTopLevelExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Applies a shared Kotlin JVM configuration to the given project.
 */
@Suppress("unused")
internal class JvmPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply<KotlinPluginWrapper>()

        val javaTarget = target.intProperty("aditogether.jvmOptions.javaTarget")
        val warningsAsErrors = target.boolProperty("aditogether.jvmOptions.warningsAsErrors")
        target.tasks.withType<KotlinCompile> { task ->
            task.compilerOptions.allWarningsAsErrors.set(warningsAsErrors)
        }

        target.extensions.configure<KotlinTopLevelExtension> { ext ->
            ext.jvmToolchain(javaTarget)
        }
    }
}
