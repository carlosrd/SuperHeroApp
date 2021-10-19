package com.carlosrd.superhero.data.characters.framework.di

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.room.Room
import com.carlosrd.superhero.data.characters.datasource.local.room.CharacterDatabase
import com.carlosrd.superhero.data.characters.framework.room.SuperHeroDatabase
import com.carlosrd.superhero.data.characters.framework.room.SuperHeroRoomDatabase
import dagger.Binds
import dagger.Provides
import javax.inject.Singleton

@dagger.Module(includes = [
    CharactersDataFrameworkModule.AbstractCharacterDataFrameworkModule::class,
    ApiKeysModule::class])
class CharactersDataFrameworkModule {

    @Singleton
    @Provides
    fun bindSuperHeroDatabase(context: Context): SuperHeroRoomDatabase =
        Room.databaseBuilder(context,
            SuperHeroRoomDatabase::class.java,
            "super_hero.db")
        .build()

    @dagger.Module
    abstract class AbstractCharacterDataFrameworkModule {

        @Binds
        abstract fun bindCharacterDatabase(superHeroDatabaseImpl: SuperHeroDatabase) : CharacterDatabase

    }
}