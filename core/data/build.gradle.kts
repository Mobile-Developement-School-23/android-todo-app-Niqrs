plugins {
    id("android-setup")
}

android {
    namespace = ProjectConfig.namespace("core.data")
}

dependencies {
    implementation(project(":core:di"))

    //Room
    api(Dependencies.Room.roomKtx)

    //Ktor
    implementation(Dependencies.Ktor.core)
    api(Dependencies.Ktor.cio)
    api(Dependencies.Ktor.serializationJson)
    implementation(Dependencies.Ktor.contentNegotiation)
    implementation(Dependencies.Ktor.slf4fNop)
}