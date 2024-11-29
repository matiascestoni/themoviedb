package com.themoviedb.data.remote.api

import com.themoviedb.domain.model.MovieDetail
import com.themoviedb.domain.model.MovieResponse

interface MovieApiService {

    suspend fun getPopularMovies(page: Int): MovieResponse

    suspend fun getMoviesByCategory(category: String): MovieResponse

    suspend fun getMovieDetails(movieId: Int): MovieDetail?
}