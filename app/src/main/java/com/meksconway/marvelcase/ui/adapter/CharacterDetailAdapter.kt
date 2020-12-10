package com.meksconway.marvelcase.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.meksconway.marvelcase.data.repository.CharacterDetailItemType.*
import com.meksconway.marvelcase.data.repository.CharacterDetailUiModel
import com.meksconway.marvelcase.databinding.ItemChDetailComicBinding
import com.meksconway.marvelcase.databinding.ItemChDetailDescriptionBinding
import com.meksconway.marvelcase.databinding.ItemChDetailHeaderBinding
import com.meksconway.marvelcase.databinding.ItemChDetailImageBinding
import com.meksconway.marvelcase.util.viewBinding

class CharacterDetailAdapter : ListAdapter<CharacterDetailUiModel,
        RecyclerView.ViewHolder>(CharacterDetailDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            IMAGE_HEADER.ordinal -> {
                ImageHeaderVH(parent.viewBinding(ItemChDetailImageBinding::inflate))
            }
            TITLE_HEADER.ordinal -> {
                TitleHeaderVH(parent.viewBinding(ItemChDetailHeaderBinding::inflate))
            }
            DESC.ordinal -> {
                DescVH(parent.viewBinding(ItemChDetailDescriptionBinding::inflate))
            }
            COMIC.ordinal -> {
                ComicVH(parent.viewBinding(ItemChDetailComicBinding::inflate))
            }
            else -> throw IllegalStateException("")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = getItem(position)
        when (model.type) {
            IMAGE_HEADER -> (holder as? ImageHeaderVH)
                ?.bind(model as? CharacterDetailUiModel.ImageHeaderItem)
            TITLE_HEADER -> (holder as? TitleHeaderVH)
                ?.bind(model as? CharacterDetailUiModel.TitleHeaderItem)
            DESC -> (holder as? DescVH)
                ?.bind(model as? CharacterDetailUiModel.DescriptionItem)
            COMIC -> (holder as? ComicVH)
                ?.bind(model as? CharacterDetailUiModel.ComicItem)
        }
    }


}

class ImageHeaderVH(private val binding: ItemChDetailImageBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: CharacterDetailUiModel.ImageHeaderItem?) {
        binding.imgCharacter.load(model?.imageUrl)
        binding.txtCharacterName.text = model?.name
    }

}

class ComicVH(private val binding: ItemChDetailComicBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: CharacterDetailUiModel.ComicItem?) {
        binding.txtComic.text = model?.comicName
    }

}

class DescVH(private val binding: ItemChDetailDescriptionBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: CharacterDetailUiModel.DescriptionItem?) {
        binding.txtDesc.text = model?.desc
    }

}


class TitleHeaderVH(private val binding: ItemChDetailHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: CharacterDetailUiModel.TitleHeaderItem?) {
        binding.txtHeader.text = model?.title
    }

}


class CharacterDetailDiffUtil : DiffUtil.ItemCallback<CharacterDetailUiModel>() {

    override fun areItemsTheSame(
        oldItem: CharacterDetailUiModel,
        newItem: CharacterDetailUiModel
    ): Boolean {
        return oldItem is CharacterDetailUiModel.ImageHeaderItem
                && newItem is CharacterDetailUiModel.ImageHeaderItem
                && oldItem.name == newItem.name
                || oldItem is CharacterDetailUiModel.TitleHeaderItem
                && newItem is CharacterDetailUiModel.TitleHeaderItem
                && oldItem.title == newItem.title
                || oldItem is CharacterDetailUiModel.DescriptionItem
                && newItem is CharacterDetailUiModel.DescriptionItem
                && oldItem.desc == newItem.desc
                || oldItem is CharacterDetailUiModel.ComicItem
                && newItem is CharacterDetailUiModel.ComicItem
                && oldItem.comicName == newItem.comicName
    }

    override fun areContentsTheSame(
        oldItem: CharacterDetailUiModel,
        newItem: CharacterDetailUiModel
    ): Boolean {
        return oldItem == newItem
    }
}