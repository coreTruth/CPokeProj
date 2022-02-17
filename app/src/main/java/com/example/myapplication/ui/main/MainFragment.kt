package com.example.myapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.myapplication.data.PokemonItemUI
import com.example.myapplication.databinding.MainFragmentBinding
import com.example.myapplication.ui.PokemonItemAdapter
import com.example.myapplication.util.disableUI
import com.example.myapplication.util.toast

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var pokemonItemAdapter: PokemonItemAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        pokemonItemAdapter = PokemonItemAdapter(::onMainBreedClick)
        rvPokemon.adapter = pokemonItemAdapter
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        )
        rvPokemon.addItemDecoration(dividerItemDecoration)

    }

    private fun initViewModel() = with(viewModel) {
        loading.observe(viewLifecycleOwner, ::showProgressBar)
        itemUIs.observe(viewLifecycleOwner, pokemonItemAdapter::submitList)
        fetchPageDataState.observe(viewLifecycleOwner) {
            when(it) {
                MainViewModel.FetchPageDataState.Success -> Unit
                is MainViewModel.FetchPageDataState.Error -> it.errorMessage?.let { errMsgId -> toast(errMsgId) }
                null -> Unit
            }
        }
    }

    private fun onMainBreedClick(item: PokemonItemUI, isLoadMoreButton: Boolean) {
        if (isLoadMoreButton) {
            viewModel.fetchNextPage()
        } else {
            val direction: NavDirections =
                MainFragmentDirections.actionMainFragmentToDetailFragment(item)

            findNavController().navigate(direction)
        }
    }

    private fun showProgressBar(show: Boolean) {
        binding.progressBar.isVisible = show
        disableUI(show)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}