plugins {
    kotlin("multiplatform")
    id("kotlinx-atomicfu")
    id("maven-publish")
}

version = "0.0.1"
group = "org.burnoutcrew.store"
kotlin {
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation (testLibs.coroutines)
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation (libs.atomicfu)
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
    }
}