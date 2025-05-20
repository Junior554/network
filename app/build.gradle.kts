plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.arise.network"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.arise.network"
        minSdk = 24
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
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))

    // Core Jetpack + Compose
    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.0")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.appcompat:appcompat:1.7.0")

    // Firebase BoM (ensures consistent versions)
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))

    // Firebase Services
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    dependencies {
        // Firebase Firestore
        implementation ("com.google.firebase:firebase-firestore-ktx:24.10.2")
    }


    // Google Sign-In & Credential Manager
//    implementation("androidx.credentials:credentials")
//    implementation("androidx.credentials:credentials-play-services-auth")
//    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.4.0")



    implementation ("androidx.activity:activity-compose:1.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)

    // Testing
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    debugImplementation("androidx.compose.ui:ui-tooling")
//    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
