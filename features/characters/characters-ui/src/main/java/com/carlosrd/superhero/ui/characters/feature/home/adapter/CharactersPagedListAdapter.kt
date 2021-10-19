package com.carlosrd.superhero.ui.characters.feature.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import com.carlosrd.superhero.ui.characters.feature.R
import com.carlosrd.superhero.ui.manager.ImageManager
import com.carlosrd.superhero.ui.characters.feature.databinding.CharacterListItemBinding
import com.carlosrd.superhero.ui.extension.gone
import com.carlosrd.superhero.ui.extension.visible

class CharactersPagedListAdapter(private val imageManager: ImageManager,
                                 private val itemClickListener: ((characterId: Long) -> Unit)? = null) :
    PagingDataAdapter<CharacterListItem, CharactersViewHolder>(CHARACTER_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val binding = CharacterListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CharactersViewHolder(binding, imageManager)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.bind(getItem(position), itemClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        // set ViewType
        return if (position == itemCount) CHARACTER_ITEM else LOADING_ITEM
    }

    companion object {
        // Define Loading ViewType
        val LOADING_ITEM = 0

        // Define Movie ViewType
        val CHARACTER_ITEM = 1

        private val CHARACTER_COMPARATOR = object : DiffUtil.ItemCallback<CharacterListItem>() {
            override fun areItemsTheSame(oldItem: CharacterListItem, newItem: CharacterListItem): Boolean {
                // Id is unique.
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CharacterListItem, newItem: CharacterListItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}


class CharactersViewHolder(
    private val binding: CharacterListItemBinding,
    private val imageManager: ImageManager
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: CharacterListItem?,
        itemClickListener: ((characterId: Long) -> Unit)?,
    ) {

        with(binding){
            if (item != null){

                characterName.text = item.name
                characterSubtitleName.text = item.nameSubtitle
                characterSubtitleName.isVisible = item.nameSubtitle.isNotEmpty()

                if (item.imageUrl.contains("image_not_available")){
                    imageManager.load(R.drawable.ic_superhero, characterImage)
                } else {
                    imageManager.loadFrom(item.imageUrl, characterImage, R.drawable.ic_superhero)
                }

                root.setOnClickListener { itemClickListener?.invoke(item.id) }

            } else {
                characterName.text = "???"
                imageManager.load(R.drawable.ic_superhero, binding.characterImage)
            }
        }

    }

}

data class CharacterListItem(val id: Long,
                             val imageUrl: String,
                             val name: String,
                             val nameSubtitle : String = "")
