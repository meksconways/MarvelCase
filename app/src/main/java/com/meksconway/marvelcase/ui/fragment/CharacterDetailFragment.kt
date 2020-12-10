package com.meksconway.marvelcase.ui.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.meksconway.marvelcase.R
import com.meksconway.marvelcase.base.BaseFragment
import com.meksconway.marvelcase.databinding.FragmentCharacterDetailBinding
import com.meksconway.marvelcase.ui.adapter.CharacterDetailAdapter
import com.meksconway.marvelcase.util.Status.*
import com.meksconway.marvelcase.util.viewBinding
import com.meksconway.marvelcase.viewmodel.CharacterDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailFragment :
    BaseFragment<CharacterDetailViewModel>(R.layout.fragment_character_detail) {

    override val binding by viewBinding(FragmentCharacterDetailBinding::bind)
    override val viewModel: CharacterDetailViewModel by viewModels()

    private val characterDetailAdapter = CharacterDetailAdapter()
    private val args: CharacterDetailFragmentArgs by navArgs()

    override fun viewDidLoad(savedInstanceState: Bundle?) {
        super.viewDidLoad(savedInstanceState)

        activity?.title = args.characterName
        (activity as? AppCompatActivity)?.supportActionBar?.title = args.characterName

        binding.rvCharacterDetail.run {
            adapter = characterDetailAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        lifecycleScope.launch {
            viewModel.getCharacter(args.characterId)

        }

    }

    override fun observeViewModel(viewModel: CharacterDetailViewModel) {
        super.observeViewModel(viewModel)
        viewModel.characterData.observe(viewLifecycleOwner) {
            when (it.status) {
                SUCCESS -> {
                    binding.progressBar.isVisible = false
                    characterDetailAdapter.submitList(it.data)
                }
                FAIL -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(this.requireContext(), it.errorMessage, Toast.LENGTH_SHORT)
                        .show()
                }
                LOADING -> {
                    binding.progressBar.isVisible = true
                }
            }
        }
    }


}