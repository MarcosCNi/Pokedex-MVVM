package com.example.pokedex.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pokedex.R
import com.example.pokedex.data.model.pokemon.PokemonModel
import com.example.pokedex.ui.activity.list.ListPokemonFragment

class ViewPagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    val tabs = arrayOf(R.string.stats, R.string.moves, R.string.evolutions)
    val fragments = arrayOf(ListPokemonFragment(), ListPokemonFragment(), ListPokemonFragment())
    lateinit var pokemonModel: PokemonModel

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}