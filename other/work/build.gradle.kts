plugins {
    id("android-setup")
}

android {
    namespace = ProjectConfig.namespace("other.work")
}

dependencies {
    implementation(project(":auth:domain"))
    implementation(project(":tasks:domain"))
    implementation(project(":other:alarm"))

    implementation(Dependencies.Android.coreKtx)

    //WorkManager
    api(Dependencies.Other.workManager)
}