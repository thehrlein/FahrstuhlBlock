// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath(Classpaths.androidGradlePlugin)
        classpath(Classpaths.kotlinGradlePlugin)
        classpath(Classpaths.gradleUpdate)
        classpath(Classpaths.safeArgs)
        classpath(Classpaths.detektGradlePlugin)
        classpath(Classpaths.googleServices)
        classpath(Classpaths.firebaseCrashlytics)
        classpath(Classpaths.firebasePerformance)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts.kts.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        
    }
}

subprojects {
    project.plugins.whenPluginAdded {
        when (this) {
            is AppPlugin -> applyAppPlugin(this)
            is LibraryPlugin -> applyLibraryPlugin(this, name)
        }
    }

    tasks.withType<DependencyUpdatesTask> {

        // optional parameters
        checkForGradleUpdate = true
        outputFormatter = "json"
        outputDir = "build/dependencyUpdates"
        reportfileName = "report"
    }
}
fun applyAppPlugin(plugin: AppPlugin) {
    println("-> App Plugin")
    apply {
        plugin(BuildPlugins.gradleUpdater)
        from(BuildPlugins.projectDependencyGraph)
    }

    plugin.extension.apply {
        compileSdkVersion(AndroidSdkTools.compileSdk)
        defaultConfig {
            applicationId = AndroidSdkTools.application_id
            minSdkVersion(AndroidSdkTools.minSdk)
            targetSdkVersion(AndroidSdkTools.targetSdk)
            versionCode = AndroidSdkTools.version_code
            versionName = AndroidSdkTools.version_name
            testInstrumentationRunner = Others.ANDROID_JUNIT_TEST_IMPLEMENTATION_RUNNER
        }

        buildTypes {
            named("debug") {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
            named("release") {
                isMinifyEnabled = false
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }

        compileOptions {
            setSourceCompatibility(1.8)
            setTargetCompatibility(1.8)
        }

        tasks.withType<KotlinCompile>().all {
            kotlinOptions {
                jvmTarget = Others.JVM_TARGET
            }
        }

        dataBinding {
            isEnabled = true
        }
    }
}

fun applyLibraryPlugin(plugin: LibraryPlugin, name: String) {
    println("-> LibraryPlugin")
    apply { plugin(BuildPlugins.gradleUpdater) }

    plugin.extension.apply {
        compileSdkVersion(AndroidSdkTools.compileSdk)
        defaultConfig {
            minSdkVersion(AndroidSdkTools.minSdk)
            targetSdkVersion(AndroidSdkTools.targetSdk)
            testInstrumentationRunner = Others.ANDROID_JUNIT_TEST_IMPLEMENTATION_RUNNER
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        tasks.withType<KotlinCompile>().all {
            kotlinOptions {
                jvmTarget = Others.JVM_TARGET
            }
        }

        when {
            name.startsWith("ui") -> {
                println("-> UI Plugin")
                dataBinding {
                    isEnabled = true
                }
            }
            name.startsWith("fw_network") -> {
                println("-> Framework (Network) Plugin")
            }
        }
    }
}

tasks.register("clean").configure {
    delete("rootProject.buildDir")
}