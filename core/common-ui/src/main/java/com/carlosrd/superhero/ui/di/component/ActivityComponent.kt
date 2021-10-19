package com.carlosrd.superhero.ui.di.component

import androidx.appcompat.app.AppCompatActivity
import com.carlosrd.superhero.ui.activity.MainActivity
import com.carlosrd.superhero.ui.di.scope.ActivityScope
import com.carlosrd.superhero.ui.navigation.AppNavigator
import com.carlosrd.superhero.ui.navigation.ExternalNavigationProvider
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityComponent.Module::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    @Subcomponent.Builder
    interface Builder {

        fun module(module: Module) : Builder
        fun build(): ActivityComponent

    }

    @dagger.Module
    class Module(private val activity: AppCompatActivity) {

        @Provides
        @ActivityScope
        fun provideActivity() : AppCompatActivity = activity

        @Provides
        @ActivityScope
        fun provideAppNavigator(externalNavigationProvider: ExternalNavigationProvider) : AppNavigator =
            AppNavigator(activity.supportFragmentManager, externalNavigationProvider)

    }

}

interface ActivityComponentProvider {
    fun provideActivityComponent(activity: AppCompatActivity) : ActivityComponent
}