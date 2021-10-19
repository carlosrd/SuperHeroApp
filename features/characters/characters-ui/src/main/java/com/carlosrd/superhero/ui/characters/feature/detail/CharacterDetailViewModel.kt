package com.carlosrd.superhero.ui.characters.feature.detail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.carlosrd.superhero.domain.either.Either
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import com.carlosrd.superhero.domain.characters.usecase.GetCharacterDetailUseCase
import com.carlosrd.superhero.ui.characters.feature.R
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.model.CharacterDetailDescriptionItem
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.model.CharacterDetailFeatureItem
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.model.CharacterDetailItem
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.model.CharacterDetailTitleItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CharacterDetailViewModel  @Inject constructor(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : ViewModel() {

    var characterId: Long = -1

    private var characterDetailsList : List<CharacterDetailItem> = listOf()

    private val _uiState = MutableStateFlow<CharacterDetailsUiState>(CharacterDetailsUiState.Loading())
    val uiState: StateFlow<CharacterDetailsUiState> = _uiState

    fun loadCharacterDetails(){

        if (characterId > 0){
            _uiState.value = CharacterDetailsUiState.Loading()
            getCharacterDetailUseCase.execute(characterId, viewModelScope, { result ->

                when (result){
                    is Either.Success -> {

                       buildList(result.data)

                    }
                    is Either.Failure -> {
                        _uiState.value =
                            CharacterDetailsUiState.OnDataError(result.error)
                    }
                }

            })

        }

    }

    private fun buildList(characterData: CharacterModel){

        val list : MutableList<CharacterDetailItem> = mutableListOf()

        if (hasEmptyDetails(characterData)) {

            list.add(CharacterDetailTitleItem(
                R.drawable.ic_info,
                R.string.characters_details_no_data_title))

            list.add(CharacterDetailDescriptionItem(
                R.string.characters_details_no_data_info))

        } else {

            if (characterData.description.isNotEmpty()){

                list.add(CharacterDetailTitleItem(
                    R.drawable.ic_info,
                    R.string.characters_details_description))

                list.add(CharacterDetailDescriptionItem(
                    characterData.description))
            }

            characterData.comics?.let{ comics ->
                list.addAll(buildFeatureList(R.drawable.ic_comic,
                    R.string.characters_details_comics,
                    comics))
            }

            characterData.series?.let{ series ->
                list.addAll(buildFeatureList(R.drawable.ic_series,
                    R.string.characters_details_series,
                    series))
            }

            characterData.events?.let{ events ->
                list.addAll(buildFeatureList(R.drawable.ic_event,
                    R.string.characters_details_events,
                    events))
            }

            characterData.stories?.let{ stories ->
                list.addAll(buildFeatureList(R.drawable.ic_stories,
                    R.string.characters_details_stories,
                    stories))
            }

        }

        characterDetailsList = list

        _uiState.value =
            CharacterDetailsUiState.OnDataReady(
                CharacterUiData(
                    characterData.name,
                    characterData.imageUrl,
                    characterDetailsList
                )
            )

    }

    private fun buildFeatureList(@DrawableRes featureIcon: Int,
                                 @StringRes featureTitle: Int,
                                 featureList: List<String>): List<CharacterDetailItem> {

        val uiList = mutableListOf<CharacterDetailItem>()

        if (featureList.isNotEmpty()){

            uiList.add(CharacterDetailTitleItem(
                featureIcon,
                featureTitle))

            featureList.forEach {
                uiList.add(CharacterDetailFeatureItem(it))
            }

            (uiList[uiList.size - 1] as CharacterDetailFeatureItem).separator = false

        }

        return uiList

    }

    private fun hasEmptyDetails(character : CharacterModel) : Boolean =
        character.description.isEmpty() &&
                character.comics.isNullOrEmpty() &&
                character.series.isNullOrEmpty() &&
                character.events.isNullOrEmpty() &&
                character.stories.isNullOrEmpty()

}

sealed class CharacterDetailsUiState {
    data class Loading(val showLoading: Boolean = true) : CharacterDetailsUiState()
    data class OnDataReady(val characterData : CharacterUiData ) : CharacterDetailsUiState()
    data class OnDataError(val error: String) : CharacterDetailsUiState()
}

data class CharacterUiData(
    val characterName : String,
    val imageUrl: String,
    val features: List<CharacterDetailItem>
)