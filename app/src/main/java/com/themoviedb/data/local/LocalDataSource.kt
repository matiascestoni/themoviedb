package com.themoviedb.data.local

import com.themoviedb.domain.model.MovieDetail
import com.themoviedb.domain.model.MovieResponse

interface LocalDataSource {
    suspend fun fetchPopularMovies(page: Int): MovieResponse?
    suspend fun savePopularMovies(response: MovieResponse, page: Int)
    suspend fun getLastUpdateTime(): Long?
    suspend fun updateLastUpdateTime(timestamp: Long)
    suspend fun fetchMoviesByCategory(category: String): MovieResponse?
    suspend fun saveMoviesByCategory(response: MovieResponse, category: String)
    suspend fun getCategoryLastUpdateTime(category: String): Long?
    suspend fun updateCategoryLastUpdateTime(category: String, timestamp: Long)
    suspend fun fetchMovieDetails(movieId: Int): MovieDetail?
    suspend fun saveMovieDetails(detail: MovieDetail)
}
