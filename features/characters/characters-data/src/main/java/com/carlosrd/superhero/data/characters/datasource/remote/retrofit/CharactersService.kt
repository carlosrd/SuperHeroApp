package com.carlosrd.superhero.data.characters.datasource.remote.retrofit

import com.carlosrd.superhero.data.characters.datasource.remote.model.CharactersDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface CharactersService {

    @GET("/v1/public/characters")
    fun getCharacters(@QueryMap params : Map<String,String>) : Call<CharactersDTO>

    @GET("/v1/public/characters/{characterId}")
    fun getCharacterDetails(
        @Path("characterId") characterId: Long,
        @QueryMap params : Map<String,String>
    ) : Call<CharactersDTO>

}