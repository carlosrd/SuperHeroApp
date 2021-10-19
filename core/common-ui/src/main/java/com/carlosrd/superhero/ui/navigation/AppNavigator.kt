package com.carlosrd.superhero.ui.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.carlosrd.superhero.ui.R

class AppNavigator(private val fm: FragmentManager,
                   private val externalNavigatorProvider: ExternalNavigationProvider
){

    @IdRes val mainActivityPlaceholder = R.id.activity_placeholder

    fun goToCharacters() {
        replaceFragment(externalNavigatorProvider.getCharactersContainer())
    }

    private fun replaceFragment(fragment: Fragment){

        fm.beginTransaction()
            .replace(mainActivityPlaceholder, fragment)
           // .addToBackStack(fragment.tag)
            .commit()

    }

}