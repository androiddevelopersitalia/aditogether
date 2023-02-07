package aditogether.buildtools.lint

import aditogether.buildtools.lint.util.detektPlugins
import aditogether.buildtools.utils.apply
import aditogether.buildtools.utils.configureTaskNamed
import aditogether.buildtools.utils.libsCatalog
import aditogether.buildtools.utils.withType
import com.netflix.nebula.lint.plugin.GradleLintExtension
import com.netflix.nebula.lint.plugin.GradleLintPlugin
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class LintPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.pluginManager.withPlugin("kotlin") {
            // Detekt should be applied only on Kotlin modules.
            configureDetekt(target)
        }
        configureGradleLint(target)
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

private fun configureGradleLint(target: Project) {
    target.pluginManager.apply<GradleLintPlugin>()

    target.extensions.configure(GradleLintExtension::class.java) { ext ->
        ext.alwaysRun = false
        ext.reportFormat = "text"
        ext.criticalRules = listOf(
            "all-dependency",
            "duplicate-dependency-class"
        )
    }

    // The task `check` should run all the linters, this included.
    target.tasks.configureTaskNamed("check") { task ->
        task.dependsOn("generateGradleLintReport")
    }

    // The Kotlin plugin adds the stdlib automatically by default in the `implementationDependenciesMetadata` config.
    // Since this config is not queried using this Gradle linter, we should add it to the `implementation` config.
    // Also, for performance reasons, the
    target.pluginManager.withPlugin("kotlin") {
//        target.configurations.named("implementationDependenciesMetadata").get().withDependencies {  }

        target.dependencies.add("implementation", target.libsCatalog.findLibrary("kotlin-stdlib").get())
    }
}
