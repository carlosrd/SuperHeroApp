plugins {
    id("com.carlosrd.superhero.dependencies")
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {

    buildFeatures {
        viewBinding = true
    }

    compileSdk = 31

    defaultConfig {
        minSdk = 21
        targetSdk = 31

        consumerProguardFiles("consumer-rules.pro")
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
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.Lifecycle.runtime)

    implementation(Libs.MaterialDesign.lib)

    implementation(Libs.Glide.lib)

    implementation(Libs.RecyclerAdapterDelegates.lib)

    implementation(Libs.Dagger.lib)
    kapt(Libs.Dagger.compiler)

    testImplementation (Libs.Test.jUnit)
}