package com.themoviedb.domain.repository

import com.themoviedb.common.Response
import com.themoviedb.domain.model.Movie
import com.themoviedb.domain.model.MovieDetail
import com.themoviedb.domain.model.MovieResponse

interface MovieRepository {
    suspend fun fetchPopularMovies(): List<Movie>
    suspend fun fetchPopularMovies(page: Int): Response<MovieResponse>
    suspend fun fetchMoviesByCategory(category: String): List<Movie>
    suspend fun fetchTopPopularMovies(limit: Int): List<Movie>
    suspend fun fetchMovieDetails(movieId: Int): Response<MovieDetail?>
}
