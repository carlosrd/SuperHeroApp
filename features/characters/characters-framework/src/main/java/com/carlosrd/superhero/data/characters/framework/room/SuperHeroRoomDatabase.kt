package com.carlosrd.superhero.data.characters.framework.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.carlosrd.superhero.data.characters.datasource.local.room.dao.CharacterDao
import com.carlosrd.superhero.data.characters.datasource.local.room.dao.CharactersCacheControlDao
import com.carlosrd.superhero.data.characters.datasource.local.room.model.CharacterEntity
import com.carlosrd.superhero.data.characters.datasource.local.room.model.CharactersCacheControlEntity


@Database(
    entities = [
        CharacterEntity::class,
        CharactersCacheControlEntity::class],
    version = 1,
    exportSchema = false
)
abstract class SuperHeroRoomDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharacterDao
    abstract fun charactersCacheControlDao(): CharactersCacheControlDao

}
