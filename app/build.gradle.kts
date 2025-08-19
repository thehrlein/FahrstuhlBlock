import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

plugins {
    alias(libs.plugins.ohhell.android.application)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.kotlin.kapt)
}

val buildNumber = Integer.parseInt(
    SimpleDateFormat("yyyyMMddHH", Locale.getDefault()).format(
        Date()
    )
)

project.rootProject.ext.set("buildNumber", buildNumber)

android {
    val releaseAlias: String by project.rootProject.ext
    val releaseKeyPassword: String by project.rootProject.ext
    val releaseKeyStorePassword: String by project.rootProject.ext

    namespace = AppBuildConfig.applicationId

    defaultConfig {
        applicationId = AppBuildConfig.applicationId
        versionCode = buildNumber
        versionName = AppBuildConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            keyAlias = releaseAlias
            keyPassword = releaseKeyPassword
            storeFile = file("../signing/app/release_key.jks")
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

    buildFeatures.apply {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    // Modules
    implementation(projects.feature.common)
    implementation(projects.feature.menu)
    implementation(projects.feature.gamesettings)
    implementation(projects.feature.block)
    implementation(projects.feature.savedgames)

    implementation(projects.core.repositories)
    implementation(projects.core.databaseroom)
    implementation(projects.core.presentation)
    implementation(projects.core.interactor)
    implementation(projects.core.entities)

    // Google
    implementation(libs.google.material)
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.analytics)
    implementation(libs.google.firebase.crashlytics)
    implementation(libs.google.firebase.messaging)
    
    // AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    
    // Kotlin
    implementation(libs.kotlin.coroutines)

    // Koin (Dependency Injection)
    implementation(libs.koin)

    // Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logginginterceptor)

    // Logging
    implementation(libs.timber)

    // Images
    implementation(libs.coil)
}