package com.example.pokedex.ui.activity.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pokedex.databinding.FragmentFavoritePokemonBinding
import com.example.pokedex.ui.activity.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritePokemonFragment : BaseFragment<FragmentFavoritePokemonBinding, FavoritePokemonViewModel>() {
    override val viewModel: FavoritePokemonViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoritePokemonBinding = FragmentFavoritePokemonBinding.inflate(inflater, container, false)
}