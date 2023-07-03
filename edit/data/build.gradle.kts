plugins {
    id("android-setup")
}

android {
    namespace = ProjectConfig.namespace("edit.data")
}

dependencies {
    implementation(project(":core:data"))

    implementation(project(":edit:domain"))
}