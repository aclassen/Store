pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "StoreKMP"
include(":androidApp")
include(":desktopApp")
include(":shared")
include(":cache-kmp")
include(":filesystem-kmp")
include(":multicast-kmp")
include(":store-kmp")



enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {

    versionCatalogs {
        create("libs") {
            version("okio", "3.0.0")
            version("ktor", "2.0.0-rc-1")
            version("sqldelight", "1.5.3")
            version("coroutines", "1.6.1")
            version("atomicfu", "0.17.2")
            version("lifecycle", "2.4.0")
            version("compose", "1.2.0-alpha08")
            version("activity-compose", "1.4.0")
            version("compose-navigation", "2.4.0-rc01")
            version("material", "1.5.0")
            version("kotlinx-serialization", "1.3.2")
            version("mockk", "1.12.2")
            version("swiperefresh","0.24.0-alpha")

            alias("okio_core").to("com.squareup.okio", "okio").versionRef("okio")
            alias("okio_jvm").to("com.squareup.okio", "okio-jvm").versionRef("okio")
            alias("coroutines_core").to("org.jetbrains.kotlinx", "kotlinx-coroutines-core").versionRef("coroutines")
            alias("coroutines_android").to("org.jetbrains.kotlinx", "kotlinx-coroutines-android").versionRef("coroutines")
            alias("coroutines_jvm").to("org.jetbrains.kotlinx", "kotlinx-coroutines-swing").versionRef("coroutines")
            alias("ktor_client_core").to("io.ktor", "ktor-client-core").versionRef("ktor")
            alias("ktor_client_android").to("io.ktor", "ktor-client-okhttp").versionRef("ktor")
            alias("ktor_client_ios").to("io.ktor", "ktor-client-darwin").versionRef("ktor")
            alias("ktor_client_jvm").to("io.ktor", "ktor-client-cio-jvm").versionRef("ktor")
            alias("ktor_serialization_core").to("io.ktor", "ktor-serialization-kotlinx-json").versionRef("ktor")
            alias("ktor_contentnegotiation").to("io.ktor", "ktor-client-content-negotiation").versionRef("ktor")



            alias("sqldelight_coroutines").to("com.squareup.sqldelight", "coroutines-extensions").versionRef("sqldelight")
            alias("sqldelight_driver_android").to("com.squareup.sqldelight", "android-driver").versionRef("sqldelight")
            alias("sqldelight_driver_native").to("com.squareup.sqldelight", "native-driver").versionRef("sqldelight")
            alias("sqldelight_driver_jvm").to("com.squareup.sqldelight", "sqlite-driver").versionRef("sqldelight")
            alias("serialization_json").to("org.jetbrains.kotlinx", "kotlinx-serialization-json").versionRef("kotlinx-serialization")
            alias("atomicfu").to("org.jetbrains.kotlinx", "atomicfu").versionRef("atomicfu")

            alias("android_material").to("com.google.android.material", "material").versionRef("material")
            alias("androidx_viewmodel").to("androidx.lifecycle", "lifecycle-viewmodel-ktx").versionRef("lifecycle")
            alias("androidx_lifecycle").to("androidx.lifecycle", "lifecycle-runtime-ktx").versionRef("lifecycle")
            alias("compose_activity").to("androidx.activity", "activity-compose").versionRef("activity-compose")
            alias("compose_material").to("androidx.compose.material", "material").versionRef("compose")
            alias("compose_viewmodel").to("androidx.lifecycle", "lifecycle-viewmodel-compose").versionRef("lifecycle")
            alias("compose_navigation").to("androidx.navigation", "navigation-compose").versionRef("compose-navigation")
            alias("compose_ui_tooling").to("androidx.compose.ui", "ui-tooling").versionRef("compose")
            alias("accompanist_swiperefresh").to("com.google.accompanist", "accompanist-swiperefresh").versionRef("swiperefresh")
        }
        create("testLibs") {
            version("coroutines", "1.6.0")
            version("mockk", "1.12.2")
            alias("coroutines").to("org.jetbrains.kotlinx", "kotlinx-coroutines-test").versionRef("coroutines")
            alias("mockk_core").to("io.mockk", "mockk").versionRef("mockk")
            alias("mockk_common").to("io.mockk", "mockk-common").versionRef("mockk")
        }
    }
}