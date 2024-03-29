// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

val releaseAlias: String? = gradleLocalProperties(rootDir).getProperty("releaseAlias") ?: System.getenv("RELEASEALIAS")
val releaseKeyPassword: String? = gradleLocalProperties(rootDir).getProperty("releaseKeyPassword") ?: System.getenv("RELEASEKEYPASSWORD")
val releaseKeyStorePassword: String? = gradleLocalProperties(rootDir).getProperty("releaseKeyStorePassword") ?: System.getenv("RELEASEKEYSTOREPASSWORD")

project.extra.apply {
    set("releaseAlias", releaseAlias)
    set("releaseKeyPassword", releaseKeyPassword)
    set("releaseKeyStorePassword", releaseKeyStorePassword)
}

plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false
    id("androidx.navigation.safeargs") version "2.6.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.22" apply false
    id("com.github.ben-manes.versions") version "0.47.0" apply false
    id("com.google.gms.google-services") version "4.3.3" apply false
    id("com.google.firebase.crashlytics") version "2.9.8" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
    id("io.gitlab.arturbosch.detekt") version "1.11.0" apply false
    id("com.google.devtools.ksp") version "1.8.22-1.0.11" apply false
}
//
//plugins {
//    id(BuildPlugins.detekt).version(BuildPlugins.detektVersion)
//}
//
//dependencies {
//    detektPlugins(BuildPlugins.detektFormatting)
//}

//subprojects {
//
//    apply { plugin(BuildPlugins.detekt) }
//    detekt {
//        ignoreFailures = true
//        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
//        baseline = file("${rootProject.projectDir}/config/detekt/baseline_config.xml")
//    }
//
//    project.plugins.whenPluginAdded {
//        when (this) {
//            is AppPlugin -> applyAppPlugin(this)
//            is LibraryPlugin -> applyLibraryPlugin(this, name)
//        }
//    }
//
//    tasks.withType<DependencyUpdatesTask> {
//
//        // optional parameters
//        checkForGradleUpdate = true
//        outputFormatter = "json"
//        outputDir = "build/dependencyUpdates"
//        reportfileName = "report"
//    }
//}
//fun applyAppPlugin(plugin: AppPlugin) {
//    println("-> App Plugin")
//    apply {
//        plugin(BuildPlugins.gradleUpdater)
//        from(BuildPlugins.projectDependencyGraph)
//    }
//
//    (extensions.getByName("android") as com.android.build.gradle.BaseExtension).apply {
//        compileSdkVersion(AndroidSdkTools.compileSdk)
//        defaultConfig {
//            applicationId = AndroidSdkTools.application_id
//            minSdkVersion(AndroidSdkTools.minSdk)
//            targetSdkVersion(AndroidSdkTools.targetSdk)
//            versionCode = AndroidSdkTools.version_code
//            versionName = AndroidSdkTools.version_name
//            testInstrumentationRunner = Others.ANDROID_JUNIT_TEST_IMPLEMENTATION_RUNNER
//
//            // possibility to colorize vector drawable in xml based on color resources (< API 24)
//            vectorDrawables.useSupportLibrary = true
//        }
//
//        signingConfigs {
//            create("release") {
//                keyAlias = releaseAlias
//                keyPassword = releaseKeyPassword
//                storeFile = file("signing/app/release_key.jks")
//                storePassword = releaseKeyStorePassword
//            }
//        }
//
//        buildTypes {
//            named("debug") {
//                isMinifyEnabled = false
//                applicationIdSuffix = ".debug"
//                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//            }
//            named("release") {
//                isMinifyEnabled = false
//                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
//                signingConfig = signingConfigs.getByName("release")
//            }
//        }
//
//        compileOptions {
//            setSourceCompatibility(1.8)
//            setTargetCompatibility(1.8)
//        }
//
//        tasks.withType<KotlinCompile>().all {
//            kotlinOptions {
//                jvmTarget = Others.JVM_TARGET
//            }
//        }
//
//        dataBinding {
//            isEnabled = true
//        }
//    }
//}
//
//fun applyLibraryPlugin(plugin: LibraryPlugin, name: String) {
//    println("-> LibraryPlugin")
//    apply { plugin(BuildPlugins.gradleUpdater) }
//
////    plugin.extension.apply {
////        compileSdkVersion(AndroidSdkTools.compileSdk)
////        defaultConfig {
////            minSdkVersion(AndroidSdkTools.minSdk)
////            targetSdkVersion(AndroidSdkTools.targetSdk)
////            testInstrumentationRunner = Others.ANDROID_JUNIT_TEST_IMPLEMENTATION_RUNNER
////
////            // possibility to colorize vector drawable in xml based on color resources (< API 24)
////            vectorDrawables.useSupportLibrary = true
////        }
////
////        compileOptions {
////            sourceCompatibility = JavaVersion.VERSION_17
////            targetCompatibility = JavaVersion.VERSION_17
////        }
////
////        tasks.withType<KotlinCompile>().all {
////            kotlinOptions {
////                jvmTarget = Others.JVM_TARGET
////            }
////        }
////
////        when {
////            name.startsWith("ui") -> {
////                println("-> UI Plugin")
////                dataBinding {
////                    isEnabled = true
////                }
////            }
////            name.startsWith("fw_network") -> {
////                println("-> Framework (Network) Plugin")
////            }
////        }
////    }
//}

tasks.register("clean").configure {
    delete("rootProject.buildDir")
}

val detektAllAutocorrect by tasks.registering(Detekt::class) {
    description = "Runs a failfast detekt build."
    setSource(file(projectDir))
    debug = true
    parallel = true
    buildUponDefaultConfig = true
    autoCorrect = true
    ignoreFailures = true
    config.setFrom(files("$projectDir/config/detekt/detekt.yml"))
    baseline.set(file("$projectDir/config/detekt/baseline_config.xml"))
    reports {
        html.enabled = true
    }
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}

val detektProjectBaseline by tasks.registering(DetektCreateBaselineTask::class) {
    description = "Overrides current baseline."
    ignoreFailures.set(true)
    parallel.set(true)
    buildUponDefaultConfig.set(true)
    setSource(files(rootDir))
    config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
    baseline.set(file("$rootDir/config/detekt/baseline_config.xml"))
    include("**/*.kt")
    include("**/*.kts")
    exclude("**/resources/**")
    exclude("**/build/**")
}
