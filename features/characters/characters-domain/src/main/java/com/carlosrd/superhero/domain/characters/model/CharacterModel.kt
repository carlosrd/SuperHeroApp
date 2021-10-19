package com.carlosrd.superhero.domain.characters.model

data class CharacterModel(
    val id : Long,
    val name: String,
    val description: String,
    val imageUrl: String,
    val comics: List<String>? = null,
    val stories: List<String>? = null,
    val events: List<String>? = null,
    val series: List<String>? = null,
)

