package com.jonesl7l.pokemoncardcollection.model

data class Pokemon(
    val id: String,
    val name: String,
    val hp: String,
    val attacks: List<PokeAttack>,
    val abilities: List<PokeAbility>?,
    val set: PokeSet,
    val nationalPokedexNumbers: List<String>,
    val artist: String?
)

data class PokeData(val data: MutableList<Pokemon>)

data class PokeAttack(
    val name: String,
    val cost: List<String>,
    val text: String?,
    val damage: String
)

data class PokeAbility(
    val name: String,
    val text: String?,
    val type: String
)

data class PokeSet(var releaseDate: String)