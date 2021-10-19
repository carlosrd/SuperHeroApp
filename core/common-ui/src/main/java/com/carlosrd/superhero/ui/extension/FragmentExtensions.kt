package com.carlosrd.superhero.ui.extension

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.carlosrd.superhero.ui.viewbinding.FragmentViewBindingDelegate
import kotlinx.coroutines.launch

// ViewBinding Delegate Extension
inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    FragmentViewBindingDelegate(this, T::class.java)


fun Fragment.showToast(message: String, length : Int = Toast.LENGTH_LONG){
    Toast.makeText(context, message, length).show()
}


