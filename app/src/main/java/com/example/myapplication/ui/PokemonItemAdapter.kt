package com.example.myapplication.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.PokemonItemUI
import com.example.myapplication.databinding.ItemPokemonBinding
import com.example.myapplication.util.load

class PokemonItemAdapter(private val clickListener: (item: PokemonItemUI, isLoadMoreButton: Boolean) -> Unit) :
    ListAdapter<PokemonItemUI, PokemonItemAdapter.ItemViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_pokemon, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPokemonBinding.bind(itemView)
        fun bind(
            item: PokemonItemUI,
            clickListener: (item: PokemonItemUI, isLoadMoreButton: Boolean) -> Unit
        ) = with(binding) {
            layoutMain.isVisible = !item.isLoadMoreButton
            btLoadMore.isVisible = item.isLoadMoreButton

            tvName.text = item.name
            tvTypes.text = item.typeString
            item.icon?.let { ivIcon.load(it) }
            btLoadMore.setOnClickListener { clickListener(item, true) }
            itemView.setOnClickListener { clickListener(item, false) }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<PokemonItemUI>() {
    override fun areItemsTheSame(oldItem: PokemonItemUI, newItem: PokemonItemUI): Boolean = oldItem.url == newItem.url
    override fun areContentsTheSame(oldItem: PokemonItemUI, newItem: PokemonItemUI): Boolean = oldItem == newItem
}