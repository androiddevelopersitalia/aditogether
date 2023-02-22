package aditogether.buildtools.multiplatform

import aditogether.buildtools.utils.apply
import aditogether.buildtools.utils.boolProperty
import aditogether.buildtools.utils.configure
import aditogether.buildtools.utils.intProperty
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

        val javaTarget = target.intProperty("aditogether.jvmOptions.javaTarget")
        val warningsAsErrors = target.boolProperty("aditogether.jvmOptions.warningsAsErrors")

        target.extensions.configure<KotlinMultiplatformExtension> { ext ->
            ext.targetFromPreset(KotlinJvmTargetPreset(target)) {
                configureJvmTarget(this, javaTarget, warningsAsErrors)
            }
        }
    }

    private fun configureJvmTarget(
        target: KotlinJvmTarget,
        javaTarget: Int,
        warningsAsErrors: Boolean
    ) {
        target.compilations.all { compilation ->
            compilation.compilerOptions.configure {
                allWarningsAsErrors.set(warningsAsErrors)
                jvmTarget.set(JvmTarget.fromTarget(javaTarget.toString()))
            }
        }
    }
}
