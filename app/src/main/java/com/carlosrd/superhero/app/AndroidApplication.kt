package com.carlosrd.superhero.app

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.carlosrd.superhero.ui.di.component.ActivityComponent
import com.carlosrd.superhero.ui.di.component.ActivityComponentProvider
import com.carlosrd.superhero.di.ApplicationComponent
import com.carlosrd.superhero.di.DaggerApplicationComponent
import com.carlosrd.superhero.ui.characters.di.CharactersComponent
import com.carlosrd.superhero.ui.characters.di.CharactersComponentProvider

class AndroidApplication : Application(),
    ActivityComponentProvider,
    CharactersComponentProvider {

    private lateinit var appComponent : ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        initDagger()

    }

    private fun initDagger(){
        appComponent = DaggerApplicationComponent
            .builder()
            .module(ApplicationComponent.Module(this))
            .build()
    }

    override fun provideActivityComponent(activity: AppCompatActivity): ActivityComponent =
        appComponent.activityComponent()
            .module(ActivityComponent.Module(activity))
            .build()

    override fun provideCharactersComponent(): CharactersComponent =
        appComponent.charactersComponent().build()

}