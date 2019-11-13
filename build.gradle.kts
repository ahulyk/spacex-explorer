// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {

    repositories {
        google()
        jcenter()
        maven("https://maven.fabric.io/public")
    }
    dependencies {
        classpath(Config.BuildPlugins.android_plugin)
        classpath(Config.BuildPlugins.kotlin_plugin)
        classpath(Config.BuildPlugins.serialization_plugin)
        //Crashlytics
        classpath(Config.BuildPlugins.fabric_plugin)
        classpath(Config.BuildPlugins.google_services_plugin)
        classpath(Config.BuildPlugins.navigation_plugin)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://kotlin.bintray.com/kotlinx")
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
}