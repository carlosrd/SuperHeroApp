package com.carlosrd.superhero.domain.characters.usecase

import com.carlosrd.superhero.domain.characters.CharactersRepository
import com.carlosrd.superhero.domain.characters.MainCoroutineRule
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import com.carlosrd.superhero.domain.either.Either
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule
import org.junit.Test


class GetCharacterDetailUseCaseTest {

    private val mockCharacterId = 1234L

    private val characterModelMock = CharacterModel(mockCharacterId,
        "mockName",
        "mockDescription",
        "mockImageUrl")


    private val charactersRepository : CharactersRepository
    private val getCharacterDetailUseCase : GetCharacterDetailUseCase

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    init {

        charactersRepository = mockk(){
            every { getCharacterDetails(any()) } returns Either.Success(characterModelMock)
        }

        getCharacterDetailUseCase = GetCharacterDetailUseCase(charactersRepository)

    }

    @Test
    fun `Test UseCase calls repository with same ID`(){

        getCharacterDetailUseCase.execute(mockCharacterId, TestCoroutineScope(), {})

        verify(exactly = 1) {  charactersRepository.getCharacterDetails(mockCharacterId) }

    }
}