plugins {
    id(BuildPlugins.javaLibrary)
    id(BuildPlugins.kotlin)
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dependencies.Kotlin.kotlin)
}
