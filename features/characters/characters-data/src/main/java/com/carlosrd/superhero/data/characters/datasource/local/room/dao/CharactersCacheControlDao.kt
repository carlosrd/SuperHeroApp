package com.carlosrd.superhero.data.characters.datasource.local.room.dao

import androidx.room.*
import com.carlosrd.superhero.data.characters.datasource.local.room.model.CharactersCacheControlEntity

@Dao
interface CharactersCacheControlDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cacheControl: CharactersCacheControlEntity)

    @Query("SELECT * FROM characters_cache_control")
    fun getCacheStatus(): CharactersCacheControlEntity?

}

