plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.ksp)

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")


}

android {
    namespace = "com.example.wfrleytask"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wfrleytask"
        minSdk = 30
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

 /*   sourceSets {
        getByName("main") {
            java {
                srcDir("build/generated/ksp/debug/java")
                srcDir("build/generated/ksp/debug/kotlin")
            }
        }
    }*/


    buildFeatures {
        viewBinding = true
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

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)



    implementation(libs.retrofit)
    implementation(libs.converter.gson)


    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.kotlin.stdlib)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.ksp)
    implementation(libs.coroutines.core)

    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)


    implementation(libs.glide)
}