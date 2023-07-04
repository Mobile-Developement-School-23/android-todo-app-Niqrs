plugins {
    id("android-setup")
}

android {
    namespace = ProjectConfig.namespace("other.work")
}

dependencies {
    implementation(project(":auth:domain"))
    implementation(project(":tasks:domain"))

    //Hilt
    kapt(Dependencies.Hilt.androidCompiler)

    //WorkManager
    api(Dependencies.Other.workManager)
    api(Dependencies.Hilt.workManager)
}