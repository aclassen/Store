import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
}
dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.rx2)
    implementation(libs.coroutines.reactive)
    implementation(project(":store"))
    implementation(libs.rx2)

    testImplementation(testLibs.junit)
    testImplementation(testLibs.truth)
    testImplementation(testLibs.mockito)
    testImplementation(testLibs.coroutines)
}
val compileKotlin: KotlinCompile by tasks
val compileTestKotlin: KotlinCompile by tasks

java {
    targetCompatibility = JavaVersion.VERSION_1_8
    sourceCompatibility = JavaVersion.VERSION_1_8
}

compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
    freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}
