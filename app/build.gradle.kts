plugins {
    id("com.carlosrd.superhero.dependencies")
    id("com.android.application")
    id("com.google.secrets_gradle_plugin") version "0.4"
    kotlin("android")
    kotlin("kapt")
}

android {

    buildFeatures {
        viewBinding = true
    }

    compileSdk = 31

    defaultConfig {
        applicationId = "com.carlosrd.superhero.app"
        minSdk = 21
        targetSdk = 29
        versionCode = 1
        versionName = "1.0"

        //testInstrumentationRunner("androidx.test.runner.AndroidJUnitRunner")
    }

    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    implementation(Libs.Kotlin.stdLib)

    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.constraintLayout)
    implementation(Libs.AndroidX.cardView)
    
    implementation(Libs.AndroidX.Paging.runtime)

    implementation(Libs.AndroidX.Room.runtime)
    implementation(Libs.AndroidX.Room.roomKtx)
    kapt(Libs.AndroidX.Room.compiler)

    implementation(Libs.MaterialDesign.lib)

    implementation(Libs.Gson.lib)

    implementation(Libs.OkHttp.lib)
    implementation(Libs.OkHttp.loggingInterceptor)

    implementation(Libs.Retrofit.lib)
    implementation(Libs.Retrofit.gsonConverter)

    implementation(project(":core:common-ui"))
    implementation(project(":core:common-data"))
    implementation(project(":core:common-domain"))

    implementation(project(":features:characters:characters-framework"))
    implementation(project(":features:characters:characters-data"))
    implementation(project(":features:characters:characters-domain"))
    implementation(project(":features:characters:characters-ui"))

    implementation(Libs.Dagger.lib)
    kapt(Libs.Dagger.compiler)

    testImplementation(Libs.Test.jUnit)

}