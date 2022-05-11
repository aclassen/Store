plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "org.burnoutcrew.storekmp.android"
        minSdk = 21
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-alpha08"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.coroutines.android)
    implementation(libs.android.material)
    implementation(libs.compose.activity)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.viewmodel)
    implementation(libs.compose.navigation)
    implementation(libs.androidx.lifecycle)
    implementation(libs.accompanist.swiperefresh)
}