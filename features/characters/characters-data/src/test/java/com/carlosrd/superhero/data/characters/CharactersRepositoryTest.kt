package com.carlosrd.superhero.data.characters

import com.carlosrd.superhero.data.characters.datasource.remote.CharactersRemoteDataSource
import com.carlosrd.superhero.data.characters.datasource.remote.mappers.toDomain
import com.carlosrd.superhero.data.characters.datasource.remote.model.CharactersDTO
import com.carlosrd.superhero.domain.characters.CharactersRepository
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import com.carlosrd.superhero.domain.either.Either
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Test

class CharactersRepositoryTest {

    private val mockCharacterDTO : CharactersDTO
    private val mockCharacterId = 1234L
    private val mockCharacterIdError = 4321L
    private val mockCharacterName = "CharacterName"
    private val mockCharacterDescription = "CharacterDescription"
    private val mockErrorMessage = "Error"

    private val characterModelMock = CharacterModel(mockCharacterId,
        "mockName",
        "mockDescription",
        "mockImageUrl")

    private val remoteDataSource: CharactersRemoteDataSource

    private val charactersRepository : CharactersRepository

    init {

        val mockThumbnailDTO : CharactersDTO.Data.Results.Thumbnail = mockk {
            every { path } returns ""
            every { extension } returns ""
        }

        val mockResultsDTO : CharactersDTO.Data.Results = mockk(){
            every { id } returns mockCharacterId
            every { name } returns mockCharacterName
            every { description } returns mockCharacterDescription
            every { thumbnail } returns mockThumbnailDTO
            every { comics } returns CharactersDTO.Data.Results.Comics(0,0,"", listOf())
            every { series } returns CharactersDTO.Data.Results.Series(0,0,"", listOf())
            every { events } returns CharactersDTO.Data.Results.Events(0,0,"", listOf())
            every { stories } returns CharactersDTO.Data.Results.Stories(0,0,"", listOf())
        }

        mockCharacterDTO = mockk(){

            every { data.results } returns listOf(mockResultsDTO)
        }

        remoteDataSource = mockk(){
            every { getPagedCharacters() } returns mockk()
            every { getCharacterDetails(mockCharacterId) } returns Either.Success(mockCharacterDTO)
            every { getCharacterDetails(mockCharacterIdError) } returns Either.Failure(mockErrorMessage)
        }

        charactersRepository = CharactersRepositoryImpl(remoteDataSource,  mockk())

    }
/*
    @Test
    fun `Test GetCharacters with RemotePaging`(){

        charactersRepository.getCharactersPaged()

        verify(exactly = 1) { remoteDataSource.getPagedCharacters()}

    }

 */

    @Test
    fun `Test getCharacterDetails - Success`(){

        val result = charactersRepository.getCharacterDetails(mockCharacterId)

        verify(exactly = 1) { remoteDataSource.getCharacterDetails(mockCharacterId)}

        assert(result is Either.Success<CharacterModel>)
        assertEquals((result as Either.Success<CharacterModel>).data.name, mockCharacterName)
        assertEquals(result.data.id, mockCharacterId)
        assertEquals(result.data.name, mockCharacterName)
        assertEquals(result.data.description, mockCharacterDescription)

    }

    @Test
    fun `Test getCharacterDetails - Failure`(){

        val result = charactersRepository.getCharacterDetails(mockCharacterIdError)

        verify(exactly = 1) { remoteDataSource.getCharacterDetails(mockCharacterIdError)}

        assert(result is Either.Failure<String>)
        assertEquals((result as Either.Failure<String>).error, mockErrorMessage)

    }
}