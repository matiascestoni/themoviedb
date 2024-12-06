package com.themoviedb.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themoviedb.common.Response
import com.themoviedb.domain.repository.MovieRepository
import com.themoviedb.presentation.model.MovieDetailNavigation
import com.themoviedb.presentation.model.MovieDetailUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailUIState>(MovieDetailUIState.Loading)
    val uiState: StateFlow<MovieDetailUIState> = _uiState

    private val _navigation = MutableStateFlow<MovieDetailNavigation?>(null)
    val navigation: StateFlow<MovieDetailNavigation?> = _navigation

    private var isTablet: Boolean = false

    fun init(movieId: String, isTablet: Boolean) = viewModelScope.launch {
        this@MovieDetailViewModel.isTablet = isTablet
        when (val response = repository.fetchMovieDetails(movieId.toInt())) {
            is Response.Error -> _uiState.value = MovieDetailUIState.Error(response.message)
            is Response.Success -> {
                if (response.isOffline) {
                    _uiState.value = MovieDetailUIState.IsOffline
                }
                if (response.result == null) {
                    _uiState.value = MovieDetailUIState.Error("Movie not found")
                    _navigation.value = MovieDetailNavigation.ToMHome
                } else {
                    _uiState.value = MovieDetailUIState.Success(response.result)
                }
            }
        }
    }

    fun onBackPressed() {
        if (isTablet) {
            _uiState.value = MovieDetailUIState.GoBack
        } else {
            _navigation.value = MovieDetailNavigation.ToMHome
        }
    }

    fun onNavigationHandled() = viewModelScope.launch {
        _navigation.value = null
    }
}