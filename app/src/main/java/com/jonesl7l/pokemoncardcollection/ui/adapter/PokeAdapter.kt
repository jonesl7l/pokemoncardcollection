package com.jonesl7l.pokemoncardcollection.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jonesl7l.pokemoncardcollection.R
import com.jonesl7l.pokemoncardcollection.databinding.ItemCardBinding
import com.jonesl7l.pokemoncardcollection.model.PokeData
import com.jonesl7l.pokemoncardcollection.model.Pokemon
import com.jonesl7l.pokemoncardcollection.utils.*
import timber.log.Timber
import java.util.*

class PokeAdapter(private val pokeData: PokeData) :
    RecyclerView.Adapter<PokeAdapter.PokemonCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonCardViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokemonCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PokemonCardViewHolder, position: Int) {
        holder.bind(pokeData.data[position])
    }

    override fun getItemCount(): Int = pokeData.data.size.orZero()

    fun updateDataSet(newPokemonCards: PokeData) {
        //TODO sorting list by release date

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                newPokemonCards.data[newItemPosition] == pokeData.data[oldItemPosition]

            override fun getOldListSize(): Int = pokeData.data.size

            override fun getNewListSize(): Int = newPokemonCards.data.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                newPokemonCards.data[newItemPosition].id == pokeData.data[oldItemPosition].id
        })

        diffResult.dispatchUpdatesTo(this)
        pokeData.data.clear()
        pokeData.data.addAll(newPokemonCards.data)
    }

    inner class PokemonCardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Pokemon) {
            with(binding) {
                try {
                    val imageRef = getResId(appContext(), "pokemon_image_${item.id.replace("-", "_")}", "drawable")
                    cardImage.setImageDrawable(ContextCompat.getDrawable(appContext(), imageRef))
                } catch (exception: Exception) {
                    Timber.e(exception)
                    //TODO add placeholder
                }
                cardName.text = item.name
                initAttackOne(item)
                initAttackTwo(item)
                cardHitpointValueText.text = item.hp
                cardNumber.text = appContext().getString(R.string.card_number_placeholder).replace("{NUMBER}", item.nationalPokedexNumbers.firstOrNull().orNotSet())
                cardArtist.text = item.artist.orEmpty()
            }
        }

        private fun initAttackOne(item: Pokemon) {
            val name: String?
            val description: String?
            var damage = ""
            var types: List<String> = listOf()
            //Poke ability with 'poke power' takes precedence over Poke attack
            if (!item.abilities.isNullOrEmpty() && item.abilities.find { it.type == PokemonCardConsts.POKE_POWER_TYPE } != null) {
                val ability = item.abilities.find { it.type == PokemonCardConsts.POKE_POWER_TYPE }
                name = ability?.name
                binding.partialAttackOne.partialCardAttackName.setTextColor(ContextCompat.getColor(appContext(), R.color.red_text))
                description = ability?.text
            } else {
                binding.partialAttackOne.partialCardAttackName.setTextColor(ContextCompat.getColor(appContext(), R.color.black_text))
                val attack = item.attacks.firstOrNull()
                name = attack?.name
                description = attack?.text
                damage = attack?.damage.orEmpty()
                types = attack?.cost.orEmpty()
            }
            with(binding.partialAttackOne) {
                partialCardAttackName.text = name.orNotSet()
                partialCardAttackDescription.text = description.orNotSet()
                partialCardAttackDamage.text = damage
                setTypeImage(types, 0, partialCardTypeOne)
                if (types.size > 1) {
                    partialCardTypeTwo.show()
                    setTypeImage(types, 1, partialCardTypeTwo)
                } else {
                    partialCardTypeTwo.gone()
                }
                if (types.size > 2) {
                    partialCardTypeThree.show()
                    setTypeImage(types, 2, partialCardTypeThree)
                } else {
                    partialCardTypeThree.gone()
                }
            }
        }

        private fun initAttackTwo(item: Pokemon) {
            var name: String? = null
            var description: String? = null
            var damage = ""
            var types: List<String> = listOf()
            if (!item.abilities.isNullOrEmpty() && item.abilities.find { it.type == PokemonCardConsts.POKE_POWER_TYPE } != null) {
                val attack = item.attacks.firstOrNull()
                name = attack?.name
                description = attack?.text
                damage = attack?.damage.orEmpty()
                types = attack?.cost.orEmpty()
            } else if (item.attacks.size > 1) {
                val attack = item.attacks.getOrNull(1)
                name = attack?.name
                description = attack?.text
                damage = attack?.damage.orEmpty()
                types = attack?.cost.orEmpty()
            }
            with(binding.partialAttackTwo) {
                partialCardAttackName.text = name.orNotSet()
                partialCardAttackDescription.text = description.orNotSet()
                partialCardAttackDamage.text = damage
                setTypeImage(types, 0, partialCardTypeOne)
                if (types.size > 1) {
                    partialCardTypeTwo.show()
                    setTypeImage(types, 1, partialCardTypeTwo)
                } else {
                    partialCardTypeTwo.gone()
                }
                if (types.size > 2) {
                    partialCardTypeThree.show()
                    setTypeImage(types, 2, partialCardTypeThree)
                } else {
                    partialCardTypeThree.gone()
                }
            }
        }

        private fun setTypeImage(types: List<String>, index: Int, imageView: ImageView) {
            try {
                val imageRef = getResId(appContext(), "type_${types.getOrNull(index)?.toLowerCase(
                    Locale.getDefault())}", "drawable")
                imageView.setImageDrawable(ContextCompat.getDrawable(appContext(), imageRef))
            } catch (exception: Exception) {
                Timber.e(exception)
                imageView.setImageDrawable(ContextCompat.getDrawable(appContext(), R.drawable.type_colorless))
            }
        }
    }
}