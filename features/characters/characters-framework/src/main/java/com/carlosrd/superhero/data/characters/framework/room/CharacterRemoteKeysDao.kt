package com.carlosrd.superhero.data.characters.framework.room


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
/*
@Dao
interface  CharacterRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<CharacterRemoteKeyEntity>)

    @Query("SELECT * FROM characters_remote_keys WHERE characterId = :characterId")
    suspend fun remoteKeysCharacterId(characterId: Long): CharacterRemoteKeyEntity?

    @Query("DELETE FROM characters_remote_keys")
    suspend fun clearRemoteKeys()

}*/