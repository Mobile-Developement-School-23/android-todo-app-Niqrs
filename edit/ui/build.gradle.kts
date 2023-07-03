plugins {
    id("android-setup")
    id("compose-setup")
}

android {
    namespace = ProjectConfig.namespace("edit.ui")
}

dependencies {
    implementation(project(":core:ui"))

    implementation(project(":edit:domain"))
}