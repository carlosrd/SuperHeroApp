plugins {
    id("com.carlosrd.superhero.dependencies")
    id("kotlin")
    kotlin("kapt")
}

dependencies {

    implementation(Libs.Kotlin.Coroutines.lib)
    implementation(Libs.AndroidX.Paging.commonKtx)

    implementation(project(":core:common-domain"))

    implementation(Libs.Dagger.lib)
    kapt(Libs.Dagger.compiler)

    testImplementation(Libs.Kotlin.Coroutines.lib)
    testImplementation(Libs.Test.jUnit)
    testImplementation(Libs.Test.mockk)
    testImplementation(Libs.Test.coroutines)
    
}
