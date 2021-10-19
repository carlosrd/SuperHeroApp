plugins {
    id("com.carlosrd.superhero.dependencies")
    id("kotlin")
    kotlin("kapt")
}

dependencies {

    implementation(Libs.Gson.lib)

    implementation(Libs.OkHttp.lib)
    implementation(Libs.OkHttp.loggingInterceptor)

    implementation(Libs.Retrofit.lib)
    implementation(Libs.Retrofit.gsonConverter)

    implementation(Libs.Dagger.lib)
    kapt(Libs.Dagger.compiler)

    testImplementation (Libs.Test.jUnit)
}