plugins {
    id("com.carlosrd.superhero.dependencies")
    id("java-library")
    id("kotlin")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {

    //implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation(Libs.Kotlin.stdLib)
    implementation(Libs.Kotlin.Coroutines.lib)
 //   implementation("androidx.lifecycle:lifecycle-common:2.3.1")
}