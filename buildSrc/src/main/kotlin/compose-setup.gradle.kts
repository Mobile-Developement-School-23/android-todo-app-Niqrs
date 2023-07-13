plugins {
    id("com.android.library")
}

android {
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = ProjectConfig.kotlinCompilerExtensionVersion
    }
}

dependencies {
    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.tooling)
    implementation(Dependencies.Compose.material2)
    implementation(Dependencies.Compose.material3)
    implementation(Dependencies.Compose.navigation)
    implementation(Dependencies.Compose.lifecycleRuntime)
    implementation(Dependencies.Compose.Accompanist.navigationAnimation)
}
