plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.zee.amusicplayer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zee.amusicplayer"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug{
            applicationIdSuffix = ".debug"
            resValue("string","app_name","aMusicPlayer Debug")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            resValue("string","app_name","aMusicPlayer")

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
}

dependencies {

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.4")

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")

    implementation("androidx.media3:media3-session:1.4.1")
    implementation("androidx.media3:media3-exoplayer:1.4.1")
    implementation("androidx.media3:media3-ui:1.4.1")

    //compose
    implementation("androidx.activity:activity-compose:1.9.1")
    val bom = platform("androidx.compose:compose-bom:2023.09.00")

    implementation(bom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material")
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // accompanist
    implementation("com.google.accompanist:accompanist-permissions:0.36.0")

    //lottie
    implementation ("com.airbnb.android:lottie-compose:4.2.2")

    // breaking while updating
    implementation ("androidx.compose.material:material-icons-extended:1.7.0")

    // coil
    implementation("io.coil-kt:coil-compose:2.7.0")

    implementation("com.google.code.gson:gson:2.11.0")

    // workManager
    implementation("androidx.work:work-runtime-ktx:2.9.1")
}