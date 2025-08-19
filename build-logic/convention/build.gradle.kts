plugins {
    `kotlin-dsl`
}

group = "com.tobiapplications.fahrstuhlblock.buildlogic"

kotlin {
    jvmToolchain(21)
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.detekt.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        // Android
        register("androidApplication") {
            id = libs.plugins.ohhell.android.application.get().pluginId
            implementationClass = "plugins.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.ohhell.android.library.get().pluginId
            implementationClass = "plugins.AndroidLibraryConventionPlugin"
        }

        // Kotlin
        register("kotlinLibrary") {
            id = libs.plugins.ohhell.kotlin.library.get().pluginId
            implementationClass = "plugins.KotlinLibraryConventionPlugin"
        }

        // Project
        register("project") {
            id = libs.plugins.ohhell.project.get().pluginId
            implementationClass = "plugins.ProjectConventionPlugin"
        }
    }
}
