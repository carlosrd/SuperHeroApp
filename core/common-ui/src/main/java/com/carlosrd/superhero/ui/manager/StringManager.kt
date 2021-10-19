package com.carlosrd.superhero.ui.manager

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringManager @Inject constructor(private val context: Context) {

    fun getString(@StringRes stringRes: Int): String {
        return context.getString(stringRes)
    }
}