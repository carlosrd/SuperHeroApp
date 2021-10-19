package com.carlosrd.superhero.data.characters.datasource.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters_cache_control")
data class CharactersCacheControlEntity(
    @SerializedName("cache_timestamp") val cacheTimestamp: Long?,
    @SerializedName("id") @PrimaryKey val id: Int = 1,
)