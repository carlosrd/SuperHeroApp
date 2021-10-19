# SuperHeroApp
Android App Example modularized with Kotlin + MVVM + Clean Architecture

App loads a list of Marvel Characters using [Marvel API](https://developer.marvel.com/docs#!/public/getSeriesCollection_get_25).
Users can navigate to Character Detail for displaying more information regarding selected Character like Comics, Series, Events, etc.
<p>
<img height="500" alt="Captura de pantalla 2021-10-19 a las 3 54 26" src="https://user-images.githubusercontent.com/2896275/137830984-01527118-22cd-4c1a-af1b-8504e722c9ef.png">
<img height="500" alt="Captura de pantalla 2021-10-19 a las 3 54 02" src="https://user-images.githubusercontent.com/2896275/137830988-c83226da-5cde-4c21-9744-1dd09c576e12.png">

<img height="500" alt="Captura de pantalla 2021-10-19 a las 3 51 40" src="https://user-images.githubusercontent.com/2896275/137831135-895612d8-de3c-4f6c-bfb6-d8e58f61b3e6.png">
<img height="500" alt="Captura de pantalla 2021-10-19 a las 3 53 23" src="https://user-images.githubusercontent.com/2896275/137831137-6bddc54b-52fd-4f2c-878d-cdada4fcccd1.png">
</p>

Setting up Project
-------
Before running the application, you need to get a **Marvel API Key** and declare it in your `local.properties` file:
```
## This file is automatically generated by Android Studio.
# Do not modify this file -- YOUR CHANGES WILL BE ERASED!
#
# This file should *NOT* be checked into Version Control Systems,
# as it contains information specific to your local configuration.
#
# Location of the SDK. This is only used by Gradle.
# For customization when using a Version Control System, please read the
# header note.
sdk.dir=C\:\\Android\\sdk

MARVEL_PUBLIC_API_KEY=d0c43...
MARVEL_PRIVATE_API_KEY=f3cc...
```
Libraries
=====
Dependencies are globally handled with a Gradle Plugin created for it (**plugins** module). Libraries versioning it's included in `DependenciesPlugin.kt`

* Uses [PagingLibrary](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) for loading Character list on demand. Also includes a PagingSource that acts as a mediator. This mediator caches data from network request for being used in next requests.
* Uses Dagger for dependency injection
* Uses Glide for Image loading
* Uses Room for database handling
* Uses Retrofit and OkHttp for remote connections

Architecture
====
App is totally split into modules, representing each module a layer in the Clean Architecture. Also, code is split into:
* **features:** Containing concrete features in the application like *Characters* modules. Other features could be *Login*, *Comics*, etc. Each feature is split in modules representing private layers in Clean Architecture
  * **characters-ui:** It's Character's UI or presentation layer (Android module)
  * **characters-domain:** It's business logic for Characters (Kotlin module)
  * **characters-data:** It's data provider for Characters Domain (Kotlin module)
  * **characters-framework:** Access to Android APIs for Data layer (Android module)
* **core:** Contains public or shared files for layers in Clean Architecture (e.g: **common-ui** to be used in **FEATURE-ui**)

Following images contains diagrams of interaction between layers in architecture described above:
<p>
<img src="https://user-images.githubusercontent.com/2896275/137827948-75759976-35ce-432d-8bf0-16fdd78a27c4.png" height="400" />
<img height="300" src="https://user-images.githubusercontent.com/2896275/137828271-72adf643-a954-44c7-afd4-abf4883abeb1.png"  />
</p>

Tests
====
Application includes some unit tests using [MockK library](https://mockk.io/). 

Future Tasks
===
* Implement local cache for Character Details screen so it will be able to load data offline if available
* Migrate to DaggerAndroid or DaggerHilt
* Completing unitary testing

