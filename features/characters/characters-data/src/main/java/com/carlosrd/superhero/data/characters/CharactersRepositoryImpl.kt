package com.carlosrd.superhero.data.characters

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.carlosrd.superhero.data.characters.datasource.remote.CharactersRemoteDataSource
import com.carlosrd.superhero.data.characters.datasource.remote.mappers.toDomain
import com.carlosrd.superhero.data.characters.di.annotation.MediatorPaging
import com.carlosrd.superhero.domain.characters.CharactersRepository
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import com.carlosrd.superhero.domain.either.Either
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepositoryImpl @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource,
    @MediatorPaging private val charactersPagingSource : PagingSource<Int, CharacterModel>,
): CharactersRepository {

    override fun getCharactersPaged(): Flow<PagingData<CharacterModel>> {
        // Use RemotePaging
        // return remoteDataSource.getPagedCharacters()

        // Use MediatorPaging (DB cache + Remote)
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { charactersPagingSource }
        ).flow
    }

    override fun getCharacterDetails(characterId: Long): Either<CharacterModel, String> {

        return when(val result = remoteDataSource.getCharacterDetails(characterId)){
            is Either.Success -> {
                Either.Success(result.data.data.results[0].toDomain())
            }
            is Either.Failure -> {
                result
            }
        }

    }

}



