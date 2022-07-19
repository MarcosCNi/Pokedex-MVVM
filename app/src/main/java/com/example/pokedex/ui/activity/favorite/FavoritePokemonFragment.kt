package com.example.pokedex.ui.activity.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.databinding.FragmentFavoritePokemonBinding
import com.example.pokedex.ui.activity.base.BaseFragment
import com.example.pokedex.ui.adapters.PokemonAdapter
import com.example.pokedex.ui.state.ResourceState
import com.example.pokedex.utils.hide
import com.example.pokedex.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritePokemonFragment : BaseFragment<FragmentFavoritePokemonBinding, FavoritePokemonViewModel>() {

    override val viewModel: FavoritePokemonViewModel by viewModels()
    private val pokemonAdapter by lazy { PokemonAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        clickAdapter()
        observer()
    }

    private fun observer() = lifecycleScope.launch {
        viewModel.favorites.collect { result->
            when(result){
                is ResourceState.Success -> {
                    result.data?.let {
                        binding.tvEmptyList.hide()
                        pokemonAdapter.pokemons =  it
                    }
                }
                is ResourceState.Empty -> {
                    binding.tvEmptyList.show()
                }
                else -> {}
            }
        }
    }

    private fun clickAdapter() {
        pokemonAdapter.setOnClickListener { pokemonModel ->
            val action = FavoritePokemonFragmentDirections
                .actionFavoritePokemonFragmentToDetailsPokemonFragment(pokemonDetail = pokemonModel)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvFavoritePokemon.apply {
            adapter = pokemonAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoritePokemonBinding = FragmentFavoritePokemonBinding.inflate(inflater, container, false)
}