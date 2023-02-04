package aditogether.buildtools.lint.util

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider
import java.util.Optional

internal fun DependencyHandler.detektPlugins(dependencyNotation: Optional<Provider<MinimalExternalModuleDependency>>) {
    add("detektPlugins", dependencyNotation.get())
}
