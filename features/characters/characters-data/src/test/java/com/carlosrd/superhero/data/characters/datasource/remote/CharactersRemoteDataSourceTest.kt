package com.carlosrd.superhero.data.characters.datasource.remote

import com.carlosrd.superhero.data.characters.datasource.remote.model.CharactersDTO
import com.carlosrd.superhero.data.characters.datasource.remote.retrofit.CharactersService
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import com.carlosrd.superhero.domain.either.Either

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class CharactersRemoteDataSourceTest {

    private val mockCharacterDTO : CharactersDTO
    private val mockCharacterId = 1234L
    private val mockCharacterIdError = 4321L
    private val mockCharacterIdException = 9999L
    private val mockCharacterName = "CharacterName"
    private val mockCharacterDescription = "CharacterDescription"
    private val mockErrorMessage = "Error"

    private val charactersService : CharactersService
    private val charactersRemoteDataSource : CharactersRemoteDataSource

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

        val mockResponse : Response<CharactersDTO> = mockk() {
            every { body() } returns mockCharacterDTO
            every { isSuccessful } returns true
        }

        val mockResponseError : Response<CharactersDTO> = mockk() {
            every { isSuccessful } returns false
        }

        val mockCall : Call<CharactersDTO> = mockk() {
            every { execute() } returns mockResponse
        }

        val mockCallError : Call<CharactersDTO> = mockk() {
            every { execute() } returns mockResponseError
        }

        val mockCallException : Call<CharactersDTO> = mockk() {
            every { execute() } throws IOException()
        }

        charactersService = mockk() {
            every { getCharacterDetails(mockCharacterId, any()) } returns mockCall
            every { getCharacterDetails(mockCharacterIdError, any()) } returns mockCallError
            every { getCharacterDetails(mockCharacterIdException, any()) } returns mockCallException
        }
        charactersRemoteDataSource = CharactersRemoteDataSource(charactersService, mockk())
    }

    @Test
    fun `Test getCharacterDetails - Success`(){

        val result = charactersRemoteDataSource.getCharacterDetails(mockCharacterId)

        verify(exactly = 1) { charactersService.getCharacterDetails(mockCharacterId, any()) }

        assert(result is Either.Success<CharactersDTO>)
        assertEquals((result as Either.Success<CharactersDTO>).data.data.results[0].name, mockCharacterName)
        assertEquals(result.data.data.results[0].id, mockCharacterId)
        assertEquals(result.data.data.results[0].description, mockCharacterDescription)

    }

    @Test
    fun `Test getCharacterDetails - Failure`(){

        val result = charactersRemoteDataSource.getCharacterDetails(mockCharacterIdError)

        verify(exactly = 1) { charactersService.getCharacterDetails(mockCharacterIdError, any()) }

        assert(result is Either.Failure<String>)
        assertEquals((result as Either.Failure<String>).error,
            "Request error.\nPlease try again...")

    }

    @Test
    fun `Test getCharacterDetails - Failure by Exception`(){

        val result = charactersRemoteDataSource.getCharacterDetails(mockCharacterIdException)

        verify(exactly = 1) { charactersService.getCharacterDetails(mockCharacterIdException, any()) }

        assert(result is Either.Failure<String>)
        assertEquals((result as Either.Failure<String>).error,
            "Request exception error.\nPlease try again...")

    }
}