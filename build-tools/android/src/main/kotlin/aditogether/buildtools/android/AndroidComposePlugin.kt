package aditogether.buildtools.android

import aditogether.buildtools.utils.configure
import aditogether.buildtools.utils.libsCatalog
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog

internal class AndroidComposePlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val libsCatalog = target.libsCatalog
        target.extensions.configure<CommonExtension<*, *, *, *>> { ext -> configureComposeOptions(libsCatalog, ext) }
    }

    @Suppress("UnstableApiUsage")
    private fun configureComposeOptions(libsCatalog: VersionCatalog, ext: CommonExtension<*, *, *, *>) {
        ext.buildFeatures.compose = true
        ext.composeOptions.kotlinCompilerExtensionVersion =
            libsCatalog.findVersion("androidxComposeCompiler").get().toString()
    }
}
