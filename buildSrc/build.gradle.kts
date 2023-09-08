plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

gradlePlugin {
    plugins.register("upload-tg-plugin") {
        id = "upload-tg-plugin"
        implementationClass = "UploadPlugin"
    }
}

dependencies {
    implementation(Dependencies.Kotlin.gradlePlugin)
    implementation(Dependencies.Android.gradlePlugin)
    implementation(Dependencies.Kotlin.Serialization.gradlePlugin)
    implementation(Dependencies.Kotlin.Ksp.gradlePlugin)

    implementation(Dependencies.Ktor.core)
    implementation(Dependencies.Ktor.cio)
    implementation(Dependencies.Ktor.serializationJson)
    implementation(Dependencies.Ktor.contentNegotiation)
    implementation(Dependencies.Ktor.slf4fNop)
}

kotlin {
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}