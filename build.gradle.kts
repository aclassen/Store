buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath("com.android.tools.build:gradle:7.1.0")
        classpath ("org.jetbrains.kotlinx:atomicfu-gradle-plugin:0.17.0")
        classpath ("com.squareup.sqldelight:gradle-plugin:1.5.3")
    }

}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}