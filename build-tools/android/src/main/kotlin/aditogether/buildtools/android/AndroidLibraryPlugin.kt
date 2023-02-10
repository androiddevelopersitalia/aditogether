package aditogether.buildtools.android

import aditogether.buildtools.utils.apply
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Applies a shared configuration to Android libraries targets.
 */
@Suppress("unused")
internal class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("com.android.library")
        target.pluginManager.apply<AndroidPlugin>()
    }
}
