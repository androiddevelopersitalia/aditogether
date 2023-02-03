package buildtools.lint

import buildtools.lint.util.apply
import buildtools.lint.util.withType
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class LintPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.configureDetekt()
    }
}

private fun Project.configureDetekt() {
    allprojects {
        apply<DetektPlugin>()
    }

    subprojects {

        tasks.withType<Detekt> {
            basePath = rootDir.path
            config.setFrom("${rootDir.path}/detekt/config.yml")
        }
    }

    dependencies.add(
        "detektPlugins",
        "com.twitter.compose.rules:detekt:0.0.26" // TODO: #1 Use from version Catalog
    )
}
