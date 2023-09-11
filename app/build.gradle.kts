plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}

android {
    compileSdk = 34
    namespace = "com.zee.amusicplayer"

    defaultConfig {
        applicationId = "com.zee.amusicplayer"
        minSdk = 27
        targetSdk = 34
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

            resValue("string","app_name","aMusic")

        }
        getByName("debug") {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            resValue("string","app_name","aMusicDebug")

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
//        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val bom = platform("androidx.compose:compose-bom:2023.09.00")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(bom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(bom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation ("androidx.navigation:navigation-compose:2.7.2")


    // Compose dependencies
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
    implementation ("androidx.hilt:hilt-navigation-compose:1.1.0-alpha01")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.48")
    kapt( "com.google.dagger:hilt-android-compiler:2.48")

    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.30.1")
    implementation ("com.google.accompanist:accompanist-permissions:0.30.1")
    implementation ("com.google.accompanist:accompanist-navigation-material:0.30.1")
    implementation ("com.google.accompanist:accompanist-insets:0.30.1")



    val exoplayer_version = "2.19.1"
    implementation ("com.google.android.exoplayer:exoplayer-core:$exoplayer_version")
    implementation ("com.google.android.exoplayer:exoplayer-ui:$exoplayer_version")
    implementation ("com.google.android.exoplayer:extension-mediasession:$exoplayer_version")


    //lottie
    val lottieVersion = "4.2.2"
    implementation ("com.airbnb.android:lottie-compose:$lottieVersion")

    //glide
    implementation("com.github.bumptech.glide:compose:1.0.0-alpha.5")
    implementation("io.coil-kt:coil-compose:2.4.0")


    // breaking while updating
    implementation ("androidx.compose.material:material-icons-extended:$1.4.0")
}
