package com.themoviedb.domain.repository

import com.themoviedb.common.Response
import com.themoviedb.domain.model.Movie
import com.themoviedb.domain.model.MovieDetail

interface MovieRepository {
    suspend fun fetchPopularMovies(): List<Movie>
    suspend fun fetchPopularMovies(page: Int): Response<List<Movie>>
    suspend fun fetchMoviesByCategory(genreId: Int): List<Movie>
    suspend fun fetchTopPopularMovies(limit: Int): List<Movie>
    suspend fun fetchMovieDetails(movieId: Int): Response<MovieDetail?>
}
