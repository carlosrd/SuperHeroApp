package com.carlosrd.superhero.domain.characters

import androidx.paging.PagingData
import com.carlosrd.superhero.domain.either.Either
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun getCharactersPaged() : Flow<PagingData<CharacterModel>>

    fun getCharacterDetails(characterId: Long) : Either<CharacterModel, String>

}