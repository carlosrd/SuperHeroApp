package com.carlosrd.superhero.domain.characters.usecase

import androidx.paging.PagingData
import com.carlosrd.superhero.domain.characters.CharactersRepository
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import com.carlosrd.superhero.domain.either.Either
import com.carlosrd.superhero.domain.usecase.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCharactersFlowUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) : UseCase<Unit, Flow<PagingData<CharacterModel>>>() {

    override suspend fun run(characterId: Unit): Flow<PagingData<CharacterModel>> {
        return charactersRepository.getCharactersPaged()
    }
}

