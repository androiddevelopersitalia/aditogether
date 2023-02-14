plugins {
    id("aditogether.android.app")
    kotlin("android")
}

android {
    namespace = "aditogether.android.app"

    buildTypes {
        getByName("release")

        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }
}

dependencies {
    implementation(project(mapOf("path" to ":shared")))

}
