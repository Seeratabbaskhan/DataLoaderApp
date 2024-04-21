plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.dataloaderapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dataloaderapp"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        multiDexEnabled = true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    implementation ("androidx.lifecycle:lifecycle-process:2.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // multidex enabled
    implementation("androidx.multidex:multidex:2.0.1")

    // sdp library for different screen size set
    implementation("com.intuit.sdp:sdp-android:1.1.1")

    val room_version = "2.4.3"

    // Room components
    implementation ("androidx.room:room-ktx:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")
    androidTestImplementation ("androidx.room:room-testing:$room_version")

    // gson library
    implementation ("com.google.code.gson:gson:2.9.0")


    //lifecycle scope
    implementation( "androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

     // hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.48")

    implementation ("androidx.activity:activity-ktx:1.9.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")



}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}