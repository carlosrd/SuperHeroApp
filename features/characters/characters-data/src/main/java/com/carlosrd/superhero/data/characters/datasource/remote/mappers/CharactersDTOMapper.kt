package com.carlosrd.superhero.data.characters.datasource.remote.mappers

import com.carlosrd.superhero.data.characters.datasource.remote.model.CharactersDTO
import com.carlosrd.superhero.domain.characters.model.CharacterModel

fun CharactersDTO.Data.Results.toDomain() : CharacterModel {
    val imageUrl = "${thumbnail.path}.${thumbnail.extension}"
        .replace("http://", "https://") // Or Glide will fail
    val comicsList = comics.items.map { it.name }
    val storiesList = stories.items.map { it.name }
    val eventsList = events.items.map { it.name }
    val seriesList = series.items.map { it.name }

    return CharacterModel(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        comics = comicsList,
        stories = storiesList,
        events = eventsList,
        series = seriesList)
}