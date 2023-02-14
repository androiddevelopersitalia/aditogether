plugins {
    id("aditogether.multiplatform")
    id("com.android.library")
}

android {
    namespace = "aditogether.shared"
    compileSdk = aditogether.buildtools.android.AndroidConfigs.COMPILE_SDK

    buildTypes {
        getByName("release")

        getByName("debug") {
            isJniDebuggable = true
        }
    }
}

kotlin {
    android()

    iosArm64 {
        /* Apple iOS on ARM64 platforms (Apple iPhone 5s and newer) */
        this.binaries.framework {
            baseName = "aditogether"
        }
    }

    iosX64 {
        /* Apple iOS simulator on x86_64 platforms */
        this.binaries.framework {
            baseName = "aditogether"
        }
    }

    iosSimulatorArm64 {
        /* Apple iOS simulator on Apple Silicon platforms */
        this.binaries.framework {
            baseName = "aditogether"
        }
    }

    sourceSets {
        val commonMain by sourceSets.getting
        val commonTest by sourceSets.getting

        val androidMain by sourceSets.getting
        val androidUnitTest by sourceSets.getting

        val jvmMain by sourceSets.getting
        val jvmTest by sourceSets.getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosMain by sourceSets.creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}
