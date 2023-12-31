plugins {
    id("android-setup")
}

android {
    namespace = ProjectConfig.namespace("auth.data")
}

dependencies {
    implementation(project(":core:data"))

    implementation(project(":auth:domain"))
    implementation(project(":settings:domain"))

    //Datastore
    implementation(Dependencies.Other.preferencesDatastore)
}
