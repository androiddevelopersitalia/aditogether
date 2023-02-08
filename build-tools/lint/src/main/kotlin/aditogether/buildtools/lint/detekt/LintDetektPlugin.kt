package aditogether.buildtools.lint.detekt

import aditogether.buildtools.utils.apply
import aditogether.buildtools.utils.libsCatalog
import aditogether.buildtools.utils.withType
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Plugin used to apply Detekt and its configuration to the given project.
 */
internal class LintDetektPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply<DetektPlugin>()

        target.tasks.withType<Detekt> {
            autoCorrect = true
            config.setFrom("${target.rootDir.path}/detekt/config.yml")
            jvmTarget = "1.8"
        }

        val catalog = target.libsCatalog
        target.dependencies.apply {
            addDetektPlugin(catalog, plugin = "detekt-rules-compose")
            addDetektPlugin(catalog, plugin = "detekt-rules-formatting")
        }
    }

    private fun DependencyHandler.addDetektPlugin(catalog: VersionCatalog, plugin: String) {
        add("detektPlugins", catalog.findLibrary(plugin).get())
    }
}
