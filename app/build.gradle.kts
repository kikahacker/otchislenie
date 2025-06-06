plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.otchislenie"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.otchislenie"
        minSdk = 26
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
}

dependencies {
    implementation("com.google.dagger:hilt-android:2.48")
    implementation(libs.litert.support.api) // Use latest version
    kapt("com.google.dagger:hilt-android-compiler:2.48")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2") // Use latest version
    implementation("androidx.activity:activity-ktx:1.8.0") // For by viewModels()
    implementation ("androidx.compose.foundation:foundation:1.5.0")
    implementation ("com.google.accompanist:accompanist-pager:0.30.1")
    implementation ("javax.inject:javax.inject:1")
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("io.insert-koin:koin-androidx-compose:4.1.0-Beta5")
    implementation("androidx.datastore:datastore-preferences:1.1.3")
    implementation("io.insert-koin:koin-compose:4.1.0-Beta5")
    implementation("androidx.navigation:navigation-compose:2.8.9")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("io.insert-koin:koin-compose-viewmodel:4.1.0-Beta5")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation(platform("androidx.compose:compose-bom:2025.01.01"))
    implementation("androidx.compose.material3:material3")
    implementation ("androidx.compose.material:material-icons-extended")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}