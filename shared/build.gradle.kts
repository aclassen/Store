plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version "1.6.10"
    id("com.android.library")
    id("com.squareup.sqldelight")
}
version = "1.0"

kotlin {
    android()
    jvm()
    val iosTarget: (String, org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget.() -> Unit) -> org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iosTarget("ios") {}

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.okio.core)
                implementation(libs.coroutines.core)
                implementation(libs.sqldelight.coroutines)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.serialization.core)
                implementation(libs.serialization.json)
                implementation(project(":store"))
                implementation(project(":filesystem"))

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.sqldelight.driver.android)
                implementation(libs.ktor.client.android)
                implementation(libs.androidx.viewmodel)
            }
        }
        val androidAndroidTestRelease by getting
        val androidTest by getting {
            dependsOn(androidAndroidTestRelease)
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(libs.sqldelight.driver.native)
                implementation(libs.ktor.client.ios)
            }
        }
        val iosTest by getting
        val jvmMain by getting {
            dependencies {
                implementation(libs.sqldelight.driver.jvm)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.jvm)
                implementation(libs.ktor.serialization.jvm)
                implementation(libs.serialization.json)
            }
        }
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}

sqldelight {
    database("Database"){ // This will be the name of the generated database class.
        packageName = "org.burnoutcrew.storekmp.sqldelight.data"
    }
}