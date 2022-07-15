package com.example.pokedex.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.data.model.pokemon.PokemonModel
import com.example.pokedex.data.model.pokemon.PokemonResult
import com.example.pokedex.databinding.ItemPokemonBinding
import com.example.pokedex.utils.gone
import com.example.pokedex.utils.show
import java.util.*

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binding(pokemon: PokemonResult) {
            val name = binding.tvNamePokemon
            val num = binding.tvNumPokemon
            pokemon.let{
                name.text = pokemon.name.replaceFirstChar { name ->
                    if (name.isLowerCase()) name.titlecase(
                        Locale.getDefault()
                    ) else name.toString()
                }
                val number = it.url
                    .replace("https://pokeapi.co/api/v2/pokemon/", "")
                    .replace("/","").toInt()
                num.text = "Nº $number"
                Glide.with(binding.itemCard)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/$number.png")
                    .into(binding.imgPokemon)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<PokemonResult>() {

        override fun areItemsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
            return oldItem.url == newItem.url
                    && oldItem.name == newItem.name
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var pokemons: List<PokemonResult>
    get() = differ.currentList
    set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            ItemPokemonBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = pokemons.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemons[position]
        holder.binding(pokemon)
        holder.itemView.setOnClickListener{
            onItemClickListener?.let {
                it(pokemon)
            }
        }
    }

    private var onItemClickListener: ((PokemonResult) -> Unit)? = null

    fun setOnClickListener(listener: (PokemonResult) -> Unit){
        onItemClickListener = listener
    }
}






















