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
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.security)
    implementation(Dependencies.AndroidX.LifeCycle.livedataExtensions)

    // Kotlin
    implementation(Dependencies.Kotlin.Coroutine.core)

    // Gson
    implementation(Dependencies.Network.gsonConverter)

    // Koin
    implementation(Dependencies.Koin.core)

    // Logging
    implementation(Dependencies.Other.timber)
}
