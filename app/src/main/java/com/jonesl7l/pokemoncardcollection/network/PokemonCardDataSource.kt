package com.jonesl7l.pokemoncardcollection.network

import com.jonesl7l.pokemoncardcollection.R
import com.jonesl7l.pokemoncardcollection.model.PokeData
import timber.log.Timber
import javax.inject.Inject
import com.jonesl7l.pokemoncardcollection.model.Result
import com.jonesl7l.pokemoncardcollection.utils.appContext

class PokemonCardDataSource @Inject constructor(private val pokemonCardService: PokemonCardService) {

    suspend fun fetchPokemonCards(query: String): Result<PokeData?> {
        return try {
            val result = pokemonCardService.getPokemonCards(query)
            if (result.isSuccessful) {
                Timber.d("${result.body()}")
                Result.success(result.body())
            } else {
                Timber.e(Exception(appContext().getString(R.string.generic_error)))
                Result.error(appContext().getString(R.string.generic_error))
            }
        } catch (exception: Exception) {
            Timber.e(exception)
            Result.error(exception.message.orEmpty(), Error(exception))
        }
    }
}