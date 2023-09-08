plugins {
    id("android-setup")
    id("compose-setup")
}

android {
    namespace = ProjectConfig.namespace("core.ui")
}

dependencies {

    //AppMetrica
    api(Dependencies.Other.appMetrica)
}