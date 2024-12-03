package com.themoviedb.presentation.model

sealed class HomeNavigation {
    data class ToMovieDetail(val movieId: Int) : HomeNavigation()
}