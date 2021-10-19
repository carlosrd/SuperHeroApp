package com.carlosrd.superhero.ui.characters.feature.detail.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.carlosrd.superhero.ui.characters.feature.R
import com.carlosrd.superhero.ui.characters.feature.databinding.CharacterListDetailFeatureItemBinding
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.model.CharacterDetailFeatureItem
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.model.CharacterDetailItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class CharacterDetailFeatureDelegate : AdapterDelegate<List<CharacterDetailItem>>() {

    override fun isForViewType(items: List<CharacterDetailItem>, position: Int): Boolean {
        return items[position] is CharacterDetailFeatureItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val itemBinding = CharacterListDetailFeatureItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterDetailFeatureViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        items: List<CharacterDetailItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position] as? CharacterDetailFeatureItem ?: return
        if (holder !is CharacterDetailFeatureViewHolder) return

        holder.bind(item)
    }
}

class CharacterDetailFeatureViewHolder(
    private val binding : CharacterListDetailFeatureItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CharacterDetailFeatureItem){

        with(binding){

            characterFeature.text = item.name
            characterFeatureSeparator.isVisible = item.separator

            if (item.separator){
                root.setBackgroundColor(ContextCompat.getColor(root.context, R.color.character_detail_feature_bg))
            } else{
                root.setBackgroundResource(R.drawable.character_detail_feature_background)
            }

        }


    }
}