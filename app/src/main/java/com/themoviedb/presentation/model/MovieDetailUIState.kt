package com.themoviedb.presentation.model

import com.themoviedb.domain.model.MovieDetail

sealed class MovieDetailUIState {
    data object Loading : MovieDetailUIState()
    data class Error(val errorMessage: String) : MovieDetailUIState()
    data class Success(val movieDetail: MovieDetail) : MovieDetailUIState()
    data object IsOffline : MovieDetailUIState()
}