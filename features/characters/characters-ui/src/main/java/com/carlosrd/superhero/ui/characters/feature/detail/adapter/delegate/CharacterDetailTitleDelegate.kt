package com.carlosrd.superhero.ui.characters.feature.detail.adapter.delegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.carlosrd.superhero.ui.characters.feature.databinding.CharacterListDetailTitleItemBinding
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.model.CharacterDetailItem
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.model.CharacterDetailTitleItem
import com.carlosrd.superhero.ui.manager.StringManager
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class CharacterDetailTitleDelegate(private val stringManager: StringManager) : AdapterDelegate<List<CharacterDetailItem>>() {

    override fun isForViewType(items: List<CharacterDetailItem>, position: Int): Boolean {
        return items[position] is CharacterDetailTitleItem
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val itemBinding = CharacterListDetailTitleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterDetailTitleViewHolder(itemBinding, stringManager)
    }

    override fun onBindViewHolder(
        items: List<CharacterDetailItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        val item = items[position] as? CharacterDetailTitleItem ?: return
        if (holder !is CharacterDetailTitleViewHolder) return

        holder.bind(item)
    }
}

class CharacterDetailTitleViewHolder(
    private val binding : CharacterListDetailTitleItemBinding,
    private val stringManager: StringManager,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CharacterDetailTitleItem){

        binding.characterTitleImage.setImageResource(item.titleIcon)
        binding.characterTitle.text = stringManager.getString(item.title)

    }
}