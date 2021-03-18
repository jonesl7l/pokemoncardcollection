package com.jonesl7l.pokemoncardcollection.utils

/**
 * ID's of pokemon card for current collection
 */
object PokemonCardCollection {

    const val ALAKAZAM_PL2 = "pl2-103"
    const val CUBONE_DP6 = "dp6-90"
    const val HARIYAMA_EX8 = "ex8-100"
    const val VESPIQUEN_XY7 = "xy7-10"

    fun getCardCollection(): List<String> = listOf(VESPIQUEN_XY7, ALAKAZAM_PL2, CUBONE_DP6, HARIYAMA_EX8)
}

/**
 * Consts of special types of pokemon cards/abilities
 */
object PokemonCardConsts {

    const val POKE_POWER_TYPE = "Poké-Power"
}