package com.themoviedb.data.local

import com.themoviedb.domain.model.Movie
import com.themoviedb.domain.model.MovieDetail

interface LocalDataSource {
    suspend fun fetchPopularMovies(page: Int): List<Movie>
    suspend fun savePopularMovies(movies: List<Movie>, page: Int)
    suspend fun getLastUpdateTime(): Long?
    suspend fun updateLastUpdateTime(timestamp: Long)
    suspend fun fetchMoviesByGenre(genreId: Int): List<Movie>
    suspend fun fetchMovieDetails(movieId: Int): MovieDetail?
    suspend fun saveMovieDetails(detail: MovieDetail)
    suspend fun fetchTopPopularMovies(limit: Int): List<Movie>
    suspend fun clearMovies()
}
