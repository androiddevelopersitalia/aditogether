package aditogether.buildtools.multiplatform

import aditogether.buildtools.jvm.JvmOptions
import aditogether.buildtools.utils.apply
import aditogether.buildtools.utils.configure
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinJvmTargetPreset
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

/**
 * Applies a shared Kotlin multi-platform configuration to the given project.
 * This plugin supports the following compilation targets:
 * - JVM
 */
@Suppress("unused")
internal class MultiPlatformPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply<KotlinMultiplatformPluginWrapper>()

        target.extensions.configure<KotlinMultiplatformExtension> { ext ->
            ext.targetFromPreset(KotlinJvmTargetPreset(target), ::configureJvmTarget)
        }
    }

    private fun configureJvmTarget(target: KotlinJvmTarget) {
        target.compilations.all { compilation ->
            compilation.compilerOptions.configure {
                allWarningsAsErrors.set(JvmOptions.WARNINGS_AS_ERRORS)
                jvmTarget.set(JvmTarget.fromTarget(JvmOptions.JAVA_VERSION.toString()))
            }
        }
    }
}
