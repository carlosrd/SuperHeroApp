package com.carlosrd.superhero.ui.characters.feature.detail

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlosrd.superhero.ui.characters.di.CharactersComponentProvider
import com.carlosrd.superhero.ui.characters.feature.R
import com.carlosrd.superhero.ui.characters.feature.databinding.FragmentCharacterDetailBinding
import com.carlosrd.superhero.ui.characters.feature.detail.adapter.CharacterDetailsAdapter
import com.carlosrd.superhero.ui.recyclerview.StickyHeaderItemDecorator
import com.carlosrd.superhero.ui.di.viewmodel.ViewModelFactory
import com.carlosrd.superhero.ui.extension.gone
import com.carlosrd.superhero.ui.extension.viewBinding
import com.carlosrd.superhero.ui.extension.visible
import com.carlosrd.superhero.ui.manager.ImageManager
import com.carlosrd.superhero.ui.manager.StringManager
import com.carlosrd.superhero.ui.manager.ToolbarManager

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharacterDetailFragment : Fragment(R.layout.fragment_character_detail) {

    @Inject internal lateinit var viewModelFactory: ViewModelFactory
    @Inject internal lateinit var imageManager: ImageManager
    @Inject internal lateinit var toolbarManager: ToolbarManager
    @Inject internal lateinit var stringManager: StringManager

    private val viewModel: CharacterDetailViewModel by viewModels { viewModelFactory }
    private val binding by viewBinding<FragmentCharacterDetailBinding>()

    private lateinit var recyclerAdapter : CharacterDetailsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context.applicationContext as CharactersComponentProvider)
            .provideCharactersComponent()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            viewModel.characterId = it.getLong(characterIdIntentKey, 0)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerDetails()
        setUiStateObserver()
        setListeners()

        viewModel.loadCharacterDetails()

    }

    private fun setupRecyclerDetails(){

        recyclerAdapter = CharacterDetailsAdapter(stringManager)

        with(binding.characterDetailsRecycler){
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerAdapter
            addItemDecoration(StickyHeaderItemDecorator(this, recyclerAdapter))
        }
    }

    private fun setListeners(){

        binding.characterDetailsRetryButton.setOnClickListener {
            viewModel.loadCharacterDetails()
        }

    }

    private fun setUiStateObserver(){

        // Create a new coroutine since repeatOnLifecycle is a suspend function
        viewLifecycleOwner.lifecycleScope.launch {
            // The block passed to repeatOnLifecycle is executed when the lifecycle
            // is at least STARTED and is cancelled when the lifecycle is STOPPED.
            // It automatically restarts the block when the lifecycle is STARTED again.
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Safely collect from uiState when the lifecycle is STARTED
                // and stops collection when the lifecycle is STOPPED
                viewModel.uiState.collect { uiState ->

                    when(uiState){
                        is CharacterDetailsUiState.Loading -> {
                            binding.characterDetailsProgressBar.isVisible = uiState.showLoading
                            binding.characterDetailsRetryButton.gone()
                            binding.characterDetailsErrorMsg.gone()
                        }
                        is CharacterDetailsUiState.OnDataError -> {
                            binding.characterDetailsProgressBar.gone()
                            binding.characterDetailsRetryButton.visible()
                            binding.characterDetailsErrorMsg.visible()
                            binding.characterDetailsErrorMsg.text = uiState.error
                        }
                        is CharacterDetailsUiState.OnDataReady -> {

                            binding.characterDetailsProgressBar.gone()
                            binding.characterDetailsRetryButton.gone()
                            binding.characterDetailsErrorMsg.gone()

                            toolbarManager.getCollapsingToolbarLayout()?.setTitle(uiState.characterData.characterName)
                            toolbarManager.getToolbar()?.setTitle(uiState.characterData.characterName)
                            toolbarManager.getToolbarImage()?.apply {

                                if (uiState.characterData.imageUrl.contains("image_not_available")){
                                    imageManager.load(R.drawable.ic_superhero, this)
                                } else {
                                    imageManager.loadFrom(uiState.characterData.imageUrl, this, R.drawable.ic_superhero)
                                }

                                toolbarManager.getAppBarLayout()?.apply {
                                    setExpanded(true)
                                }
                               visible()
                            }

                            recyclerAdapter.setItems(uiState.characterData.features)

                        }
                    }

                }

            }

        }

    }

    private fun setupToolbar() {

        val drawable = resources.getDrawable(R.drawable.ic_back)
        val bitmap: Bitmap = (drawable as BitmapDrawable).bitmap
        val newdrawable: Drawable =
            BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap,
                24,
                24,
                true))

        toolbarManager.getToolbar()?.apply {
            setNavigationIcon(newdrawable)
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
    }

    companion object {

        private val characterIdIntentKey = "CHARACTER_ID_INTENT_KEY"

        fun newInstance(characterId: Long): CharacterDetailFragment {
            val fragment = CharacterDetailFragment()
            val bundle = Bundle().also { it.putLong(characterIdIntentKey, characterId) }
            fragment.apply { arguments = bundle }
            return fragment
        }

    }


}