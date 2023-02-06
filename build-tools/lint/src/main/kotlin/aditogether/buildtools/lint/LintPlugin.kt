package aditogether.buildtools.lint

import aditogether.buildtools.lint.util.detektPlugins
import aditogether.buildtools.utils.apply
import aditogether.buildtools.utils.libsCatalog
import aditogether.buildtools.utils.withType
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class LintPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        configureDetekt(target)
    }
}

private fun configureDetekt(target: Project) {
    target.pluginManager.apply<DetektPlugin>()

    target.tasks.withType<Detekt> {
        autoCorrect = true
        config.setFrom("${target.rootDir.path}/detekt/config.yml")
        jvmTarget = "1.8"
    }

    target.dependencies.apply {
        val catalog = target.libsCatalog
        detektPlugins(catalog.findLibrary("detekt-rules-compose"))
        detektPlugins(catalog.findLibrary("detekt-rules-formatting"))
    }
}
