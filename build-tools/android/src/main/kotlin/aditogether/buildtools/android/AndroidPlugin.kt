package aditogether.buildtools.android

import aditogether.buildtools.jvm.JvmOptions
import aditogether.buildtools.utils.apply
import aditogether.buildtools.utils.configure
import aditogether.buildtools.utils.withType
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Applies a shared configuration to Android targets (apps, libraries, etc...).
 */
internal class AndroidPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply<KotlinAndroidPluginWrapper>()

        target.tasks.withType<KotlinCompile> { task ->
            // Can't use JVM toolchains yet on Android.
            task.kotlinOptions.jvmTarget = JvmOptions.JAVA_VERSION.toString()
            task.compilerOptions.allWarningsAsErrors.set(JvmOptions.WARNINGS_AS_ERRORS)
        }

        target.extensions.configure(::configureAndroidExtension)
    }

    @Suppress("UnstableApiUsage")
    private fun configureAndroidExtension(ext: CommonExtension<*, *, *, *>) {
        ext.buildToolsVersion = AndroidConfigs.BUILD_TOOLS
        ext.compileSdk = AndroidConfigs.COMPILE_SDK
        ext.defaultConfig.minSdk = AndroidConfigs.MIN_SDK

        val javaVersion = JavaVersion.toVersion(JvmOptions.JAVA_VERSION)
        ext.compileOptions.apply {
            // Can't use JVM toolchains yet on Android.
            sourceCompatibility = javaVersion
            targetCompatibility = javaVersion
        }

        ext.lint.warningsAsErrors = true
    }
}
