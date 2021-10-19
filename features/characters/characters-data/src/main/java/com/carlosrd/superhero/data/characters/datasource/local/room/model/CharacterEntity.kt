package com.carlosrd.superhero.data.characters.datasource.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters")
data class CharacterEntity(
    @SerializedName("marvel_id") val marvelId : Long,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("id") @PrimaryKey(autoGenerate = true) val id : Long = 0,
)

