buildscript {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}

plugins {
    `kotlin-dsl`
}

allprojects {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        jcenter()
    }
}

dependencies {
    implementation(gradleApi())
    implementation(localGroovy())

    /*
    implementation(libs.kotlin.stdlib)
    implementation(libs.plugin.kotlin)
    implementation(libs.plugin.android.agp)
    implementation(libs.plugin.klint)
    implementation(libs.plugin.klint)*/
}
sourceSets {
    getByName("main"){
        java.srcDirs("src/main/java", "src/main/kotlin")
    }
}


gradlePlugin {
    plugins {
        create("Dependencies"){
            id = "com.carlosrd.superhero.dependencies"
            implementationClass = "DependenciesPlugin"
        }
    }
}