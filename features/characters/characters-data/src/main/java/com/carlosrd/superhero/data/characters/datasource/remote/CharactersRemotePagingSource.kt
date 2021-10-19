package com.carlosrd.superhero.data.characters.datasource.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.carlosrd.superhero.data.characters.datasource.remote.mappers.toDomain
import com.carlosrd.superhero.data.characters.datasource.remote.model.CharactersDTO
import com.carlosrd.superhero.data.characters.datasource.remote.retrofit.CharactersService
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRemotePagingSource @Inject constructor(
    private val charactersService: CharactersService,
    private val apiUtils: ApiUtils,
) : PagingSource<Int, CharacterModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> = withContext(
        Dispatchers.IO) {

        try {
            // Start refresh at page 1 if undefined.
            val position = params.key ?: 0

            val commonParams = apiUtils.getCommonParams().toMutableMap()
            commonParams["offset"] = position.toString()

            val response = charactersService.getCharacters(commonParams).execute()

            if (response.isSuccessful){

                val charactersResponseDTO : CharactersDTO = response.body()!!
                val charactersList = charactersResponseDTO.data.results
                    .map { it.toDomain() }

                val nextKey = if (charactersList.isEmpty()) {
                    null
                } else {

                    // ensure we're not requesting duplicating items, at the 2nd request
                    position + charactersResponseDTO.data.count
                }

                return@withContext LoadResult.Page(
                    data =  charactersList,
                    prevKey = if (position == 0) null else position - 20,
                    nextKey = nextKey
                )

            } else {
                return@withContext LoadResult.Error(Exception("Response Error"))
            }

        } catch (e: IOException) {
            // IOException for network failures.
            return@withContext LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return@withContext LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(20) ?: anchorPage?.nextKey?.minus(20)
        }

    }

}