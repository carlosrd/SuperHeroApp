package com.carlosrd.superhero.ui.characters.di

import androidx.lifecycle.ViewModel
import com.carlosrd.superhero.ui.characters.feature.CharactersContainerViewModel
import com.carlosrd.superhero.ui.characters.feature.home.CharactersListViewModel
import com.carlosrd.superhero.ui.di.viewmodel.ViewModelKey
import com.carlosrd.superhero.ui.characters.feature.detail.CharacterDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal interface CharactersViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CharactersContainerViewModel::class)
    fun bindCharactersContainerViewModel(viewModel: CharactersContainerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharactersListViewModel::class)
    fun bindCharactersListViewModel(viewModel: CharactersListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailViewModel::class)
    fun bindCharacterDetailViewModel(viewModel: CharacterDetailViewModel): ViewModel

}