plugins {
    id("android-setup")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = ProjectConfig.namespace("settings.data")
}

dependencies {
    implementation(project(":core:di"))
    implementation(project(":core:data"))

    implementation(project(":settings:domain"))

    implementation(Dependencies.Other.datastore)
    implementation(Dependencies.Kotlin.immutableCollections)
    implementation(Dependencies.Kotlin.Serialization.json)
}