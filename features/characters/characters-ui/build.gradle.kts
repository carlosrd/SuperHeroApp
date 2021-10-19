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
        targetSdk = 30

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

    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.AndroidX.fragmentKtx)
    implementation(Libs.AndroidX.coordinatorLayout)
    implementation(Libs.AndroidX.cardView)
    implementation(Libs.AndroidX.Lifecycle.runtime)
    implementation(Libs.AndroidX.Paging.runtime)

    implementation(Libs.MaterialDesign.lib)

    implementation(Libs.RecyclerAdapterDelegates.lib)

    implementation(Libs.Kotlin.Coroutines.lib)

    implementation(project(":core:common-ui"))
    implementation(project(":core:common-domain"))

    implementation(project(":features:characters:characters-data"))
    implementation(project(":features:characters:characters-domain"))

    implementation(Libs.Dagger.lib)
    kapt(Libs.Dagger.compiler)

    testImplementation (Libs.Test.jUnit)
}