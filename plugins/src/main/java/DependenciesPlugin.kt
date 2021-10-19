
import org.gradle.api.Plugin
import org.gradle.api.Project

open class DependenciesPlugin : Plugin<Project> {
    override fun apply(tarjet: Project) {
        //no-op

    }
}

object Libs {

    object Kotlin {

        private val version = "1.5.31"

        val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:$version"
        val jUnitTest = "org.jetbrains.kotlin:kotlin-test-junit:$version"

        object Coroutines {

            private val version = "1.5.2"

            val lib = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"

        }
    }

    object AndroidX {

        private val coreVersion = "1.6.0"
        private val coreKtxVersion = "1.6.0"
        private val fragmentKtxVersion = "1.3.6"
        private val appCompatVersion = "1.3.1"
        private val cardViewVersion = "1.0.0"
        private val constraintVersion = "2.1.1"
        private val coordinatorVersion = "1.1.0"


        val core = "androidx.core:core:$coreVersion"
        val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"
        val fragmentKtx = "androidx.fragment:fragment-ktx:$fragmentKtxVersion"
        val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
        val cardView = "androidx.cardview:cardview:$cardViewVersion"
        val constraintLayout = "androidx.constraintlayout:constraintlayout:$constraintVersion"
        val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:$coordinatorVersion"

        object Paging {

            private val pagingVersion = "3.0.1"

            val commonKtx = "androidx.paging:paging-common-ktx:$pagingVersion"
            val runtime = "androidx.paging:paging-runtime-ktx:$pagingVersion"

        }

        object Lifecycle {

            val version = "2.4.0-rc01" // "2.3.1"

            val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
        }

        object Room {
            val version = "2.3.0"

            val runtime = "androidx.room:room-runtime:$version"
            val compiler = "androidx.room:room-compiler:$version"
            val common = "androidx.room:room-common:$version"
            val roomKtx = "androidx.room:room-ktx:$version"

        }
    }

    object Gson {

        private val version = "2.8.7"
        val lib = "com.google.code.gson:gson:$version"

      }

    object MaterialDesign {

        private val version = "1.4.0"
        val lib = "com.google.android.material:material:$version"

    }

    object OkHttp {

        private val version = "4.9.1"

        val lib = "com.squareup.okhttp3:okhttp:$version"
        val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$version"

    }

    object Retrofit {

        private val version = "2.9.0"

        val lib = "com.squareup.retrofit2:retrofit:$version"
        val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"

    }

    object Glide {

        private val version = "4.12.0"

        val lib = "com.github.bumptech.glide:glide:$version"

    }

    object RecyclerAdapterDelegates {

        private val version = "4.3.0"

        val lib = "com.hannesdorfmann:adapterdelegates4:$version"
    }

    object Dagger {

        private val version = "2.38.1"

        val lib = "com.google.dagger:dagger:$version"
        val compiler = "com.google.dagger:dagger-compiler:$version"
    }

    object Test {

        private val jUnitVersion = "4.13.2"
        private val mockkVersion = "1.12.0"
        private val coroutinesVersion = "1.5.2"

        val jUnit = "junit:junit:$jUnitVersion"
        val mockk = "io.mockk:mockk:$mockkVersion"
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion"

    }
}
