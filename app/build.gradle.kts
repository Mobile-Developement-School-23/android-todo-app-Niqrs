plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("upload-tg-plugin")
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
    implementation(project(":core:di"))
    implementation(project(":core:ui"))
    implementation(project(":core:data"))

    implementation(project(":auth:ui"))
    implementation(project(":auth:domain"))
    implementation(project(":auth:data"))

    implementation(project(":tasks:ui"))
    implementation(project(":tasks:domain"))
    implementation(project(":tasks:data"))

    implementation(project(":edit:ui"))

    implementation(project(":settings:domain"))
    implementation(project(":settings:data"))

    implementation(project(":other:work"))
    implementation(project(":other:alarm"))

    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.activityCompose)
    implementation(Dependencies.Compose.Accompanist.navigationAnimation)

    //DI
    implementation(Dependencies.Dagger.dependency)
    kapt(Dependencies.Dagger.compiler)

    //Compose
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.tooling)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.navigation)

    //Accompanist
    implementation(Dependencies.Compose.Accompanist.permissions)

    //Test
    testImplementation(Dependencies.Testing.junit4)
    androidTestImplementation(Dependencies.Testing.junitAndroidExt)
}

kapt {
    correctErrorTypes = true
}

configure<UploadPluginExtension> {
    maxSizeMb.set(10f)
    apkName.set("todolist")
}
