package com.carlosrd.superhero.data.characters.datasource.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.carlosrd.superhero.data.characters.datasource.local.room.model.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<CharacterEntity>)

    @Query("SELECT * FROM characters WHERE id > :offset LIMIT 20")
    fun getCharactersPaged(offset : Int): List<CharacterEntity>

    @Query("DELETE FROM characters")
    fun clearAll()

}

