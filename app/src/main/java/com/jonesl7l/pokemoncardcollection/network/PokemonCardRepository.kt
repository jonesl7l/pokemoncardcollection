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

class PokemonCardRepository @Inject constructor(private val dataSource: PokemonCardDataSource) {

    suspend fun fetchPokemonCards(): Flow<Result<PokeData?>> {
        return flow() {
            emit(Result.loading())
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