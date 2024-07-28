// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask

val releaseAlias: String? = gradleLocalProperties(rootDir, providers).getProperty("releaseAlias") ?: System.getenv("RELEASEALIAS")
val releaseKeyPassword: String? = gradleLocalProperties(rootDir, providers).getProperty("releaseKeyPassword") ?: System.getenv("RELEASEKEYPASSWORD")
val releaseKeyStorePassword: String? = gradleLocalProperties(rootDir, providers).getProperty("releaseKeyStorePassword") ?: System.getenv("RELEASEKEYSTOREPASSWORD")

project.extra.apply {
    set("releaseAlias", releaseAlias)
    set("releaseKeyPassword", releaseKeyPassword)
    set("releaseKeyStorePassword", releaseKeyStorePassword)
}

plugins {
    id("com.android.application") version "8.5.1" apply false
    id("com.android.library") version "8.5.1" apply false
    id("androidx.navigation.safeargs") version "2.7.7" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("com.github.ben-manes.versions") version "0.51.0" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("com.google.firebase.crashlytics") version "3.0.2" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.6" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.22" apply false
}

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
        html {
            required.set(true)
        }
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
