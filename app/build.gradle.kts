plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.dagger.plugin)
}

android {
    namespace = "com.k10tetry.bloodsugr"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.k10tetry.bloodsugr"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    // Memory Leak
    debugImplementation(libs.leakcanary.android)

    // Unit Test
    testImplementation(libs.android.test.core.ktx)
    testImplementation(libs.junit)
    testImplementation(libs.google.truth)
    testImplementation(libs.mockito)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.androidx.arch.core)

    // Instrument Test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.coroutines.test)
    androidTestImplementation(libs.google.truth)
    androidTestImplementation(libs.androidx.arch.core)

    // DI
    implementation(libs.google.hilt)
    kapt(libs.google.hilt.compiler)
    kapt(libs.hilt.compiler)

    // Local
    implementation(libs.datastore.preferences)
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
}