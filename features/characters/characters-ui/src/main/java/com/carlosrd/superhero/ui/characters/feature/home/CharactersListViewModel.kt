package com.carlosrd.superhero.ui.characters.feature.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import com.carlosrd.superhero.domain.characters.usecase.GetCharactersFlowUseCase
import com.carlosrd.superhero.domain.usecase.execute
import com.carlosrd.superhero.ui.characters.feature.home.adapter.CharacterListItem
import com.carlosrd.superhero.ui.extension.gone
import com.carlosrd.superhero.ui.extension.visible
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersListViewModel @Inject constructor(
    private val getCharactersFlowUseCase: GetCharactersFlowUseCase
) : ViewModel() {

    private val eventChannel = Channel<CharactersListEvent>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    private val _uiState = MutableStateFlow<CharactersListUiState>(CharactersListUiState.Loading())
    val uiState: StateFlow<CharactersListUiState> = _uiState

    init {
        loadCharacters()
    }

    private fun loadCharacters() {

        getCharactersFlowUseCase.execute(viewModelScope, { result ->
            result
                .cachedIn(viewModelScope)
                .map { pagingData ->
                    pagingData.map {

                        // Split names with "(...)"
                        if (it.name.contains("(")){

                            val split = it.name.split("(")

                            CharacterListItem(it.id,
                                it.imageUrl,
                                name = split[0],
                                nameSubtitle = split[1].replace(")","") )

                        } else {

                            CharacterListItem(it.id,
                                it.imageUrl,
                                name = it.name)

                        }
                    }
                }
                .collectLatest {
                    _uiState.value = CharactersListUiState.OnDataReady(it)
                }
        })

    }

    fun onCharacterClicked(characterId: Long) {

        viewModelScope.launch {
            eventChannel.send(
                CharactersListEvent.NavigateToCharacterDetails(characterId))
        }

    }

}

sealed class CharactersListUiState {
    data class Loading(val show: Boolean = true) : CharactersListUiState()
    data class OnDataReady(val pagingData: PagingData<CharacterListItem>) : CharactersListUiState()
}

sealed class CharactersListEvent {
    data class NavigateToCharacterDetails(val characterId: Long): CharactersListEvent()
    data class ShowSnackBar(val text: String): CharactersListEvent()
    data class ShowToast(val text: String): CharactersListEvent()
}