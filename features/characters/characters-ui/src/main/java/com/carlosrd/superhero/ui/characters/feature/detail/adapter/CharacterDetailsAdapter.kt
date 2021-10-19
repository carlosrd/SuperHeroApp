package com.carlosrd.superhero.ui.characters.feature.detail.adapter

import androidx.recyclerview.widget.DiffUtil
import com.carlosrd.superhero.domain.characters.model.CharacterModel
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.delegate.CharacterDetailDescriptionDelegate
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.delegate.CharacterDetailFeatureDelegate
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.delegate.CharacterDetailTitleDelegate
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.model.CharacterDetailItem
import com.carlosrd.superhero.ui.manager.StringManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class CharacterDetailsAdapter(stringManager: StringManager) :
    AsyncListDifferDelegationAdapter<CharacterDetailItem>(CHARACTER_DETAIL_COMPARATOR) {

    init {

        delegatesManager.addDelegate(CharacterDetailDescriptionDelegate(stringManager))
        delegatesManager.addDelegate(CharacterDetailTitleDelegate(stringManager))
        delegatesManager.addDelegate(CharacterDetailFeatureDelegate())

    }


    companion object {

        private val CHARACTER_DETAIL_COMPARATOR =
            object : DiffUtil.ItemCallback<CharacterDetailItem>() {
                override fun areItemsTheSame(
                    oldItem: CharacterDetailItem,
                    newItem: CharacterDetailItem
                ): Boolean {
                    // Id is unique.
                    return oldItem === newItem
                }

                override fun areContentsTheSame(
                    oldItem: CharacterDetailItem,
                    newItem: CharacterDetailItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}