package com.example.pokedex.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.data.model.pokemon.PokemonModel
import com.example.pokedex.data.model.pokemon.PokemonResult
import com.example.pokedex.databinding.ItemPokemonBinding
import java.util.*

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root) {

        fun binding(pokemon: PokemonModel) {
            val name = binding.tvNamePokemon
            val num = binding.tvNumPokemon
            pokemon.let{
                name.text = pokemon.name.replaceFirstChar { name ->
                    if (name.isLowerCase()) name.titlecase(
                        Locale.getDefault()
                    ) else name.toString()
                }
                num.text = "Nº ${it.id}"
                Glide.with(binding.itemCard)
                    .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/${it.id}.png")
                    .into(binding.imgPokemon)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<PokemonModel>() {

        override fun areItemsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
            return oldItem.id == newItem.id && oldItem.height == newItem.height && oldItem.moves == newItem.moves
                    && oldItem.name == newItem.name && oldItem.species == newItem.species && oldItem.stats == newItem.stats
                    && oldItem.types == newItem.types && oldItem.weight == newItem.weight
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var pokemons: List<PokemonModel>
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

    private var onItemClickListener: ((PokemonModel) -> Unit)? = null

    fun setOnClickListener(listener: (PokemonModel) -> Unit){
        onItemClickListener = listener
    }
}






















