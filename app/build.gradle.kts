plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.pref)
    alias(libs.plugins.google.service)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.parvelize)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.datn.bia.a"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.datn.bia.a"
        minSdk = 26
        targetSdk = 36
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
        dataBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // rcv
    implementation(libs.dep.rcv)
    // circle img
    implementation(libs.dep.circle.img)
    // crash
    implementation(libs.dep.custom.crash)
    // firebase
    implementation(platform(libs.dep.firebase.bom))
    implementation(libs.dep.firebase.analytics.ktx)
    implementation(libs.dep.firebase.config.ktx)
    implementation(libs.dep.firebase.crashlytics.ktx)
    implementation(libs.dep.firebase.messaging.ktx)
    implementation(libs.dep.firebase.perf)
    // hilt
    implementation(libs.dep.hilt)
    ksp(libs.dep.hilt.compiler)
    ksp(libs.dep.hilt.android.compiler)
    // room db
    implementation(libs.dep.room.runtime)
    implementation(libs.dep.room.ktx)
    ksp(libs.dep.room.compiler)
    // coroutines
    implementation(libs.dep.coroutines.core)
    implementation(libs.dep.coroutines.android)
    // glide
    implementation(libs.dep.glide)
    ksp(libs.dep.glide.compiler)
    // lottie
    implementation(libs.dep.lottie)
    // shimmer
    implementation(libs.dep.shimmer)
    // lifecycle
    implementation(libs.dep.livedata.ktx)
    implementation(libs.dep.viewmodel.ktx)
    // fragment
    implementation(libs.dep.fragment.ktx)
    // gson
    implementation(libs.dep.gson)
    // exo
    implementation(libs.dep.exo.core)
    implementation(libs.dep.exo.dash)
    implementation(libs.dep.exo.ui)
    // Networking
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.retrofit2.kotlin.coroutines.adapter)
    implementation(libs.sandwich)
    implementation(libs.sandwich.retrofit)
    implementation(libs.commons.codec)
    // Unit
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    implementation(files("libs/zalo_pay.aar"))
}