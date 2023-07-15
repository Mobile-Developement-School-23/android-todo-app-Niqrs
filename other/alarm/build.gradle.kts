plugins {
    id("android-setup")
}

android {
    namespace = ProjectConfig.namespace("other.alarm")
}

dependencies {
    implementation(project(":tasks:domain"))

    implementation(Dependencies.Android.coreKtx)
}