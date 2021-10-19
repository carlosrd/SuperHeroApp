package com.carlosrd.superhero.ui.navigation

import androidx.fragment.app.Fragment

interface ExternalNavigationProvider {

    fun getCharactersContainer() : Fragment

}