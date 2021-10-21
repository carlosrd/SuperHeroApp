package com.carlosrd.superhero.data.characters.datasource.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters")
data class CharacterEntity(
    @SerializedName("udid") @PrimaryKey val id : Long,
    @SerializedName("name") val name: String,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("offset") val offset : Int,
)

