package com.carlosrd.superhero.data.characters.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.carlosrd.superhero.data.characters.datasource.local.room.CharacterDatabase
import com.carlosrd.superhero.data.characters.datasource.remote.ApiUtils
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
class CharactersMediatorPagingSource @Inject constructor(
    private val charactersService: CharactersService,
    private val apiUtils: ApiUtils,
    private val database: CharacterDatabase,
) : PagingSource<Int, CharacterModel>() {

    val pageSize = 20
    val cacheExpireTime = 24 * 60 * 60 * 1000 // One day in millis

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterModel> = withContext(
        Dispatchers.IO) {

        try {
            // Start refresh at offset = 0 if undefined.
            val offset = params.key ?: 0

            validateCacheData(offset)

            // Load data from DB (cache)
            val charactersDb = database.getCharacters(offset)

            // There are data in local
            if (charactersDb.isNotEmpty()){

                // Move offset as much as items were added
                val nextKey = offset + charactersDb.size

                return@withContext buildLoadResult(charactersDb, offset, nextKey)

            } else {
                // We have to request it remotely

                val commonParams = apiUtils.getCommonParams().toMutableMap()
                commonParams["offset"] = offset.toString()

                // Load data from remote
                val response = charactersService.getCharacters(commonParams).execute()

                if (response.isSuccessful){

                    val charactersResponseDTO : CharactersDTO = response.body()!!
                    val charactersList = charactersResponseDTO.data.results
                        .map { it.toDomain() }

                    saveInCache(charactersList, offset)

                    val nextKey = if (charactersList.isEmpty()) {
                        null
                    } else {

                        // Move offset as much as items were added
                        offset + charactersResponseDTO.data.count
                    }

                    return@withContext buildLoadResult(charactersList, offset, nextKey)
                } else {
                    return@withContext LoadResult.Error(Exception("Response Error"))
                }
            }

        } catch (e: Exception) {
            return@withContext LoadResult.Error(Exception("Response Exception Error"))
        }

    }

    override fun getRefreshKey(state: PagingState<Int, CharacterModel>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(pageSize) ?: anchorPage?.nextKey?.minus(pageSize)
        }

    }

    private fun buildLoadResult(itemsLoaded: List<CharacterModel>, offset: Int, nextKey: Int?) =
        LoadResult.Page(
            data =  itemsLoaded,
            prevKey = if (offset == 0) null else offset - pageSize,
            nextKey = nextKey
        )

    private fun validateCacheData(offset: Int){

        val cacheTimestamp = database.getCacheTimestamp()

        if (offset == 0 && // Only when loading first element
            cacheTimestamp != null && // There are cached data
            Calendar.getInstance().timeInMillis > cacheTimestamp + cacheExpireTime) { // Cached data is expired
            database.removeCharacters()
        }

    }

    private fun saveInCache(charactersList: List<CharacterModel>, offset: Int){

        // Only when updating from the first element, we save cache timestamp
        if (offset == 0){
            database.updateCacheTimestamp(Calendar.getInstance().timeInMillis)
        }

        database.addCharacters(charactersList)

    }

}