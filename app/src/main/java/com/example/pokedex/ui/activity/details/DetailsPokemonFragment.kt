package com.example.pokedex.ui.activity.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokedex.data.model.pokemon.PokemonModel
import com.example.pokedex.databinding.FragmentDetailsPokemonBinding
import com.example.pokedex.ui.activity.base.BaseFragment
import com.example.pokedex.ui.state.ResourceState
import com.example.pokedex.utils.gone
import com.example.pokedex.utils.hide
import com.example.pokedex.utils.show
import com.example.pokedex.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailsPokemonFragment : BaseFragment<FragmentDetailsPokemonBinding, DetailsPokemonViewModel>() {

    private val args: DetailsPokemonFragmentArgs by navArgs()
    override val viewModel: DetailsPokemonViewModel by viewModels()
    private lateinit var pokemonModel: PokemonModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val number = args.pokemon.url
            .replace("https://pokeapi.co/api/v2/pokemon/", "")
            .replace("/","").toInt()
        viewModel.fetch(number)
        collectObserver()

    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.pokemon.collect{ result ->
            when(result){
                is ResourceState.Success -> {
                    binding.pgCircularDetails.hide()
                    result.data?.let { values->
                        onLoadedPokemon(values)
                    }
                }
                is ResourceState.Error -> {
                    binding.pgCircularDetails.hide()
                    result.message?.let { message ->
                        Timber.tag("DetailsPokemon").e("Error -> $message")
                        toast(message)
                    }
                }
                is ResourceState.Load -> {
                    binding.pgCircularDetails.show()
                }
                else -> {}
            }
        }
    }

    private suspend fun onLoadedPokemon(pokemonModel: PokemonModel) = with(binding) {
        tvNamePokemonDetails.text = pokemonModel.name
        Glide.with(root)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${pokemonModel.id}.png")
            .into(imgPokemonDetails)
        if (pokemonModel.types!!.size > 1){
            cPokemonTypeDetails1.text = pokemonModel.types[0].pokemonType.name
            cPokemonTypeDetails2.text = pokemonModel.types[1].pokemonType.name
        }else{
            cPokemonTypeDetails1.text = pokemonModel.types[0].pokemonType.name
            cPokemonTypeDetails2.gone()
        }


    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsPokemonBinding = FragmentDetailsPokemonBinding.inflate(inflater, container, false)
}