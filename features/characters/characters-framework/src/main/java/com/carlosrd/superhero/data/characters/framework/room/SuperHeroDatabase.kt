package com.carlosrd.superhero.data.characters.framework.room

import com.carlosrd.superhero.data.characters.datasource.local.room.CharacterDatabase
import com.carlosrd.superhero.data.characters.datasource.local.room.model.CharacterEntity
import com.carlosrd.superhero.data.characters.datasource.local.room.model.CharactersCacheControlEntity
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SuperHeroDatabase @Inject constructor(
    private val database: SuperHeroRoomDatabase): CharacterDatabase
{
    override fun getCharacters(offset: Int): List<CharacterModel> {
        return database.charactersDao().getCharactersPaged(offset)
            .map { CharacterModel(it.id, it.name,"", it.imageUrl)  }
    }

    override fun addCharacters(characterList: List<CharacterModel>, offset: Int) {
        database.charactersDao().insertAll(characterList.map {
            CharacterEntity(it.id, it.name, it.imageUrl, offset)
        })
    }

    override fun removeCharacters() {
        database.charactersDao().clearAll()
    }

    override fun updateCacheTimestamp(timestamp: Long) {
        database.charactersCacheControlDao().insert(CharactersCacheControlEntity(timestamp))
    }

    override fun getCacheTimestamp(): Long? =
        database.charactersCacheControlDao().getCacheStatus()?.cacheTimestamp

}