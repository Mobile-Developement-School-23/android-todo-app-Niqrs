plugins {
    id("android-setup")
    id("compose-setup")
}

android {
    namespace = ProjectConfig.namespace("tasks.ui")
}

dependencies {
    implementation(project(":core:ui"))

    implementation(project(":tasks:domain"))
    implementation(project(":auth:domain"))

    implementation(project(":other:work"))
}