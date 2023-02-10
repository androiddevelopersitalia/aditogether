package aditogether.buildtools.android

import aditogether.buildtools.utils.apply
import aditogether.buildtools.utils.configure
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Applies a shared configuration to Android app targets.
 */
@Suppress("unused")
internal class AndroidAppPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("com.android.application")
        target.pluginManager.apply<AndroidPlugin>()

        target.extensions.configure(::configureAndroidApplicationExtension)
    }

    private fun configureAndroidApplicationExtension(ext: ApplicationExtension) {
        ext.defaultConfig.targetSdk = AndroidConfigs.TARGET_SDK
    }
}
