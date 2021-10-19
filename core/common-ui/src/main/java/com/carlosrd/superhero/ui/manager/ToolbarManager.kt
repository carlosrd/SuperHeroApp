package com.carlosrd.superhero.ui.manager

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToolbarManager @Inject constructor() {

    private var toolbarRef = WeakReference<Toolbar>(null)
    private var appBarLayoutRef = WeakReference<AppBarLayout>(null)
    private var collapsingToolbarLayoutRef = WeakReference<CollapsingToolbarLayout>(null)
    private var toolbarImageRef = WeakReference<AppCompatImageView>(null)
    private var toolbarImageBottomScrimRef = WeakReference<View>(null)

    fun setToolbar(toolbar: Toolbar,
                   appBarLayout: AppBarLayout? = null,
                   collapsingToolbarLayout: CollapsingToolbarLayout? = null,
                   toolbarImage: AppCompatImageView? = null,
                   toolbarImageBottomScrim: View? = null) {

        toolbarRef = WeakReference(toolbar)
        appBarLayoutRef = WeakReference(appBarLayout)
        collapsingToolbarLayoutRef = WeakReference(collapsingToolbarLayout)
        toolbarImageRef = WeakReference(toolbarImage)
        toolbarImageBottomScrimRef = WeakReference(toolbarImageBottomScrim)

    }

    fun getToolbar() : Toolbar? = toolbarRef.get()

    fun getAppBarLayout() : AppBarLayout? = appBarLayoutRef.get()

    fun getCollapsingToolbarLayout() : CollapsingToolbarLayout? = collapsingToolbarLayoutRef.get()

    fun getToolbarImage() : AppCompatImageView? = toolbarImageRef.get()

    fun getToolbarImageBottomScrim(): View? = toolbarImageBottomScrimRef.get()

}