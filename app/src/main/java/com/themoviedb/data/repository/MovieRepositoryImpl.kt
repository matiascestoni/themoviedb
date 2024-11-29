package com.themoviedb.data.repository

import com.themoviedb.common.Response
import com.themoviedb.data.local.LocalDataSource
import com.themoviedb.data.remote.api.MovieApiService
import com.themoviedb.domain.model.MovieDetail
import com.themoviedb.domain.model.MovieResponse
import com.themoviedb.domain.repository.MovieRepository
import java.util.concurrent.TimeUnit

class MovieRepositoryImpl(
    private val apiService: MovieApiService,
    private val localDataSource: LocalDataSource
) : MovieRepository {

    private val cacheExpirationTime = TimeUnit.HOURS.toMillis(3000)

    private fun isDataFresh(lastUpdateTime: Long?): Boolean {
        return lastUpdateTime != null && (System.currentTimeMillis() - lastUpdateTime) < cacheExpirationTime
    }

    override suspend fun fetchPopularMovies(page: Int): Response<MovieResponse> {
        return try {
            // Attempt to load local data
            val localMovies = localDataSource.fetchPopularMovies(page)
            val lastUpdateTime = localDataSource.getLastUpdateTime()

            // Check if data is fresh
            if (localMovies != null && isDataFresh(lastUpdateTime)) {
                Response.Success(localMovies)
            } else {
                // Fetch from remote if data is stale or unavailable locally
                val remoteMovies = apiService.getPopularMovies(page)
                localDataSource.savePopularMovies(remoteMovies, page)
                localDataSource.updateLastUpdateTime(System.currentTimeMillis())
                Response.Success(remoteMovies)
            }
        } catch (networkError: Exception) {
            //Timber.e(networkError, "Network error, attempting to use local data.")

            // Fallback to local data if network request fails
            val localMovies = localDataSource.fetchPopularMovies(page)
            if (localMovies != null) {
                Response.Success(localMovies)
            } else {
                Response.Error("Failed to fetch Movies: ${networkError.message}")
            }
        }
    }

    override suspend fun fetchMoviesByCategory(category: String): Response<MovieResponse> {
        return try {
            // Attempt to load local data
            val localMovies = localDataSource.fetchMoviesByCategory(category)
            val lastUpdateTime = localDataSource.getCategoryLastUpdateTime(category)

            // Check if data is fresh
            if (localMovies != null && isDataFresh(lastUpdateTime)) {
                Response.Success(localMovies)
            } else {
                // Fetch from remote if data is stale or unavailable locally
                val remoteMovies = apiService.getMoviesByCategory(category)
                localDataSource.saveMoviesByCategory(remoteMovies, category)
                localDataSource.updateCategoryLastUpdateTime(category, System.currentTimeMillis())
                Response.Success(remoteMovies)
            }
        } catch (networkError: Exception) {
            //Timber.e(networkError, "Network error, attempting to use local data.")

            // Fallback to local data if network request fails
            val localMovies = localDataSource.fetchMoviesByCategory(category)
            if (localMovies != null) {
                Response.Success(localMovies)
            } else {
                Response.Error("Failed to fetch movies for category: $category. ${networkError.message}")
            }
        }
    }

    override suspend fun fetchMovieDetails(movieId: Int): Response<MovieDetail?> {
        return try {
            // Attempt to load local data
            val localMovieDetail = localDataSource.fetchMovieDetails(movieId)
            val lastUpdateTime = localDataSource.getLastUpdateTime()

            // Check if local details exist
            if (localMovieDetail != null && isDataFresh(lastUpdateTime)) {
                Response.Success(localMovieDetail)
            } else {
                // Fetch from remote if details are stale or unavailable locally
                val remoteMovieDetail = apiService.getMovieDetails(movieId)
                remoteMovieDetail?.let {
                    localDataSource.saveMovieDetails(remoteMovieDetail)
                }
                Response.Success(remoteMovieDetail)
            }
        } catch (networkError: Exception) {
            //Timber.e(networkError, "Network error, attempting to use local data.")

            // Fallback to local data if network request fails
            val localMovieDetail = localDataSource.fetchMovieDetails(movieId)
            if (localMovieDetail != null) {
                Response.Success(localMovieDetail)
            } else {
                Response.Error("Failed to fetch details for movie ID: $movieId. ${networkError.message}")
            }
        }
    }
}