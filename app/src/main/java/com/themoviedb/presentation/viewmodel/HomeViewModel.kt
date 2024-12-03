package com.themoviedb.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.themoviedb.common.Response
import com.themoviedb.domain.repository.MovieRepository
import com.themoviedb.presentation.mapper.toMovieUIItem
import com.themoviedb.presentation.model.HomeNavigation
import com.themoviedb.presentation.model.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.map
import kotlin.collections.mapValues

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)
    var uiState: StateFlow<HomeUIState> = _uiState

    private val _navigation = MutableStateFlow<HomeNavigation?>(null)
    val navigation: StateFlow<HomeNavigation?> = _navigation

    fun onMovieSelected(movieId: Int) {
        _navigation.value = HomeNavigation.ToMovieDetail(movieId)
    }

    fun onNavigationHandled() {
        _navigation.value = null
    }

    fun init() = viewModelScope.launch {
        when (val response = repository.fetchPopularMovies()) {
            is Response.Error -> TODO()
            is Response.Success -> {
                val carouselMovies = response.result.take(5).map { it.toMovieUIItem() }
                val movieByGenreMap = repository.fetchMoviesByGenre()
                val movieUIItemByGenreMap = movieByGenreMap.mapValues { (_, movies) ->
                    movies.map { it.toMovieUIItem() }
                }
                _uiState.value = HomeUIState.Success(carouselMovies, movieUIItemByGenreMap)
            }
        }
    }
}