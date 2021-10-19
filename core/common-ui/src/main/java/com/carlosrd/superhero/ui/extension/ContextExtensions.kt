package com.carlosrd.superhero.ui.extension

import android.content.Context

fun Context.dp2px(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale).toInt()
}