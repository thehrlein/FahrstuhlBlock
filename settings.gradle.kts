
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    includeBuild("build-logic")
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

val requiredJavaVersion = JavaVersion.VERSION_21
check(JavaVersion.current().isCompatibleWith(requiredJavaVersion)) {
    """
    AFKCSS requires JDK $requiredJavaVersion+ but it is currently using JDK ${JavaVersion.current()}.
    Java Home: [${System.getProperty("java.home")}]
    https://developer.android.com/build/jdks#jdk-config-in-studio
    """.trimIndent()
}

buildCache {
    local {
        directory = File(rootDir, ".gradle-build-cache")
    }
}

// enables to use projects accessors in build.gradle files like implementation(projects.core.model)
// which make it possible to use auto-completion and faster navigation with CMD/CTRL + click
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":app")

// ui
include(":feature:common")
include(":feature:menu")
include(":feature:gamesettings")
include(":feature:block")
include(":feature:savedgames")

// framework modules
include(":core:repositories")
include(":core:databaseroom")

// basic architecture modules
include(":core:presentation")
include(":core:interactor")
include(":core:entities")

rootProject.name = "FahrstuhlBlock"
