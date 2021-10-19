package com.carlosrd.superhero.ui.characters.feature.detail.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carlosrd.superhero.ui.characters.feature.R
import com.carlosrd.superhero.ui.characters.feature.databinding.CharacterListDetailDescriptionItemBinding
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.model.CharacterDetailDescriptionItem
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.model.CharacterDetailItem
import com.carlosrd.superhero.ui.manager.StringManager
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class CharacterDetailDescriptionDelegate(private val stringManager: StringManager) : AdapterDelegate<List<CharacterDetailItem>>() {

    override fun isForViewType(items: List<CharacterDetailItem>, position: Int): Boolean {
        return items[position] is CharacterDetailDescriptionItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val itemBinding = CharacterListDetailDescriptionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterDetailDescriptionViewHolder(itemBinding, stringManager)
    }

    override fun onBindViewHolder(
        items: List<CharacterDetailItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position] as? CharacterDetailDescriptionItem ?: return
        if (holder !is CharacterDetailDescriptionViewHolder) return

        holder.bind(item)
    }
}

class CharacterDetailDescriptionViewHolder(
    private val binding : CharacterListDetailDescriptionItemBinding,
    private val stringManager: StringManager
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CharacterDetailDescriptionItem){

        with(binding){

            if (item.descriptionRes > 0){
                characterDescription.text = stringManager.getString(item.descriptionRes)
            } else {
                characterDescription.text = item.description
            }

            root.setBackgroundResource(R.drawable.character_detail_feature_background)

        }

    }

}