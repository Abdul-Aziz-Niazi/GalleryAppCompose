plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp") version "1.8.0-1.0.9"
}

android {
    namespace = "com.abdulaziz.gallaryapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.abdulaziz.gallaryapp"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }


    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.1"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        testOptions {
            packagingOptions {
                jniLibs {
                    useLegacyPackaging = true
                }
            }
            excludes += "**/META-INF/LICENSE.md"
            excludes += "**/META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    with(KTX){
        implementation(coreKTX)
        implementation(lifecycleKTX)
    }

    with(Compose) {
        implementation(composeUI)
        implementation(composeMaterial)
        implementation(composeToolingDebug)
        implementation(composeToolingPreview)
        implementation(composeActivity)
        implementation(composeFoundation)
        implementation(composeLiveData)
    }
    //Navigation
    with(Destinations) {
        implementation(destinationCompose)
        ksp(destinationKSP)
    }

    with(Mockk) {
        testImplementation(mockk)
        androidTestImplementation(mockkAndroid)
    }
    with(Coroutines) {
        androidTestImplementation(coroutineAndroidTest)
        androidTestImplementation(coroutineTest)
        testImplementation(coroutineTest)
    }

    with(Glide) {
        implementation(glideCompose)
    }

    with(JUnit) {
        testImplementation(jUnit)
        androidTestImplementation(jUnitAndroid)
        androidTestImplementation(jUnitRunner)
    }
}