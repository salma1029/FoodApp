plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.org.jetbrains.kotlin.kapt)
    alias(libs.plugins.androidx.navigation.safe.args)
    alias(libs.plugins.hiltPlugin)
}

android {
    namespace = "com.first.foodapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.first.foodapp"
        minSdk = 24
        targetSdk = 34
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
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    implementation(libs.ssp.android)
    implementation(libs.sdp.android)

    implementation(libs.android.gif.drawable)

    implementation(libs.retrofit2)
    implementation(libs.converter.gson)
    implementation(libs.okhttp3)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.glide)
    kapt (libs.glide.compiler)


    implementation(libs.room.runtime)
    kapt (libs.room.compiler)
    implementation(libs.room.ktx)

    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)


    //Hilt
    implementation (libs.hilt.android)
    kapt(libs.dagger.compiler)
    kapt(libs.hilt.compiler)
}