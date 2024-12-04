package com.themoviedb.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.themoviedb.R
import com.themoviedb.databinding.FragmentDetailMovieBinding
import com.themoviedb.domain.model.MovieDetail
import com.themoviedb.presentation.model.MovieDetailNavigation
import com.themoviedb.presentation.model.MovieDetailUIState
import com.themoviedb.presentation.viewmodel.MovieDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailMovieBinding
    private lateinit var viewModel: MovieDetailViewModel
    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailMovieBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]
        viewModel.init(args.movieId)
        buildUI()
    }

    private fun buildUI() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect(::handleUIState)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigation.collect(::handleNavigation)
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            viewModel.onBackPressed()
        }
    }

    private fun handleUIState(uiState: MovieDetailUIState) {
        if (uiState !is MovieDetailUIState.Loading) hideLoading()
        when (uiState) {
            is MovieDetailUIState.Error -> showError(uiState.errorMessage)
            MovieDetailUIState.IsOffline -> showOfflineMessage()
            MovieDetailUIState.Loading -> showLoading()
            is MovieDetailUIState.Success -> handleSuccess(uiState.movieDetail)
        }
    }

    private fun handleNavigation(navigation: MovieDetailNavigation?) {
        when (navigation) {
            null -> {}
            is MovieDetailNavigation.ToMHome -> navigateToHome()
        }
        viewModel.onNavigationHandled()
    }

    private fun handleSuccess(movieDetail: MovieDetail) {
        binding.title.text = movieDetail.title
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_MovieDetailFragment_to_HomeFragment)
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
    }

    private fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }
}