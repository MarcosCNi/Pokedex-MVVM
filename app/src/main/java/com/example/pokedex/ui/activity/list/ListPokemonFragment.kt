package com.example.pokedex.ui.activity.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentListPokemonBinding
import com.example.pokedex.ui.activity.base.BaseFragment
import com.example.pokedex.ui.adapters.PokemonResultAdapter
import com.example.pokedex.ui.state.ResourceState
import com.example.pokedex.utils.hide
import com.example.pokedex.utils.show
import com.example.pokedex.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ListPokemonFragment : BaseFragment<FragmentListPokemonBinding, ListPokemonViewModel>() {

    override val viewModel: ListPokemonViewModel by viewModels()
    private val pokemonResultAdapter by lazy { PokemonResultAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        clickAdapter()
        getObserver()
    }

    private fun getObserver() = lifecycleScope.launch{
        viewModel.list.collect{ resource ->
            when(resource){
                is ResourceState.Success -> {
                    resource.data?.let { values ->
                        binding.pgCircular.hide()
                        pokemonResultAdapter.pokemons = values.results.toList()
                    }
                }
                is ResourceState.Error -> {
                    binding.pgCircular.hide()
                    resource.message?.let{ message ->
                        toast(getString(R.string.an_error_occurred))
                        Timber.tag("ListPokemonFragment").e("Error -> $message")
                    }
                }
                is ResourceState.Load -> {
                    binding.pgCircular.show()
                }
                else -> {}
            }
        }
    }

    private fun clickAdapter() {
        pokemonResultAdapter.setOnClickListener { pokemonResult ->
            val action = ListPokemonFragmentDirections
                .actionListPokemonFragmentToDetailsPokemonFragment(pokemonResult)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvPokemon.apply {
            adapter = pokemonResultAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListPokemonBinding = FragmentListPokemonBinding.inflate(inflater, container, false)
}