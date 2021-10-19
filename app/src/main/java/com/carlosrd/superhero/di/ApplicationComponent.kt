package com.carlosrd.superhero.di

import android.app.Application
import android.content.Context
import com.carlosrd.superhero.app.AndroidApplication
import com.carlosrd.superhero.data.characters.di.CharactersDataModule
import com.carlosrd.superhero.data.characters.framework.di.CharactersDataFrameworkModule
import com.carlosrd.superhero.data.di.CommonDataModule
import com.carlosrd.superhero.ui.characters.di.CharactersComponent
import com.carlosrd.superhero.ui.di.component.ActivityComponent
import dagger.Component
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [
    ApplicationComponent.Module::class,
    CommonDataModule::class,
    NavigationProviderModule::class,
    CharactersComponent.Module::class,
    CharactersDataModule::class,
    CharactersDataFrameworkModule::class,
])
interface ApplicationComponent {

    fun inject(application: AndroidApplication)

    // Subcomponents
    fun activityComponent() : ActivityComponent.Builder
    fun charactersComponent() : CharactersComponent.Builder

    @Component.Builder
    interface Builder {

        fun module(module: Module): Builder
        fun build(): ApplicationComponent

    }

    @dagger.Module
    class Module(private val application: Application) {

        @Singleton
        @Provides
        fun provideApplicationContext(): Context = application.applicationContext

        @Singleton
        @Provides
        fun provideApplication(): Application = application

    }
}