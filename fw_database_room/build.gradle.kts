plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.ksp)
}

android {
    namespace = "com.tobiapplications.fahrstuhlblock.fw_database_room"
    compileSdk = AndroidSdkTools.compileSdk

    defaultConfig {
        minSdk = AndroidSdkTools.minSdk
        testInstrumentationRunner = Others.ANDROID_JUNIT_TEST_IMPLEMENTATION_RUNNER

        // possibility to colorize vector drawable in xml based on color resources (< API 24)
        vectorDrawables.useSupportLibrary = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = Others.JVM_TARGET
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Modules
    implementation(project(Module.General.interactor))
    implementation(project(Module.General.entities))

    // AndroidX
    implementation(Dependencies.AndroidX.Room.runtime)
    implementation(Dependencies.AndroidX.Room.extensions)
    ksp(Dependencies.AndroidX.Room.compiler)

    // Kotlin
    implementation(Dependencies.Kotlin.Coroutine.core)

    // Koin
    implementation(Dependencies.Koin.android)

    // Gson
    implementation(Dependencies.Google.gson)

    // Logging
    implementation(Dependencies.Other.timber)
}
