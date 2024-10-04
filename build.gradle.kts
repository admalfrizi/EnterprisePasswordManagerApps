plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
//    id("io.github.ttypic.swiftklib") version "0.6.3"
}

subprojects {
    repositories {
        maven("https://us-central1-maven.pkg.dev/varabyte-repos/public")
        mavenCentral()
        google()
    }
}