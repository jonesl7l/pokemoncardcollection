package com.jonesl7l.pokemoncardcollection.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonesl7l.pokemoncardcollection.model.PokeData
import com.jonesl7l.pokemoncardcollection.model.Result
import com.jonesl7l.pokemoncardcollection.network.PokemonCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * A ViewModel object provides the data for a specific UI component, such as a fragment or activity, and contains data-handling business logic to communicate with the model.
 * For example, the ViewModel can call other components to load the data, and it can forward user requests to modify the data.
 * The ViewModel doesn't know about UI components, so it isn't affected by configuration changes, such as recreating an activity when rotating the device.
 */
@HiltViewModel
class PokemonCardViewModel @Inject constructor(private val pokemonCardRepository: PokemonCardRepository) : ViewModel() {

    private var _pokeData: MutableLiveData<Result<PokeData?>?> = MutableLiveData()

    init {
        getPokemonCards()
    }

    fun getPokeData(): LiveData<Result<PokeData?>?> =_pokeData

    fun getPokemonCards() {
        viewModelScope.launch {
            pokemonCardRepository.fetchPokemonCards().collect {
                _pokeData.value = it
            }
        }
    }
}