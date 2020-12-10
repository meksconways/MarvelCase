package com.meksconway.marvelcase.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.meksconway.marvelcase.data.model.CharactersResponseResults
import com.meksconway.marvelcase.databinding.ItemCharacterBinding
import com.meksconway.marvelcase.util.loadImageResource
import com.meksconway.marvelcase.util.loadImageUrl
import com.meksconway.marvelcase.util.viewBinding

class CharactersAdapter(private val callback: (CharactersResponseResults?) -> Unit) :
    PagingDataAdapter<CharactersResponseResults,
            CharactersAdapter.CharactersVH>(CharactersDiffUtil()) {

    class CharactersVH(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: CharactersResponseResults?) {
            binding.imgCharacter.load(model?.thumbnail?.getSmallImage())
            binding.txtCharacterName.text = model?.name
        }

    }

    override fun onBindViewHolder(holder: CharactersVH, position: Int) {
        holder.bind(getItem(position))
        holder.binding.root.setOnClickListener {
            callback.invoke(getItem(position))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersVH {
        return CharactersVH(parent.viewBinding(ItemCharacterBinding::inflate))
    }


}

class CharactersDiffUtil : DiffUtil.ItemCallback<CharactersResponseResults>() {
    override fun areItemsTheSame(
        oldItem: CharactersResponseResults,
        newItem: CharactersResponseResults
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharactersResponseResults,
        newItem: CharactersResponseResults
    ): Boolean {
        return oldItem == newItem
    }
}