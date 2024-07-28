plugins {
    id(BuildPlugins.androidLibrary)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.safeArgs)
}

android {
    namespace = "com.tobiapplications.fahrstuhlblock.ui_saved_games"
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

    buildFeatures {
        dataBinding = true
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = Others.JVM_TARGET
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Modules
    implementation(project(Module.Ui.common))
    implementation(project(Module.General.presentation))
    implementation(project(Module.General.interactor))
    implementation(project(Module.General.entities))

    // AndroidX
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.swipeToRefresh)
    implementation(Dependencies.AndroidX.LifeCycle.livedataExtensions)
    implementation(Dependencies.AndroidX.LifeCycle.viewModelExtensions)
    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.ui)

    // Koin
    implementation(Dependencies.Koin.android)

    // Logging
    implementation(Dependencies.Other.timber)
}
