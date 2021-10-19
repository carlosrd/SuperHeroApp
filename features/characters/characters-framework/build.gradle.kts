plugins {
    id("com.carlosrd.superhero.dependencies")
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")

}

android {

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

    implementation(project(":core:common-data"))
    implementation(project(":features:characters:characters-domain"))

    api(project(":features:characters:characters-data"))

    implementation(Libs.Kotlin.Coroutines.lib)
    
    implementation(Libs.AndroidX.Paging.runtime)

    implementation(Libs.AndroidX.Room.runtime)
    implementation(Libs.AndroidX.Room.roomKtx)
    kapt(Libs.AndroidX.Room.compiler)

    implementation(Libs.Retrofit.lib)
    implementation(Libs.Retrofit.gsonConverter)

    implementation(Libs.Dagger.lib)
    kapt(Libs.Dagger.compiler)

    testImplementation (Libs.Test.jUnit)
}

