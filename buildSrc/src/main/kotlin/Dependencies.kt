import Config.Versions.android_plugin_version
import Config.Versions.kotlin
import Config.Versions.lifecycle
import Config.Versions.navigation
import Config.Versions.okhttp
import org.gradle.api.artifacts.dsl.DependencyHandler


object Config {

    object Versions {
        const val android_plugin_version = "3.5.1"

        const val kotlin = "1.3.50"
        const val kotlin_coroutines = "1.3.2"
        const val kotlin_serialization = "0.13.0"
        const val lifecycle = "2.2.0-rc02"
        const val navigation = "2.2.0-rc02"
        const val okhttp = "4.2.2"
        const val retrofit = "2.6.2"
        const val retrofit_kotlinx_serialization_converter = "0.4.0"
        const val dagger = "2.25.2"

    }

    object BuildPlugins {
        const val android_plugin = "com.android.tools.build:gradle:$android_plugin_version"
        const val kotlin_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin"
        const val serialization_plugin = "org.jetbrains.kotlin:kotlin-serialization:$kotlin"
        const val google_services_plugin = "com.google.gms:google-services:4.3.2"
        const val fabric_plugin = "io.fabric.tools:gradle:1.31.2"
        const val navigation_plugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$navigation"
    }

    object Libs {
        private const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin"
        private const val kotlin_android_extensions =
            "org.jetbrains.kotlin:kotlin-android-extensions-runtime:$kotlin"
        private const val kotlin_serialization =
            "org.jetbrains.kotlinx:kotlinx-serialization-runtime:${Versions.kotlin_serialization}"
        private const val kotlin_coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}"
        private const val kotlin_coroutines_android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"

        fun DependencyHandler.implementKotlin() {
            add("implementation", kotlin_stdlib)
            add("implementation", kotlin_android_extensions)
            add("implementation", kotlin_serialization)
            add("implementation", kotlin_coroutines)
            add("implementation", kotlin_coroutines_android)
        }

        private const val lifecycle_extensions =
            "androidx.lifecycle:lifecycle-extensions:$lifecycle"
        private const val lifecycle_extensions_ktx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle"
        private const val lifecycle_kapt = "androidx.lifecycle:lifecycle-common-java8:$lifecycle"
        fun DependencyHandler.implementLifecycle() {
            add("implementation", lifecycle_extensions)
            add("implementation", lifecycle_extensions_ktx)
            add("kapt", lifecycle_kapt)
        }

        private const val navigation_runtime =
            "androidx.navigation:navigation-runtime-ktx:$navigation"
        private const val navigation_fragment =
            "androidx.navigation:navigation-fragment-ktx:$navigation"
        private const val navigation_ui = "androidx.navigation:navigation-ui-ktx:$navigation"
        fun DependencyHandler.implementNavigation() {
            add("implementation", navigation_runtime)
            add("implementation", navigation_fragment)
            add("implementation", navigation_ui)
        }

        private const val ok_http = "com.squareup.okhttp3:okhttp:$okhttp"
        private const val ok_http_logging = "com.squareup.okhttp3:logging-interceptor:$okhttp"
        private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        private const val retrofit_kotlinx_serialization_converter =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:${Versions.retrofit_kotlinx_serialization_converter}"

        fun DependencyHandler.implementRetrofit() {
            add("implementation", ok_http)
            add("implementation", ok_http_logging)
            add("implementation", retrofit)
            add("implementation", retrofit_kotlinx_serialization_converter)
        }


        private const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
        private const val dagger_android_proc =
            "com.google.dagger:dagger-android-processor:${Versions.dagger}"
        private const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
        private const val dagger_android_support =
            "com.google.dagger:dagger-android-support:${Versions.dagger}"

        fun DependencyHandler.implementDagger() {
            add("kapt", dagger_compiler)
            add("kapt", dagger_android_proc)
            add("implementation", dagger)
            add("implementation", dagger_android_support)
        }


        private const val firebase_core = "com.google.firebase:firebase-core:17.2.0"
        fun DependencyHandler.implementFeribase() {
            add("implementation", firebase_core)
        }

    }

}
