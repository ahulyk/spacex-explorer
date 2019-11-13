import Config.Libs.implementDagger
import Config.Libs.implementFeribase
import Config.Libs.implementKotlin
import Config.Libs.implementLifecycle
import Config.Libs.implementNavigation
import Config.Libs.implementRetrofit

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("kotlinx-serialization")
    id("io.fabric")
    id("com.google.gms.google-services")
    id("androidx.navigation.safeargs")
}

androidExtensions {
    isExperimental = true
}

kapt {
    useBuildCache = true
    mapDiagnosticLocations = true
}

android {

    compileSdkVersion(28)

    defaultConfig {
        applicationId = "net.hulyka.spacexviewer"
        minSdkVersion(21)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        multiDexEnabled = true
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        setProperty("archivesBaseName", "spacex-viewer-v$versionName-$versionCode")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    signingConfigs {
        create("release") {
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isShrinkResources = false
            isZipAlignEnabled = false
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            isZipAlignEnabled = true
            isDebuggable = false
//            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}


dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.*"))))
    implementKotlin()
    implementLifecycle()
    implementNavigation()
    implementRetrofit()
    implementDagger()
    implementFeribase()
    //TODO move all dep to buildScr project!!!
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core:1.1.0")
    implementation("androidx.core:core-ktx:1.1.0")
    implementation("com.google.android.material:material:1.0.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta3")
    implementation("androidx.recyclerview:recyclerview:1.1.0-rc01")
    implementation("androidx.vectordrawable:vectordrawable:1.1.0")
    implementation("com.jakewharton.timber:timber:4.7.1")
    implementation("com.crashlytics.sdk.android:crashlytics:2.10.1")
    implementation("com.github.bumptech.glide:glide:4.10.0")
    kapt("com.github.bumptech.glide:compiler:4.10.0")
    implementation("com.jakewharton.threetenabp:threetenabp:1.2.1")


    testImplementation("junit:junit:4.12")
    testImplementation("org.mockito:mockito-core:3.0.0")
    testImplementation("org.threeten:threetenbp:1.4.0") //Needed fir Unit test
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")


}
