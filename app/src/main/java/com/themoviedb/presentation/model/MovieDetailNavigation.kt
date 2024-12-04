package com.themoviedb.presentation.model

sealed class MovieDetailNavigation {
    data object ToMHome : MovieDetailNavigation()
}