package aditogether.buildtools.jvm

import aditogether.buildtools.utils.apply
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

        target.tasks.withType<KotlinCompile> { task ->
            task.compilerOptions.allWarningsAsErrors.set(JvmOptions.WARNINGS_AS_ERRORS)
        }

        target.extensions.configure<KotlinTopLevelExtension> { ext ->
            ext.jvmToolchain(JvmOptions.JAVA_VERSION)
        }
    }
}
