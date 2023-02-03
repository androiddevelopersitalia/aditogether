package aditogether.buildtools.lint

import org.gradle.api.Plugin
import aditogether.buildtools.utils.apply
import aditogether.buildtools.utils.libsCatalog
import aditogether.buildtools.utils.withType
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.Project

@Suppress("unused")
class LintPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.configureDetekt()
    }
}

private fun Project.configureDetekt() {
    apply<DetektPlugin>()

    tasks.withType<Detekt> {
        config.setFrom("${rootDir.path}/detekt/config.yml")
    }

    dependencies.add(
        "detektPlugins",
        libsCatalog.findLibrary("detekt-rules-compose").get()
    )
}
