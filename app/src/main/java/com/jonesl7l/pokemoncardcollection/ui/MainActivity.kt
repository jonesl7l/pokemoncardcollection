package com.jonesl7l.pokemoncardcollection.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.airbnb.lottie.LottieDrawable
import com.jonesl7l.pokemoncardcollection.R
import com.jonesl7l.pokemoncardcollection.databinding.ActivityMainBinding
import com.jonesl7l.pokemoncardcollection.model.PokeData
import com.jonesl7l.pokemoncardcollection.model.Result
import com.jonesl7l.pokemoncardcollection.ui.adapter.PokeAdapter
import com.jonesl7l.pokemoncardcollection.utils.getLottieJson
import com.jonesl7l.pokemoncardcollection.utils.gone
import com.jonesl7l.pokemoncardcollection.utils.show
import com.jonesl7l.pokemoncardcollection.viewmodel.PokemonCardViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Annotated the activity with @AndroidEntryPoint which means that hilt should provide all the dependencies to this fragment that it asks for.
 * One important point to be noted:-If you annotate an Android class with @AndroidEntryPoint, then you also must annotate Android classes that depend on it.
 * For example, if you annotate a fragment, then you must also annotate any activities where you use that fragment.
 *
 * 'by viewModels' gets a reference to the viewmodel scoped
 * The viewmodel has to be observed to get data updates
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val pokeData: PokeData by lazy { PokeData(mutableListOf()) }
    private lateinit var pokeAdapter: PokeAdapter

    private val viewModel: PokemonCardViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    //region Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        observeLiveData()
    }

    //endregion

    //region Init

    private fun init() {
        pokeAdapter = PokeAdapter(pokeData)

        binding.mainLottie.apply {
            getLottieJson(LOADING_LOTTIE_REF)?.let { setAnimationFromJson(it, null) }
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
        binding.mainRecyclerview.apply {
            LinearSnapHelper().attachToRecyclerView(this)
            adapter = pokeAdapter
        }
        binding.mainSwipeLayout.setOnRefreshListener { viewModel.getPokemonCards() }
    }

    private fun observeLiveData() {
        viewModel.getPokeData().observe(this, Observer { pokeData ->
            when (pokeData) {
                is Result.Success -> onShowList(pokeData.data)
                is Result.Loading -> onLoading()
                is Result.Error -> onError(pokeData.message)
            }
        })
    }

    //endregion

    //region Ui Changes

    private fun onLoading() {
        binding.mainLoadingLayout.show()
        binding.mainErrorText.gone()
        binding.mainSwipeLayout.isRefreshing = false
    }

    private fun onShowList(pokeData: PokeData?) {
        if (pokeData?.data.isNullOrEmpty()) {
            onError()
            return
        }
        pokeData?.let { pokeAdapter.updateDataSet(it) }
        binding.mainRecyclerview.show()
        binding.mainSwipeLayout.show()
        binding.mainErrorText.gone()
        binding.mainLoadingLayout.gone()
        binding.mainSwipeLayout.isRefreshing = false
    }

    private fun onError(error: String? = null) {
        val errorString = error ?: getString(R.string.generic_error)
        binding.mainSwipeLayout.show()
        binding.mainErrorText.apply {
            show()
            text = errorString
        }
        binding.mainRecyclerview.gone()
        binding.mainLoadingLayout.gone()
        binding.mainSwipeLayout.isRefreshing = false
    }

    //endregion

    companion object {
        private const val LOADING_LOTTIE_REF = "loading"
    }
}