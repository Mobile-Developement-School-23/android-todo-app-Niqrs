object Dependencies {
    object Kotlin {
        const val ver = "1.8.21"

        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$ver"

        object Serialization {
            const val gradlePlugin = "org.jetbrains.kotlin:kotlin-serialization:$ver"
        }

        object Ksp {
            private const val version = "1.8.21-1.0.11"
            const val gradlePlugin = "com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:$version"
        }
    }

    object Android {
        private const val gradlePluginVersion = "8.0.2"
        const val gradlePlugin = "com.android.tools.build:gradle:$gradlePluginVersion"

        private const val activityComposeVersion = "1.7.2"
        private const val coreKtxVersion = "1.10.1"

        const val activityCompose = "androidx.activity:activity-compose:$activityComposeVersion"
        const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"
    }

    object Hilt {
        private const val version = "2.45"

        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"

        const val android = "com.google.dagger:hilt-android:$version"
        const val daggerAndroidCompiler = "com.google.dagger:hilt-android-compiler:$version" //kapt
        const val androidCompiler = "androidx.hilt:hilt-compiler:1.0.0"
        const val navigation = "androidx.hilt:hilt-navigation-compose:1.0.0"
        const val workManager = "androidx.hilt:hilt-work:1.0.0"
    }

    object Compose {
        private const val version = "1.4.3"
        private const val material3Version = "1.1.1"
        private const val navigationVersion = "2.6.0"

        const val ui = "androidx.compose.ui:ui:$version"
        const val tooling = "androidx.compose.ui:ui-tooling:$version"
        const val material3 = "androidx.compose.material3:material3:$material3Version"
        const val navigation = "androidx.navigation:navigation-compose:$navigationVersion"
    }

    object Room {
        private const val version = "2.5.2"

        const val roomKtx = "androidx.room:room-ktx:$version"
        const val roomCompiler = "androidx.room:room-compiler:$version" //ksp & annotationProcessor
    }

    object Ktor {
        private const val version = "2.3.1"

        const val core = "io.ktor:ktor-client-core:$version"
        const val cio = "io.ktor:ktor-client-cio:$version"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$version"
        const val serializationJson = "io.ktor:ktor-serialization-kotlinx-json:$version"
        const val slf4fNop = "org.slf4j:slf4j-nop:2.0.7"
    }

    object Testing {
        const val junit4 = "junit:junit:4.13.2" //testImplementation
        const val junitAndroidExt = "androidx.test.ext:junit:1.1.5" //androidTestImplementation
        const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1" //androidTestImplementation
    }

    object Other {
        private const val yandexAuthSdkVersion = "2.5.1"
        private const val datastoreVersion = "1.0.0"
        private const val workManagerVersion = "2.8.1"

        const val yandexAuthSdk = "com.yandex.android:authsdk:$yandexAuthSdkVersion"
        const val datastore = "androidx.datastore:datastore-preferences:$datastoreVersion"
        const val workManager = "androidx.work:work-runtime-ktx:$workManagerVersion"
    }
}