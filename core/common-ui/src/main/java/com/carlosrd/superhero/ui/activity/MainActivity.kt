package com.carlosrd.superhero.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.carlosrd.superhero.ui.R
import com.carlosrd.superhero.ui.di.component.ActivityComponentProvider
import com.carlosrd.superhero.ui.listener.OnBackPressedListener
import com.carlosrd.superhero.ui.navigation.AppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var appNavigator : AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {

        (applicationContext as ActivityComponentProvider)
            .provideActivityComponent(this)
            .inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load initial container
        appNavigator.goToCharacters()

    }

    override fun onBackPressed() {

        supportFragmentManager.fragments.forEach {

            if (it is OnBackPressedListener && it.onBackPressed()) {
                // Child Fragments manages back pressed
                return
            }

        }

        if (supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack()
            return
        }

        super.onBackPressed()
    }
}