package com.jonesl7l.pokemoncardcollection.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonesl7l.pokemoncardcollection.model.PokeData
import com.jonesl7l.pokemoncardcollection.model.Result
import com.jonesl7l.pokemoncardcollection.network.PokemonCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.collect

@HiltViewModel
class PokemonCardViewModel @Inject constructor(private val pokemonCardRepository: PokemonCardRepository) :
    ViewModel() {

    var pokeData: MutableLiveData<Result<PokeData?>?> = MutableLiveData()

    init {
        getPokemonCards()
    }

    fun getPokemonCards() {
        viewModelScope.launch {
            pokemonCardRepository.fetchPokemonCards().collect {
                pokeData.value = it
            }
        }
    }
}