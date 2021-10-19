package com.carlosrd.superhero.ui.characters.feature.detail.adapter.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.carlosrd.superhero.ui.recyclerview.StickyHeaderItemDecorator

interface CharacterDetailItem {
    override fun equals(other: Any?): Boolean
}

class CharacterDetailDescriptionItem: CharacterDetailItem {

    val description: String?
    @StringRes val descriptionRes : Int

    constructor(description: String){
        this.description = description
        this.descriptionRes = -1
    }

    constructor(@StringRes descriptionRes : Int){
        this.descriptionRes = descriptionRes
        this.description = null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CharacterDetailDescriptionItem

        if (description != other.description) return false
        if (descriptionRes != other.descriptionRes) return false

        return true
    }

}

data class CharacterDetailTitleItem(@DrawableRes val titleIcon: Int,
                                    @StringRes val title: Int): CharacterDetailItem,
    StickyHeaderItemDecorator.StickyHeader

data class CharacterDetailFeatureItem(val name: String,
                                      var separator: Boolean = true): CharacterDetailItem