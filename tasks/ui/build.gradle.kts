plugins {
    id("android-setup")
    id("compose-setup")
}

android {
    namespace = ProjectConfig.namespace("tasks.ui")
}

dependencies {
    implementation(project(":core:ui"))
    implementation(project(":core:di"))

    implementation(project(":tasks:domain"))
    implementation(project(":auth:domain"))

    implementation(project(":settings:domain"))

    implementation(project(":other:work"))

    implementation(Dependencies.Compose.material2)
    implementation(Dependencies.Ktor.cio)
}