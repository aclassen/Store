plugins {
    kotlin("multiplatform")
    id("com.android.library")
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

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.okio.core)
                implementation(libs.coroutines.core)
                implementation(project(":cache-kmp"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation(testLibs.mockk.common)
                implementation(libs.okio.core)
            }
        }
        val androidMain by getting{
            dependencies {
                api(libs.okio.core)
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
                implementation(libs.okio.core)
            }
        }
        val iosTest by getting

        val jvmMain by getting {
            dependencies {
                implementation(libs.okio.jvm)
                implementation(libs.coroutines.jvm)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(libs.okio.core)
                implementation(testLibs.mockk.core)
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