package com.carlosrd.superhero.di

import com.carlosrd.superhero.app.navigation.AppExternalNavigationProvider
import com.carlosrd.superhero.ui.navigation.ExternalNavigationProvider
import dagger.Binds

@dagger.Module
abstract class NavigationProviderModule {

    @Binds
    abstract fun bindExternalNavigationProvider(externalNavigationProvider: AppExternalNavigationProvider): ExternalNavigationProvider
}