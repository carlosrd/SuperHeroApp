package com.carlosrd.superhero.data.characters.di

import androidx.paging.PagingSource
import com.carlosrd.superhero.data.characters.CharactersRepositoryImpl
import com.carlosrd.superhero.data.characters.datasource.CharactersMediatorPagingSource
import com.carlosrd.superhero.data.characters.datasource.remote.CharactersRemotePagingSource
import com.carlosrd.superhero.data.characters.datasource.remote.retrofit.CharactersService
import com.carlosrd.superhero.data.characters.di.annotation.MediatorPaging
import com.carlosrd.superhero.data.characters.di.annotation.RemotePaging
import com.carlosrd.superhero.domain.characters.CharactersRepository
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import dagger.Binds
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@dagger.Module(includes = [
    CharactersDataModule.AbstractCharactersDataModule::class])
class CharactersDataModule {

    @Singleton
    @Provides
    fun provideCharactersService(retrofit: Retrofit) : CharactersService =
        retrofit.create(CharactersService::class.java)



    @dagger.Module
    abstract class AbstractCharactersDataModule {

        @Binds
        internal abstract fun bindCharactersRepository(
            repositoryImpl: CharactersRepositoryImpl): CharactersRepository

        @Singleton
        @RemotePaging
        @Binds
        internal abstract fun provideRemotePagingSource(
            charactersPagingSource: CharactersRemotePagingSource) : PagingSource<Int, CharacterModel>

        @Singleton
        @MediatorPaging
        @Binds
        internal abstract fun provideMediatorPagingSource(
            charactersPagingSource: CharactersMediatorPagingSource) : PagingSource<Int, CharacterModel>

    }
}
