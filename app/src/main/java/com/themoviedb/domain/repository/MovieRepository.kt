package com.themoviedb.domain.repository

import com.themoviedb.common.Response
import com.themoviedb.domain.model.Movie
import com.themoviedb.domain.model.MovieDetail

interface MovieRepository {

    suspend fun fetchCarouselMovies(): Response<List<Movie>>

    suspend fun fetchMoviesByCategory(category: String): Response<List<Movie>>

    suspend fun fetchMovieDetails(movieId: Int): Response<MovieDetail>
}