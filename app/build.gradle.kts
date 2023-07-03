plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
}

android {
    namespace = ProjectConfig.namespace("todoapp")
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        applicationId = ProjectConfig.applicationId
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = ProjectConfig.versionCode
        versionName = ProjectConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        manifestPlaceholders["YANDEX_CLIENT_ID"] = "0d0970774e284fa8ba9ff70b6b06479a"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = ProjectConfig.javaVersion
        targetCompatibility = ProjectConfig.javaVersion
    }
    kotlin {
        jvmToolchain(ProjectConfig.jvmTarget.toInt())
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ProjectConfig.kotlinCompilerExtensionVersion
    }
    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(project(":core:ui"))

    implementation(project(":auth:ui"))
    implementation(project(":auth:data"))

    implementation(project(":tasks:ui"))
    implementation(project(":tasks:data"))

    implementation(project(":edit:ui"))
    implementation(project(":edit:data"))

    implementation(project(":other:work"))

    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.activityCompose)

    //DI
    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.androidCompiler)
    implementation(Dependencies.Hilt.navigation)

    //Compose
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.tooling)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.navigation)

    //Yandex
    implementation(Dependencies.Other.yandexAuthSdk)

    //Datastore
    implementation(Dependencies.Other.datastore)

    //WorkManager
    implementation(Dependencies.Other.workManager)
    implementation(Dependencies.Hilt.workManager)

    //Room
    implementation(Dependencies.Room.roomKtx)
    annotationProcessor(Dependencies.Room.roomCompiler)
    ksp(Dependencies.Room.roomCompiler)

    //Ktor
    implementation(Dependencies.Ktor.core)
    implementation(Dependencies.Ktor.cio)
    implementation(Dependencies.Ktor.contentNegotiation)
    implementation(Dependencies.Ktor.serializationJson)
    implementation(Dependencies.Ktor.slf4fNop)

    //Test
    testImplementation(Dependencies.Testing.junit4)
    androidTestImplementation(Dependencies.Testing.junitAndroidExt)
    androidTestImplementation(Dependencies.Testing.espressoCore)
}

kapt {
    correctErrorTypes = true
}