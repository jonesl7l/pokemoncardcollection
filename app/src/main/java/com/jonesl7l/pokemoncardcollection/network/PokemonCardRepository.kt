package com.jonesl7l.pokemoncardcollection.network

import com.jonesl7l.pokemoncardcollection.model.PokeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.jonesl7l.pokemoncardcollection.model.Result
import com.jonesl7l.pokemoncardcollection.utils.PokemonCardCollection
import kotlinx.coroutines.flow.flowOn
import java.net.URLEncoder

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository modules handle data operations. They provide a clean API so that the rest of the app can retrieve this data easily.
 * They know where to get the data from and what API calls to make when data is updated.
 * You can consider repositories to be mediators between different data sources, such as persistent models, web services, and caches.
 */
@Singleton
class PokemonCardRepository @Inject constructor(private val dataSource: PokemonCardDataSource) {

    suspend fun fetchPokemonCards(): Flow<Result<PokeData?>> {
        return flow {
            emit(Result.Loading)
            val result = dataSource.fetchPokemonCards(getPokemonCardsQueryString())
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

    private fun getPokemonCardsQueryString(): String {
        var queryString = "("
        PokemonCardCollection.getCardCollection().forEachIndexed { index, cardId ->
            queryString = "${queryString}id:$cardId"
            queryString = if (index < PokemonCardCollection.getCardCollection().size - 1) {
                "$queryString or "
            } else {
                "$queryString)"
            }
        }
        return queryString
    }
}