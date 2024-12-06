package com.themoviedb.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
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
import com.themoviedb.presentation.model.HomeNavigation
import com.themoviedb.presentation.model.HomeUIState
import com.themoviedb.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), OnMovieSelectedListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private val carouselAdapter = CarouselAdapter()
    private val genreAdapter = GenreAdapter()

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
        val isTablet = resources.getBoolean(R.bool.isTablet)
        viewModel.init(isTablet)
        buildUI()
    }

    private fun buildUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect(::handleUIState)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigation.collect(::handleNavigation)
        }

        carouselAdapter.setListener(this)
        binding.carouselViewPager.apply {
            adapter = carouselAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
        }

        TabLayoutMediator(binding.carouselIndicator, binding.carouselViewPager) { _, _ -> }.attach()

        genreAdapter.setListener(this)
        genreAdapter.setHasStableIds(true)
        binding.genreList.apply {
            adapter = genreAdapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }
    }

    private fun handleUIState(uiState: HomeUIState) {
        if (uiState !is HomeUIState.Loading) hideLoading()
        when (uiState) {
            is HomeUIState.Error -> showError(uiState.errorMessage)
            HomeUIState.Loading -> showLoading()
            is HomeUIState.Success -> {
                carouselAdapter.submitList(uiState.carouselMovies)
                genreAdapter.submitData(uiState.moviesByGenreMap)
            }

            HomeUIState.IsOffline -> showOfflineMessage()
            is HomeUIState.ShowMovieDetail -> showMovieDetail(uiState.movieId)
        }
    }

    private fun showMovieDetail(movieId: Int) {
        val fragment = MovieDetailFragment().apply {
            arguments = Bundle().apply { putInt("movieId", movieId) }
        }
        childFragmentManager.beginTransaction()
            .replace(R.id.detailContainer, fragment)
            .commit()
    }

    private fun handleNavigation(navigation: HomeNavigation?) {
        when (navigation) {
            null -> {}
            is HomeNavigation.ToMovieDetail -> navigateToMovieDetail(navigation.movieId)
        }
        viewModel.onNavigationHandled()
    }

    private fun showOfflineMessage() {
        Toast.makeText(
            requireContext(),
            "No internet connection, using offline data.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.swipeRefreshLayout.isRefreshing = true
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun navigateToMovieDetail(movieId: Int) {
        val bundle = bundleOf("movieId" to movieId.toString())
        findNavController().navigate(R.id.action_HomeFragment_to_MovieDetailFragment, bundle)
    }

    override fun onMovieSelected(movieId: Int) {
        viewModel.onMovieSelected(movieId)
    }
}