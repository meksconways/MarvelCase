package com.meksconway.marvelcase.ui.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.meksconway.marvelcase.R
import com.meksconway.marvelcase.base.BaseFragment
import com.meksconway.marvelcase.databinding.FragmentCharactersBinding
import com.meksconway.marvelcase.ui.adapter.CharactersAdapter
import com.meksconway.marvelcase.ui.adapter.LoadMoreStateAdapter
import com.meksconway.marvelcase.util.viewBinding
import com.meksconway.marvelcase.viewmodel.CharactersViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharactersFragment : BaseFragment<CharactersViewModel>(R.layout.fragment_characters) {

    override val binding by viewBinding(FragmentCharactersBinding::bind)
    override val viewModel: CharactersViewModel by viewModels()

    private val charactersAdapter = CharactersAdapter {
        it?.id ?: return@CharactersAdapter
        it.name ?: return@CharactersAdapter
        val directions = CharactersFragmentDirections
            .actionCharactersFragmentToCharacterDetailFragment(
                characterId = it.id,
                characterName = it.name
            )
        findNavController().navigate(directions)
    }


    override fun viewDidLoad(savedInstanceState: Bundle?) {
        super.viewDidLoad(savedInstanceState)
        binding.rvCharacters.run {
            adapter = charactersAdapter.withLoadStateFooter(
                footer = LoadMoreStateAdapter {
                    charactersAdapter.retry()
                }
            )
            addItemDecoration(
                DividerItemDecoration(
                    this@CharactersFragment.requireContext(),
                    LinearLayoutManager.VERTICAL
                )
            )
            layoutManager = LinearLayoutManager(this@CharactersFragment.requireContext())
        }

        charactersAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                lifecycleScope.launchWhenResumed {
                    binding.progressBar.isVisible = true
                }

            } else {
                lifecycleScope.launchWhenResumed {
                    binding.progressBar.isGone = true
                }

                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(context, it.error.message, Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    override fun observeViewModel(viewModel: CharactersViewModel) {
        super.observeViewModel(viewModel)
        lifecycleScope.launch {
            viewModel.characters.collectLatest {
                charactersAdapter.submitData(it)
            }
        }
    }


}