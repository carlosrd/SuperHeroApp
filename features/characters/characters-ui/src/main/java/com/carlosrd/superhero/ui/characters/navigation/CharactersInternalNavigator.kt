package com.carlosrd.superhero.ui.characters.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.carlosrd.superhero.ui.characters.feature.R
import com.carlosrd.superhero.ui.characters.feature.detail.CharacterDetailFragment
import com.carlosrd.superhero.ui.characters.feature.home.CharactersListFragment

import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class CharactersInternalNavigator @Inject constructor() {

    @IdRes
    val mainActivityPlaceholder = R.id.characters_container_placeholder

    fun goToCharactersList(fragmentManager: FragmentManager) {
        replaceFragment(fragmentManager, CharactersListFragment.newInstance())
    }

    fun goToCharacterDetails(fragmentManager: FragmentManager, characterId: Long) {
        replaceFragment(fragmentManager, CharacterDetailFragment.newInstance(characterId))
    }

    private fun replaceFragment(fm: FragmentManager,
                                fragment: Fragment,
                                addToBackStack : Boolean = true){

        val transaction = fm.beginTransaction()
            .replace(mainActivityPlaceholder, fragment)

            if (addToBackStack){
                transaction.addToBackStack(fragment.tag)
            }

            transaction.commit()

    }

    private fun <T: Fragment> replaceFragment(fm: FragmentManager,
                                              fragment: KClass<T>,
                                              bundle: Bundle? = null){

        fm.beginTransaction()
            .replace(mainActivityPlaceholder, fragment.java, bundle)
            .commit()

    }

}