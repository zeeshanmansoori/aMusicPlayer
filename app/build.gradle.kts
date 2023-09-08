plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = 33
    namespace = "com.zee.amusicplayer"

    defaultConfig {
        applicationId = "com.zee.amusicplayer"
        minSdk = 27
        targetSdk = 33
        versionCode = 3
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isDebuggable = true

        }

    }

    compileOptions {
        sourceCompatibility =  JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose =  true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val compose_version = "1.4.0"
    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.compose.ui:ui:$compose_version")
    implementation ("androidx.compose.material:material:$compose_version")
    implementation ("androidx.compose.ui:ui-tooling-preview:$compose_version")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")
    implementation ("androidx.activity:activity-compose:1.7.2")
    testImplementation("junit:junit:4.+")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:$compose_version")
    debugImplementation ("androidx.compose.ui:ui-tooling:$compose_version")


    // Compose dependencies
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
    implementation ("androidx.compose.material:material-icons-extended:$compose_version")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0-rc01")
//    implementation (" androidx.navigation.compose:1.0.1")
    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")

    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.48")
    kapt( "com.google.dagger:hilt-android-compiler:2.48")
    //    kapt "androidx.hilt:hilt-compiler:1.0.0"
//    implementation ("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")


    //accomponist
    val acc_version = "0.22.0-rc"
    implementation ("com.google.accompanist:accompanist-systemuicontroller:$acc_version")
    implementation ("com.google.accompanist:accompanist-pager:$acc_version")
    implementation ("com.google.accompanist:accompanist-permissions:$acc_version")
    implementation ("com.google.accompanist:accompanist-navigation-animation:$acc_version")
    implementation ("com.google.accompanist:accompanist-navigation-material:$acc_version")
    implementation ("com.google.accompanist:accompanist-insets:$acc_version")

    //nav
    val nav_compose_version = "2.4.0-rc01"
    implementation ("androidx.navigation:navigation-compose:$nav_compose_version")


    val exoplayer_version = "2.19.1"
    implementation ("com.google.android.exoplayer:exoplayer-core:$exoplayer_version")
    implementation ("com.google.android.exoplayer:exoplayer-ui:$exoplayer_version")
    implementation ("com.google.android.exoplayer:extension-mediasession:$exoplayer_version")


    //lottie
    val lottieVersion = "4.2.2"
    implementation ("com.airbnb.android:lottie-compose:$lottieVersion")

    //coil
    val coilVersion = "2.4.0"
    implementation ("io.coil-kt:coil-compose:$coilVersion")
    implementation ("com.github.skydoves:landscapist-glide:1.4.4")
}

// AGP 8.1.