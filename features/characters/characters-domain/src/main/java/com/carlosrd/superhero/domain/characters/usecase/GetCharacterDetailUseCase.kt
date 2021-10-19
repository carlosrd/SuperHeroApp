package com.carlosrd.superhero.domain.characters.usecase

import com.carlosrd.superhero.domain.either.Either
import com.carlosrd.superhero.domain.characters.CharactersRepository
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import com.carlosrd.superhero.domain.usecase.UseCase
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) : UseCase<Long, Either<CharacterModel, String>>() {

    override suspend fun run(characterId: Long): Either<CharacterModel, String> {
        return charactersRepository.getCharacterDetails(characterId)
    }


}