import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    id(BuildPlugins.androidApplication)
    id(BuildPlugins.kotlinAndroid)
    id(BuildPlugins.kotlinKapt)
    id(BuildPlugins.googleServices)
    id(BuildPlugins.firebaseCrashlytics)
    id(BuildPlugins.firebasePerformance)
}

android {
    val releaseAlias: String by project.rootProject.ext
    val releaseKeyPassword: String by project.rootProject.ext
    val releaseKeyStorePassword: String by project.rootProject.ext

    namespace = AndroidSdkTools.application_id

    compileSdk = AndroidSdkTools.compileSdk
    defaultConfig {
        applicationId = AndroidSdkTools.application_id
        minSdk = AndroidSdkTools.minSdk
        targetSdk = AndroidSdkTools.targetSdk
        versionCode = AndroidSdkTools.version_code
        versionName = AndroidSdkTools.version_name
        testInstrumentationRunner = Others.ANDROID_JUNIT_TEST_IMPLEMENTATION_RUNNER

        // possibility to colorize vector drawable in xml based on color resources (< API 24)
        vectorDrawables.useSupportLibrary = true
    }

    signingConfigs {
        create("release") {
            keyAlias = releaseAlias
            keyPassword = releaseKeyPassword
            storeFile = file("signing/app/release_key.jks")
            storePassword = releaseKeyStorePassword
        }
    }

    buildTypes {
        named("debug") {
            isMinifyEnabled = false
            applicationIdSuffix = ".debug"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = Others.JVM_TARGET
        }
    }

    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Modules
    implementation(project(Module.Ui.common))
    implementation(project(Module.Ui.menu))
    implementation(project(Module.Ui.gameSettings))
    implementation(project(Module.Ui.block))
    implementation(project(Module.Ui.savedGames))
    implementation(project(Module.Framework.repositories))
    implementation(project(Module.Framework.Database.room))
    implementation(project(Module.General.presentation))
    implementation(project(Module.General.interactor))
    implementation(project(Module.General.entities))

    // Google
    implementation(Dependencies.Google.material)
    implementation(platform(Dependencies.Google.Firebase.bom))
    implementation(Dependencies.Google.Firebase.analytics)
    implementation(Dependencies.Google.Firebase.crashlytics)
    implementation(Dependencies.Google.Firebase.performance)
    implementation(Dependencies.Google.Firebase.messaging)

    // AndroidX
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.coreKtx)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.LifeCycle.runtime)
    implementation(Dependencies.AndroidX.LifeCycle.viewModelExtensions)
    implementation(Dependencies.AndroidX.LifeCycle.livedataExtensions)
    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.ui)

    // Kotlin
    implementation(Dependencies.Kotlin.kotlin)
    implementation(Dependencies.Kotlin.Coroutine.core)

    // Koin (Dependency Injection)
    implementation(Dependencies.Koin.android)

    // Network
    implementation(Dependencies.Network.retrofit)
    implementation(Dependencies.Network.gsonConverter)
    implementation(Dependencies.Network.okHttp3)

    // Logging
    implementation(Dependencies.Other.timber)

    // Images
    implementation(Dependencies.Other.coil)
}

tasks.withType<DependencyUpdatesTask> {

    // optional parameters
    checkForGradleUpdate = true
    outputFormatter = "json"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
}