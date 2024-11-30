package com.themoviedb.data.local

import com.themoviedb.domain.model.Movie
import com.themoviedb.domain.model.MovieDetail
import com.themoviedb.domain.model.MovieResponse

interface LocalDataSource {
    suspend fun fetchPopularMovies(page: Int): MovieResponse?
    suspend fun savePopularMovies(response: MovieResponse, page: Int)
    suspend fun getLastUpdateTime(): Long?
    suspend fun updateLastUpdateTime(timestamp: Long)
    suspend fun fetchMoviesByCategory(category: String): List<Movie>
    suspend fun saveMoviesByCategory(response: MovieResponse, category: String)
    suspend fun fetchMovieDetails(movieId: Int): MovieDetail?
    suspend fun saveMovieDetails(detail: MovieDetail)
    suspend fun getMoviesByGenre(id: Int): List<Movie>
    suspend fun fetchTopPopularMovies(limit: Int): List<Movie>
}
