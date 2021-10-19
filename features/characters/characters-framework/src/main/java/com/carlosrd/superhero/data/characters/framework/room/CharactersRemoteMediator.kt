package com.carlosrd.superhero.data.characters.framework.room
/*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.carlosrd.superhero.data.characters.datasource.remote.ApiUtils
import com.carlosrd.superhero.data.characters.datasource.remote.retrofit.CharactersService
import com.carlosrd.superhero.data.characters.model.CharactersResponseDTO
import com.carlosrd.superhero.data.characters.toDomain
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class CharactersRemoteMediator @Inject constructor(
    private val charactersService: CharactersService,
    private val database: SuperHeroDatabase,
) : RemoteMediator<Int, CharacterModel>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterModel>
    ): MediatorResult {

        val offset = when (loadType) {

            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }

        }

        try {

            val commonParams = ApiUtils.getCommonParams().toMutableMap()
            commonParams["offset"] = offset.toString()

            val response = charactersService.getCharacters(ApiUtils.getCommonParams()).execute()

            if (response.isSuccessful) {

                val charactersResponseDTO: CharactersResponseDTO = response.body()!!
                val charactersList = charactersResponseDTO.data.results
                    .map { it.toDomain() }
                val endOfPaginationReached = charactersList.isEmpty()
                database.withTransaction {
                    // clear all tables in the database
                    if (loadType == LoadType.REFRESH) {
                        database.charactersRemoteKeysDao().clearRemoteKeys()
                        database.charactersDao().clearAll()
                    }
                    val prevKey = if (offset == 0) null else offset - 20
                    val nextKey = if (endOfPaginationReached) null else offset + 20
                    val keys = charactersList.map {
                        CharacterRemoteKeyEntity(
                            characterId = it.id.toLong(),
                            prevKey = prevKey, nextKey = nextKey
                        )
                    }
                    database.charactersRemoteKeysDao().insertAll(keys)
                    val charactersListEntity = charactersList.map {
                        CharacterEntity(it.id, it.name, it.description, it.imageUrl)
                    }

                    database.charactersDao().insertAll(charactersListEntity)
                }
                return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } else {
                return MediatorResult.Error(Exception("API Error"))
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterModel>): CharacterRemoteKeyEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                // Get the remote keys of the last item retrieved
                database.charactersRemoteKeysDao().remoteKeysCharacterId(character.id.toLong())
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterModel>): CharacterRemoteKeyEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                // Get the remote keys of the first items retrieved
                database.charactersRemoteKeysDao().remoteKeysCharacterId(character.id.toLong())
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharacterModel>
    ): CharacterRemoteKeyEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { characterId ->
                database.charactersRemoteKeysDao().remoteKeysCharacterId(characterId.toLong())
            }
        }
    }
}
*/