plugins {
    id("android-setup")
}

android {
    namespace = ProjectConfig.namespace("tasks.data")
}

dependencies {
    implementation(project(":core:data"))

    implementation(project(":tasks:domain"))
}