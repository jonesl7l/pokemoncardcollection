package com.jonesl7l.pokemoncardcollection.network

import com.jonesl7l.pokemoncardcollection.R
import com.jonesl7l.pokemoncardcollection.model.PokeData
import timber.log.Timber
import javax.inject.Inject
import com.jonesl7l.pokemoncardcollection.model.Result
import com.jonesl7l.pokemoncardcollection.utils.appContext

/**
 * Data source is the bridge between the network calls and the repository
 * It will tell the service what call to make and pass the result data onto the repository
 * Query we don't know at compile time so can't inject
 */
class PokemonCardDataSource @Inject constructor(private val pokemonCardService: PokemonCardService) {

    suspend fun fetchPokemonCards(query: String): Result<PokeData?> {
        return try {
            val result = pokemonCardService.getPokemonCards(query)
            if (result.isSuccessful) {
                Timber.d("${result.body()}")
                Result.Success(result.body())
            } else {
                Timber.e(Exception(appContext().getString(R.string.generic_error)))
                Result.Error(appContext().getString(R.string.generic_error))
            }
        } catch (exception: Exception) {
            Timber.e(exception)
            Result.Error(exception.message.orEmpty(), exception)
        }
    }
}