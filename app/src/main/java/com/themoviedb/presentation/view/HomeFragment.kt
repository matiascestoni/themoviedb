package com.themoviedb.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.themoviedb.R
import com.themoviedb.databinding.FragmentHomeBinding
import com.themoviedb.presentation.adapter.CarouselAdapter
import com.themoviedb.presentation.adapter.GenreAdapter
import com.themoviedb.presentation.model.HomeUIState
import com.themoviedb.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var genreAdapter: GenreAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.init()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect(::handleUIState)
        }

        carouselAdapter = CarouselAdapter()
        binding.carouselViewPager.apply {
            adapter = carouselAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        TabLayoutMediator(binding.carouselIndicator, binding.carouselViewPager) { _, _ -> }.attach()

        genreAdapter = GenreAdapter()
        genreAdapter.setHasStableIds(true)
        binding.genreList.apply {
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun handleUIState(uiState: HomeUIState) {
        when (uiState) {
            is HomeUIState.Error -> TODO()
            HomeUIState.Loading -> {}
            is HomeUIState.Success -> {
                carouselAdapter.submitList(uiState.carouselMovies)
                genreAdapter.submitData(uiState.moviesByGenreMap)
            }
        }
    }

    private fun navigateToMovieDetail() {
        findNavController().navigate(R.id.action_HomeFragment_to_MovieDetailFragment)
    }
}