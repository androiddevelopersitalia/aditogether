package aditogether.buildtools.lint

import aditogether.buildtools.utils.applyPlugin
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
    target.applyPlugin<DetektPlugin>()

    target.tasks.withType<Detekt> {
        config.setFrom("${target.rootDir.path}/detekt/config.yml")
    }

    target.dependencies.add(
        "detektPlugins",
        target.libsCatalog.findLibrary("detekt-rules-compose").get()
    )
}
