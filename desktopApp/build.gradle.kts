
plugins {
    kotlin("multiplatform")
    application
}

group = "org.burnoutcrew"
version = "1.0"

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val jvmMain by getting{
            dependencies {
                implementation(project(":shared"))
                implementation(libs.coroutines.jvm)

            }
        }
    }
}
repositories {
    mavenCentral()
}

application {
    mainClass.set("MainKt")
}