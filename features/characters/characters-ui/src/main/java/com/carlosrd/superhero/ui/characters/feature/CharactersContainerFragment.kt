package com.carlosrd.superhero.ui.characters.feature

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.carlosrd.superhero.ui.listener.OnBackPressedListener
import com.carlosrd.superhero.ui.characters.di.CharactersComponentProvider
import com.carlosrd.superhero.ui.characters.feature.databinding.FragmentCharactersContainerBinding
import com.carlosrd.superhero.ui.characters.navigation.CharactersInternalNavigator
import com.carlosrd.superhero.ui.di.viewmodel.ViewModelFactory
import com.carlosrd.superhero.ui.extension.viewBinding
import com.carlosrd.superhero.ui.manager.ToolbarManager
import javax.inject.Inject

class CharactersContainerFragment : Fragment(R.layout.fragment_characters_container),
    OnBackPressedListener {

    @Inject internal lateinit var viewModelFactory: ViewModelFactory
    @Inject internal lateinit var internalNavigator: CharactersInternalNavigator
    @Inject internal lateinit var toolbarManager: ToolbarManager

    private val viewModel: CharactersContainerViewModel by viewModels { viewModelFactory }
    private val binding by viewBinding<FragmentCharactersContainerBinding>()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context.applicationContext as CharactersComponentProvider)
            .provideCharactersComponent()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        internalNavigator.goToCharactersList(childFragmentManager)

        with(binding){
            toolbarManager.setToolbar(charactersToolbar,
                charactersAppbarLayout,
                charactersToolbarLayout,
                charactersToolbarImage,
                characterToolbarImageScrimBottom)
        }

        toolbarManager.getAppBarLayout()?.apply {
            setExpanded(false)
        }


    }

    override fun onBackPressed(): Boolean {

        if (childFragmentManager.backStackEntryCount > 1){
            childFragmentManager.popBackStackImmediate()
            return true
        }

        return false

    }

    companion object {
        fun newInstance() = CharactersContainerFragment()
    }

}