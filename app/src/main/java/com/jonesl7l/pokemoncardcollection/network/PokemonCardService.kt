package com.jonesl7l.pokemoncardcollection.network

import com.jonesl7l.pokemoncardcollection.model.PokeData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit service that takes a string query and returns a 'data' object containing card data
 * Set 'encoded' to true because we don't want certain characters encoded for our query
 */
interface PokemonCardService {

    @GET("/v2/cards")
    suspend fun getPokemonCards(@Query("q", encoded = true) query: String) : Response<PokeData>
}