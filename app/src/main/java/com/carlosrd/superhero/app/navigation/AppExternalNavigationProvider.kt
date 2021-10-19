package com.carlosrd.superhero.app.navigation

import androidx.fragment.app.Fragment
import com.carlosrd.superhero.ui.navigation.ExternalNavigationProvider
import com.carlosrd.superhero.ui.characters.feature.CharactersContainerFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppExternalNavigationProvider @Inject constructor(): ExternalNavigationProvider {

    override fun getCharactersContainer(): Fragment =
        CharactersContainerFragment.newInstance()

}