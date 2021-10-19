package com.carlosrd.superhero.data.characters.datasource.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.carlosrd.superhero.data.characters.datasource.remote.retrofit.CharactersService
import com.carlosrd.superhero.data.characters.datasource.remote.model.CharactersDTO
import com.carlosrd.superhero.data.characters.di.annotation.MediatorPaging
import com.carlosrd.superhero.data.characters.di.annotation.RemotePaging
import com.carlosrd.superhero.domain.either.Either
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRemoteDataSource @Inject constructor(
    private val charactersService: CharactersService,
    @RemotePaging private val charactersPagingSource : PagingSource<Int, CharacterModel>,
    private val apiUtils : ApiUtils,
) {

    fun getPagedCharacters(): Flow<PagingData<CharacterModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { charactersPagingSource }
        ).flow
    }

    fun getCharacterDetails(characterId: Long): Either<CharactersDTO, String> {

        try {
            val response = charactersService.getCharacterDetails(
                characterId,
                apiUtils.getCommonParams(),
            ).execute()

            when {
                response.isSuccessful -> {
                    return Either.Success(response.body()!!)
                }
                else -> {
                    return Either.Failure("Request error.\nPlease try again...")
                }
            }
        } catch (e: Exception){
            return Either.Failure("Request exception error.\nPlease try again...")
        }

    }

}