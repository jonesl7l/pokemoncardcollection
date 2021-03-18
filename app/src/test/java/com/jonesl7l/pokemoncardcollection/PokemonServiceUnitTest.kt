package com.jonesl7l.pokemoncardcollection

import com.jonesl7l.pokemoncardcollection.model.*
import com.jonesl7l.pokemoncardcollection.network.AppModule
import com.jonesl7l.pokemoncardcollection.network.PokemonCardService
import com.jonesl7l.pokemoncardcollection.utils.PokemonCardCollection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import javax.inject.Inject

/**
 * Unit Tests for the PokemonService Api
 * Wouldn't have experimental code in develop but I think in tests it's okay as to my knowledge they don't get compiled with the app bundle
 * Had to use runBlocking because of the coroutine network calls; have used delays before to allow network calls to complete outwith coroutines
 */
@ExperimentalCoroutinesApi
class PokemonServiceUnitTest @Inject constructor(private val pokemonCardService: PokemonCardService) {

    @Test
    fun dataDownloadedIsSuccess() {
        runBlocking {
            val result =
                pokemonCardService.getPokemonCards("(id:${PokemonCardCollection.VESPIQUEN_XY7}%20or%20id:${PokemonCardCollection.ALAKAZAM_PL2}%20or%20id:${PokemonCardCollection.CUBONE_DP6}%20or%20id:${PokemonCardCollection.HARIYAMA_EX8})")
            assertTrue(result.isSuccessful)
        }
    }

    @Test
    fun cuboneDataIsCorrect() {
        runBlocking {
            val result = pokemonCardService.getPokemonCards("id:${PokemonCardCollection.CUBONE_DP6}")
            assertEquals(cubone, result.body()?.data?.firstOrNull())
        }
    }

    @Test
    fun alakazamDataIsCorrect() {
        runBlocking {
            val result = pokemonCardService.getPokemonCards("id:${PokemonCardCollection.ALAKAZAM_PL2}")
            assertEquals(alakazam, result.body()?.data?.firstOrNull())
        }
    }

    @Test
    fun hariyamaDataIsCorrect() {
        runBlocking {
            val result = pokemonCardService.getPokemonCards("id:${PokemonCardCollection.HARIYAMA_EX8}")
            assertEquals(hariyamaEx, result.body()?.data?.firstOrNull())
        }
    }

    @Test
    fun vespiquenDataIsCorrect() {
        runBlocking {
            val result = pokemonCardService.getPokemonCards("id:${PokemonCardCollection.VESPIQUEN_XY7}")
            assertEquals(vespiquen, result.body()?.data?.firstOrNull())
        }
    }

    @Test
    fun currentCollectionDownloadIsCorrect() {
        runBlocking {
            val result =
                pokemonCardService.getPokemonCards("(id:${PokemonCardCollection.VESPIQUEN_XY7}%20or%20id:${PokemonCardCollection.ALAKAZAM_PL2}%20or%20id:${PokemonCardCollection.CUBONE_DP6}%20or%20id:${PokemonCardCollection.HARIYAMA_EX8})")
            val expectedResult = PokeData(mutableListOf(cubone, hariyamaEx, alakazam, vespiquen))
            assertEquals(expectedResult, result.body())
        }
    }

    companion object {

        private const val COLORLESS_TYPE = "Colorless"
        private const val FIGHTING_TYPE = "Fighting"
        private const val PSYCHIC_TYPE = "Psychic"

        private val cubone: Pokemon = Pokemon(
            PokemonCardCollection.CUBONE_DP6, "Cubone", "60", listOf(
                PokeAttack("Headbutt", listOf(COLORLESS_TYPE), "", "10"),
                PokeAttack("Bonemerang", listOf(FIGHTING_TYPE, COLORLESS_TYPE), "Flip 2 coins. This attack does 20 damage times the number of heads.", "20×")
            ),
            null, PokeSet("2008/08/01"), listOf("104"), "Kagemaru Himeno"
        )
        private val hariyamaEx = Pokemon(
            PokemonCardCollection.HARIYAMA_EX8, "Hariyama ex", "110",
            listOf(
                PokeAttack("Knock Off", listOf(FIGHTING_TYPE, COLORLESS_TYPE), "Choose 1 card from your opponent's hand without looking and discard it.", "40"),
                PokeAttack(
                    "Pivot Throw",
                    listOf(FIGHTING_TYPE, FIGHTING_TYPE, COLORLESS_TYPE),
                    "During your opponent's next turn, any damage done to Hariyama ex by attacks is increased by 10 (before applying Weakness and Resistance).",
                    "80"
                )
            ),
            listOf(
                PokeAbility("Commanding Aura", "As long as Hariyama ex is your Active Pokémon, your opponent can't play any Stadium cards from his or her hand.", "Poké-Body")
            ),
            PokeSet("2005/02/01"), listOf("297"), "Ryo Ueda"
        )
        private val alakazam = Pokemon(
            PokemonCardCollection.ALAKAZAM_PL2, "Alakazam E4 LV.X", "100", listOf(
                PokeAttack("Mind Shock", listOf(PSYCHIC_TYPE, PSYCHIC_TYPE, COLORLESS_TYPE), "This attack's damage isn't affected by Weakness or Resistance.", "50"),
            ), listOf(
                PokeAbility(
                    "Damage Switch",
                    "As often as you like during your turn (before your attack), you may move 1 damage counter from 1 of your Pokémon SP to another of your Pokémon SP. This power can't be used if Alakazam E4 is affected by a Special Condition.",
                    "Poké-Power"
                )
            ), PokeSet("2009/05/16"), listOf("65"), "Ryo Ueda"
        )

        private val vespiquen = Pokemon(
            PokemonCardCollection.VESPIQUEN_XY7, "Vespiquen", "90", listOf(
                PokeAttack("Intelligence Gathering", listOf(COLORLESS_TYPE), "You may draw cards until you have 6 cards in your hand.", "10"),
                PokeAttack("Bee Revenge", listOf(COLORLESS_TYPE, COLORLESS_TYPE), "This attack does 10 more damage for each Pokémon in your discard pile.", "20+")
            ), null, PokeSet("2015/08/12"), listOf("416"), "kawayoo"
        )
    }
}