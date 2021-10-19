package com.carlosrd.superhero.domain.characters.usecase

import androidx.paging.PagingData
import com.carlosrd.superhero.domain.characters.CharactersRepository
import com.carlosrd.superhero.domain.characters.MainCoroutineRule
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import com.carlosrd.superhero.domain.either.Either
import com.carlosrd.superhero.domain.usecase.execute
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Rule
import org.junit.Test


class GetCharactersFlowUseCaseTest {

    private val mockCharacterId = 1234L

    private val characterModelMock = CharacterModel(mockCharacterId,
        "mockName",
        "mockDescription",
        "mockImageUrl")


    private val charactersRepository : CharactersRepository
    private val getCharactersFlowUseCase : GetCharactersFlowUseCase

    val flow = mockk<Flow<PagingData<CharacterModel>>>()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    init {

        charactersRepository = mockk(){
            every { getCharactersPaged() } returns flow
        }

        getCharactersFlowUseCase = GetCharactersFlowUseCase(charactersRepository)

    }

    @Test
    fun `Test UseCase calls repository with same ID`(){

        getCharactersFlowUseCase.execute(TestCoroutineScope(), {})

        verify(exactly = 1) { charactersRepository.getCharactersPaged() }

    }
}