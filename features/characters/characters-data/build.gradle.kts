plugins {
    id("com.carlosrd.superhero.dependencies")
    id("kotlin")
    kotlin("kapt")
}

dependencies {

    implementation(project(":core:common-domain"))
    implementation(project(":core:common-data"))

    implementation(project(":features:characters:characters-domain"))

    implementation(Libs.AndroidX.Paging.commonKtx)
    implementation(Libs.AndroidX.Room.common)

    implementation(Libs.Retrofit.lib)
    implementation(Libs.Retrofit.gsonConverter)

    implementation(Libs.Dagger.lib)
    kapt(Libs.Dagger.compiler)

    testImplementation (Libs.Test.jUnit)
    testImplementation (Libs.Test.mockk)
}
