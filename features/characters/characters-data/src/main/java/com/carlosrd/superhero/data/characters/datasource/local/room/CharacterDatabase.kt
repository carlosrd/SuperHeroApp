package com.carlosrd.superhero.data.characters.datasource.local.room

import androidx.paging.PagingSource
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharacterDatabase {

    fun getCharacters(offset:Int) : List<CharacterModel>

    fun addCharacters(characterList: List<CharacterModel>, offset: Int)

    fun removeCharacters()

    fun getCacheTimestamp(): Long?

    fun updateCacheTimestamp(timestamp: Long)

}