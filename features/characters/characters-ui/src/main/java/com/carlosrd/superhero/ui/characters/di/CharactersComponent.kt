package com.carlosrd.superhero.ui.characters.di

import com.carlosrd.superhero.ui.characters.feature.CharactersContainerFragment
import com.carlosrd.superhero.ui.characters.feature.detail.CharacterDetailFragment
import com.carlosrd.superhero.ui.characters.feature.home.CharactersListFragment
import dagger.Subcomponent

@CharactersScope
@Subcomponent(modules = [CharactersComponent.Module::class])
interface CharactersComponent {

    fun inject(fragment: CharactersContainerFragment)
    fun inject(fragment: CharactersListFragment)
    fun inject(fragment: CharacterDetailFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): CharactersComponent
    }

    @dagger.Module(includes = [CharactersViewModelModule::class])
    abstract class Module {

    }
}

interface CharactersComponentProvider {
    fun provideCharactersComponent() : CharactersComponent
}