package com.themoviedb.presentation.model

sealed class HomeUIState {
    data object Loading : HomeUIState()
    data class Error(val errorMessage: String) : HomeUIState()
    data class Success(
        val carouselMovies: List<MovieUIItem>,
        val moviesByGenreMap: Map<String, List<MovieUIItem>>
    ) : HomeUIState()
    data object IsOffline : HomeUIState()
    data class ShowMovieDetail(val movieId: Int) : HomeUIState()
}