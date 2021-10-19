package com.carlosrd.superhero.ui.characters.feature.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.carlosrd.superhero.ui.characters.feature.R
import com.carlosrd.superhero.ui.characters.feature.databinding.CharacterListLoadingItemBinding

class CharactersPagedListLoadStateAdapter (
    private val retry: () -> Unit) : LoadStateAdapter<CharactersLoadStateViewHolder>() {
    override fun onBindViewHolder(holder: CharactersLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): CharactersLoadStateViewHolder {
        return CharactersLoadStateViewHolder.create(parent, retry)
    }

}

class CharactersLoadStateViewHolder(
    private val binding: CharacterListLoadingItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {

        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error

    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): CharactersLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.character_list_loading_item, parent, false)
            val binding = CharacterListLoadingItemBinding.bind(view)
            return CharactersLoadStateViewHolder(binding, retry)
        }
    }
}
