package com.carlosrd.superhero.ui.characters.feature.home

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.carlosrd.superhero.ui.characters.di.CharactersComponentProvider
import com.carlosrd.superhero.ui.characters.feature.R
import com.carlosrd.superhero.ui.characters.feature.databinding.FragmentCharactersListBinding
import com.carlosrd.superhero.ui.characters.feature.home.adapter.CharactersPagedListAdapter
import com.carlosrd.superhero.ui.characters.feature.home.adapter.CharactersPagedListAdapter.Companion.LOADING_ITEM
import com.carlosrd.superhero.ui.characters.feature.home.adapter.CharactersPagedListLoadStateAdapter
import com.carlosrd.superhero.ui.characters.navigation.CharactersInternalNavigator
import com.carlosrd.superhero.ui.di.viewmodel.ViewModelFactory
import com.carlosrd.superhero.ui.extension.gone
import com.carlosrd.superhero.ui.extension.showToast
import com.carlosrd.superhero.ui.extension.viewBinding
import com.carlosrd.superhero.ui.extension.visible
import com.carlosrd.superhero.ui.manager.ImageManager
import com.carlosrd.superhero.ui.manager.ToolbarManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharactersListFragment : Fragment(R.layout.fragment_characters_list) {

    @Inject internal lateinit var viewModelFactory: ViewModelFactory
    @Inject internal lateinit var internalNavigator: CharactersInternalNavigator
    @Inject internal lateinit var imageManager: ImageManager
    @Inject internal lateinit var toolbarManager: ToolbarManager

    private val viewModel: CharactersListViewModel by viewModels { viewModelFactory }
    private val binding by viewBinding<FragmentCharactersListBinding>()

    private lateinit var recyclerPagedAdapter : CharactersPagedListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (context.applicationContext as CharactersComponentProvider)
            .provideCharactersComponent()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPagedRecyclerView()
        setupToolbar()

        setListeners()
        setObservers()

    }

    override fun onStart() {
        super.onStart()

        toolbarManager.getToolbar()?.setTitle("MARVEL Characters")
        toolbarManager.getCollapsingToolbarLayout()?.setTitle("MARVEL Characters")

    }

    private fun setPagedRecyclerView() {

        recyclerPagedAdapter = CharactersPagedListAdapter(imageManager, { characterId ->
            viewModel.onCharacterClicked(characterId)
        })

        with(binding.charactersRecycler){

            val gridLayoutManager =  GridLayoutManager(context, 2)
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int =
                    when(recyclerPagedAdapter.getItemViewType(position)) {
                        LOADING_ITEM -> 1
                        else -> 2
                    }

            }

            layoutManager = gridLayoutManager

            val headerAdapter = CharactersPagedListLoadStateAdapter { recyclerPagedAdapter.retry() }
            val footerAdapter = CharactersPagedListLoadStateAdapter { recyclerPagedAdapter.retry() }

            adapter = recyclerPagedAdapter.withLoadStateHeaderAndFooter(
                header = headerAdapter,
                footer = footerAdapter
            )

            isNestedScrollingEnabled = false
        }

    }

    private fun setupToolbar() {

        toolbarManager.getToolbar()?.apply {

            setTitle("MARVEL Characters")
            setNavigationIcon(null)

        }

        toolbarManager.getAppBarLayout()?.apply {
            setExpanded(false, false)
        }

    }

    private fun setListeners() {

        binding.characterListRetryButton.setOnClickListener {
            recyclerPagedAdapter.retry()
        }

    }

    private fun setObservers() {

        setUiStateObserver()
        setEventsObserver()
        setLoadStateObserver()

    }

    private fun setUiStateObserver() {

        // Create a new coroutine since repeatOnLifecycle is a suspend function
        viewLifecycleOwner.lifecycleScope.launch {
            // The block passed to repeatOnLifecycle is executed when the lifecycle
            // is at least STARTED and is cancelled when the lifecycle is STOPPED.
            // It automatically restarts the block when the lifecycle is STARTED again.
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Safely collect from uiState when the lifecycle is STARTED
                // and stops collection when the lifecycle is STOPPED
                viewModel.uiState.collect {
                    when(it){
                        is CharactersListUiState.Loading -> {

                            binding.characterListProgressBar.visible()
                            binding.charactersRecycler.gone()
                            binding.characterListRetryButton.gone()
                            binding.characterListErrorMsg.gone()

                        }
                        is CharactersListUiState.OnDataReady -> {

                            binding.charactersRecycler.visible()
                            binding.characterListProgressBar.gone()
                            binding.characterListRetryButton.gone()
                            binding.characterListErrorMsg.gone()

                            recyclerPagedAdapter.submitData(it.pagingData)
                        }
                    }
                }

            }
        }
    }

    private fun setEventsObserver() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.eventsFlow.collect {
                    when(it){
                        is CharactersListEvent.NavigateToCharacterDetails ->
                            internalNavigator.goToCharacterDetails(parentFragmentManager, it.characterId)
                        is CharactersListEvent.ShowSnackBar -> {}
                        is CharactersListEvent.ShowToast -> {
                            showToast(it.text)
                        }
                    }
                }

            }
        }
    }

    private fun setLoadStateObserver() {

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                recyclerPagedAdapter.loadStateFlow.collect { loadState ->

                    val isListEmpty = loadState.refresh is LoadState.NotLoading && recyclerPagedAdapter.itemCount == 0
                    // show empty list
                    // emptyList.isVisible = isListEmpty
                    // Only show the list if refresh succeeds.
                    binding.charactersRecycler.isVisible = !isListEmpty
                    // Show loading spinner during initial load or refresh.
                    binding.characterListProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                    // Show the retry state if initial load or refresh fails.
                    binding.characterListErrorMsg.isVisible = loadState.source.refresh is LoadState.Error
                    binding.characterListRetryButton.isVisible = loadState.source.refresh is LoadState.Error

                    // Toast on any error, regardless of whether it came from RemoteMediator or PagingSource
                    val errorState = loadState.source.append as? LoadState.Error
                        ?: loadState.source.prepend as? LoadState.Error
                        ?: loadState.append as? LoadState.Error
                        ?: loadState.prepend as? LoadState.Error
                    errorState?.let {
                        binding.characterListErrorMsg.text = "\uD83D\uDE28 Wooops ${it.error}"
                    }

                }

            }
        }
    }

    companion object {
        fun newInstance() = CharactersListFragment()
    }

}