package com.themoviedb.data.local

import com.themoviedb.domain.model.Movie
import com.themoviedb.domain.model.MovieDetail
import com.themoviedb.domain.model.MovieResponse
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor() : LocalDataSource {
    override suspend fun fetchPopularMovies(page: Int): MovieResponse? {
        return null
    }

    override suspend fun savePopularMovies(response: MovieResponse, page: Int) {
        //TODO
    }

    override suspend fun getLastUpdateTime(): Long? {
        return null
    }

    override suspend fun updateLastUpdateTime(timestamp: Long) {
        //TODO
    }

    override suspend fun fetchMoviesByCategory(category: String): List<Movie> {
        return emptyList()
    }

    override suspend fun saveMoviesByCategory(response: MovieResponse, category: String) {

    }

    override suspend fun fetchMovieDetails(movieId: Int): MovieDetail? {
        return null
    }

    override suspend fun saveMovieDetails(detail: MovieDetail) {

    }

    override suspend fun getMoviesByGenre(id: Int): List<Movie> {
        return emptyList()
    }

    override suspend fun fetchTopPopularMovies(limit: Int): List<Movie> {
        return emptyList()
    }
}