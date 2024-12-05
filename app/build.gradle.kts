plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.themoviedb"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.themoviedb"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    viewBinding {
        enable = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    packagingOptions {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/DEPENDENCIES"
            )
        }
    }
}

dependencies {
    // Core AndroidX components
    implementation(libs.androidx.core.ktx)
    // Lifecycle (ViewModel, LiveData)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    // UI
    implementation(libs.androidx.material3)
    implementation(libs.androidx.swiperefreshlayout)
    // Navigation Component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    // Timber
    implementation(libs.timber)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    // Gson
    implementation(libs.gson)
    //Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    // --- Testing ---
    // Local Unit Tests
    testImplementation(libs.junit)
    testImplementation(libs.core.ktx)
    testImplementation(libs.androidx.junit.ktx)
    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.hilt.android.testing)
    // Instrumented Tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.core.testing)
    // Jetpack Compose debugging tools
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
