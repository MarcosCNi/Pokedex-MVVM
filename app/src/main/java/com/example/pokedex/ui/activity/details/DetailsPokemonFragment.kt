package com.example.pokedex.ui.activity.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.data.model.pokemon.PokemonModel
import com.example.pokedex.databinding.FragmentDetailsPokemonBinding
import com.example.pokedex.ui.activity.base.BaseFragment
import com.example.pokedex.ui.adapters.ViewPagerAdapter
import com.example.pokedex.ui.state.ResourceState
import com.example.pokedex.utils.gone
import com.example.pokedex.utils.hide
import com.example.pokedex.utils.show
import com.example.pokedex.utils.toast
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailsPokemonFragment : BaseFragment<FragmentDetailsPokemonBinding, DetailsPokemonViewModel>() {

    private val args: DetailsPokemonFragmentArgs by navArgs()
    override val viewModel: DetailsPokemonViewModel by viewModels()
    private val pokemonInfoPagerAdapter by lazy {ViewPagerAdapter(this)}
    private lateinit var pokemonModel: PokemonModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val number: Int
        if (args.pokemonDetail == null){
            number = args.pokemon!!.url
                .replace("https://pokeapi.co/api/v2/pokemon/", "")
                .replace("/","").toInt()
            setHasOptionsMenu(true)
        }else{
            number = args.pokemonDetail?.id?.toInt()!!
        }

        viewModel.fetch(number)
        collectObserver()
        setupViewPager()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                viewModel.insert(pokemonModel)
                toast(getString(R.string.saved_sucess))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun collectObserver() = lifecycleScope.launch {
        viewModel.pokemon.collect{ result ->
            when(result){
                is ResourceState.Success -> {
                    binding.pgCircularDetails.hide()
                    result.data?.let { values->
                        onLoadedPokemon(values)
                        pokemonInfoPagerAdapter.pokemonModel = values
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

    private fun setupViewPager() = with(binding) {
        vpPokemonDetails.adapter = pokemonInfoPagerAdapter
        TabLayoutMediator(tlPokemonTabDetails, vpPokemonDetails){ tab, position ->
            tab.text = getString(pokemonInfoPagerAdapter.tabs[position])
        }.attach()
    }

    private fun onLoadedPokemon(pokemon: PokemonModel) = with(binding) {
        pokemonModel = pokemon
        tvNamePokemonDetails.text = pokemon.name
        Glide.with(root)
            .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${pokemon.id}.png")
            .into(imgPokemonDetails)
        if (pokemon.types.size > 1){
            cPokemonTypeDetails1.text = pokemon.types[0].type.name
            cPokemonTypeDetails2.text = pokemon.types[1].type.name
        }else{
            cPokemonTypeDetails1.text = pokemon.types[0].type.name
            cPokemonTypeDetails2.gone()
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsPokemonBinding = FragmentDetailsPokemonBinding.inflate(inflater, container, false)
}