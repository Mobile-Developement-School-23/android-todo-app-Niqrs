plugins {
    id("android-setup")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = ProjectConfig.namespace("tasks.data")
}

dependencies {
    implementation(project(":core:di"))
    implementation(project(":core:data"))

    implementation(project(":tasks:domain"))

    implementation(project(":auth:domain"))

    //Room
    annotationProcessor(Dependencies.Room.roomCompiler)
    ksp(Dependencies.Room.roomCompiler)
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}