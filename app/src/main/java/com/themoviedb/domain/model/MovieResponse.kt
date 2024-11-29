package com.themoviedb.domain.model

data class MovieResponse(
    val page: Int? = null,
    val results: List<Movie?>? = null,
    val totalPages: Int? = null,
    val totalResults: Int? = null
)