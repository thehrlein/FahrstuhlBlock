plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinAndroidExtensions)
    id(BuildPlugins.kotlinKapt)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = Others.JVM_TARGET
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Modules
    implementation(project(Module.General.interactor))
    implementation(project(Module.General.entities))

    // AndroidX
    implementation(Dependencies.AndroidX.paging)
    implementation(Dependencies.AndroidX.Room.runtime)
    implementation(Dependencies.AndroidX.Room.extensions)
    kapt(Dependencies.AndroidX.Room.compiler)

    // Kotlin
    implementation(Dependencies.Kotlin.Coroutine.core)

    // Koin
    implementation(Dependencies.Koin.core)

    // Gson
    implementation(Dependencies.Other.gson)

    // Logging
    implementation(Dependencies.Other.timber)
}
