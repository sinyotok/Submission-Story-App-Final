plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
    alias(libs.plugins.googleAndroidLibrariesMapsplatformSecretsGradlePlugin)
}

android {
    namespace = "com.aryanto.storyappfinal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aryanto.storyappfinal"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "baseUrl", "\"https://story-api.dicoding.dev/v1/\"")

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
        freeCompilerArgs  += ("-opt-in=kotlin.RequiresOptIn")
    }

    buildFeatures{
        buildConfig = true
        viewBinding = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        animationsDisabled = true
    }

}

dependencies {

    // EXIF(Exchangeable Image File Format)
    implementation(libs.androidx.exifinterface)

    // Koin Dependency Injection
    implementation(libs.koin.android)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Okhttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Glide
    implementation(libs.glide)
    ksp(libs.compiler)

    // Map, location
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)

    //paging 3
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.room.paging)

    //room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)



    // Espresso
    implementation(libs.androidx.espresso.idling.resource) // Just for test

    androidTestImplementation(libs.androidx.espresso.intents)//IntentsTestRule
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.espresso.idling.resource)

    //Mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)

    testImplementation(libs.androidx.core.testing) // InstantTaskExecutorRule
    testImplementation(libs.kotlinx.coroutines.test) //TestDispatcher


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}