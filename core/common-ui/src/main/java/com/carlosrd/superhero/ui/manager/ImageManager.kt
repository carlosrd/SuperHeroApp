package com.carlosrd.superhero.ui.manager

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.carlosrd.superhero.ui.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageManager @Inject constructor() {

    fun loadFrom(url: String, imageView: ImageView,
        @DrawableRes placeholder : Int) {

        Glide.with(imageView)
            .load(url)
            .placeholder(placeholder)
            .into(imageView)

    }

    fun load(@DrawableRes drawable: Int, imageView: ImageView) {

        Glide.with(imageView)
            .load(drawable)
            .into(imageView)

    }
}