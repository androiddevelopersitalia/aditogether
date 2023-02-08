package aditogether.buildtools.lint

import aditogether.buildtools.lint.detekt.LintDetektPlugin
import aditogether.buildtools.utils.apply
import aditogether.buildtools.utils.withPlugins
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Plugin used to apply our linters to the given project.
 * Linters currently supported:
 * - Detekt (Kotlin)
 */
@Suppress("unused")
internal class LintPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.withPlugins(
            "kotlin",
            "kotlin-android",
            "kotlin-multiplatform"
        ) {
            // Detekt should be applied only on Kotlin modules.
            target.pluginManager.apply<LintDetektPlugin>()
        }
    }
}
