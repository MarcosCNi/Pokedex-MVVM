package com.example.pokedex.ui.activity.search

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentSearchPokemonBinding
import com.example.pokedex.ui.activity.base.BaseFragment
import com.example.pokedex.ui.adapters.PokemonAdapter
import com.example.pokedex.ui.state.ResourceState
import com.example.pokedex.utils.Constants.DEFAULT_QUERY
import com.example.pokedex.utils.Constants.LAST_SEARCH_QUERY
import com.example.pokedex.utils.hide
import com.example.pokedex.utils.show
import com.example.pokedex.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchPokemonFragment : BaseFragment<FragmentSearchPokemonBinding, SearchPokemonViewModel>() {

    override val viewModel: SearchPokemonViewModel by viewModels()
    private val pokemonAdapter by lazy { PokemonAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        clickAdapter()

        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        searchInit(query)
        collectObserver()
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.searchPokemon.collect { result ->
            when(result){
                is ResourceState.Success -> {
                    binding.pgCircularSearch.hide()
                    result.data.let {
                        pokemonAdapter.pokemons = it!!.results.toList()
                    }
                }
                is ResourceState.Error -> {
                    binding.pgCircularSearch.hide()
                    result.message?.let { message ->
                        Timber.tag("SearchpokemonFragment").e("Error -> $message")
                        toast(getString(R.string.an_error_occurred))
                    }
                }
                is ResourceState.Load -> {
                    binding.pgCircularSearch.show()
                }
                else -> {}
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(LAST_SEARCH_QUERY, binding.edSearchPokemon.editableText.trim().toString())
    }

    private fun searchInit(query: String) = with(binding) {
        edSearchPokemon.setText(query)
        edSearchPokemon.setOnEditorActionListener{ _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO){
                updatePokemonList()
                true
            }else{
                false
            }
        }

        edSearchPokemon.setOnKeyListener{_, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                updatePokemonList()
                true
            }else{
                false
            }
        }
    }

    private fun updatePokemonList() = with(binding) {
        edSearchPokemon.editableText.trim().let {
            if(it.isNotEmpty()){
                searchQuery(it.toString())
            }
        }
    }

    private fun searchQuery(query: String) {
        viewModel.fetch(query)
    }

    private fun clickAdapter() {
        pokemonAdapter.setOnClickListener { pokemonResult ->
            val action = SearchPokemonFragmentDirections
                .actionSearchPokemonFragmentToDetailsPokemonFragment(pokemonResult)
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() = with(binding) {
        rvSearchPokemon.apply {
            adapter = pokemonAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchPokemonBinding = FragmentSearchPokemonBinding.inflate(inflater, container, false)
}