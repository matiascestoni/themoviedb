package com.themoviedb.data.repository

import com.themoviedb.common.Response
import com.themoviedb.data.local.LocalDataSource
import com.themoviedb.data.remote.api.MovieApiService
import com.themoviedb.domain.model.Movie
import com.themoviedb.domain.model.MovieDetail
import com.themoviedb.domain.model.MovieGenre
import com.themoviedb.domain.repository.MovieRepository
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService,
    private val localDataSource: LocalDataSource
) : MovieRepository {

    private val cacheExpirationTime = TimeUnit.MINUTES.toMillis(1)

    private fun isDataFresh(lastUpdateTime: Long?): Boolean {
        return lastUpdateTime != null && (System.currentTimeMillis() - lastUpdateTime) < cacheExpirationTime
    }

    override suspend fun fetchPopularMovies(page: Int): Response<List<Movie>> {
        return try {
            // Attempt to load local data
            val localMovies = localDataSource.fetchPopularMovies(page)
            val lastUpdateTime = localDataSource.getLastUpdateTime()

            // Check if data is fresh
            if (localMovies.isNotEmpty() && isDataFresh(lastUpdateTime)) {
                Response.Success(localMovies)
            } else {
                // Fetch from remote if data is stale or unavailable locally
                val response = apiService.getPopularMovies("en-US", page)
                localDataSource.savePopularMovies(response.results, page)
                localDataSource.updateLastUpdateTime(System.currentTimeMillis())
                Response.Success(response.results)
            }
        } catch (networkError: Exception) {
            Timber.e(networkError, "Network error, attempting to use local data.")
            // Fallback to local data if network request fails
            val localMovies = localDataSource.fetchPopularMovies(page)
            Response.Success(localMovies)
        } catch (e: Exception) {
            Timber.e("Failed to fetch popular movies: ${e.message}")
            Response.Error("Failed to fetch popular movies: ${e.message}")
        }
    }

    override suspend fun fetchPopularMovies(): Response<List<Movie>> {
        val movies = mutableListOf<Movie>()
        for (page in 1..3) {
            val response = fetchPopularMovies(page)
            if (response is Response.Success) {
                movies.addAll(response.result as Collection<Movie>)
            } else {
                return Response.Error("Error getting movies")
            }
        }
        return Response.Success(movies)
    }

    override suspend fun fetchMoviesByGenre(): Map<String, List<Movie>> {
        val map = mutableMapOf<String, List<Movie>>()
        for (genre in MovieGenre.entries) {
            val movies = localDataSource.fetchMoviesByGenre(genre.id)
            map[genre.genreName] = movies
        }
        return map
    }

    override suspend fun fetchMoviesByGenre(genreId: Int): List<Movie> {
        return try {
            localDataSource.fetchMoviesByGenre(genreId)
        } catch (e: Exception) {
            Timber.e("Failed to fetch movies by category: ${e.message}")
            return emptyList()
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
                val remoteMovieDetail = apiService.getMovieDetails(movieId, "en-US")
                remoteMovieDetail?.let {
                    localDataSource.saveMovieDetails(remoteMovieDetail)
                }
                Response.Success(remoteMovieDetail)
            }
        } catch (networkError: Exception) {
            Timber.e(networkError, "Network error, attempting to use local data.")

            // Fallback to local data if network request fails
            val localMovieDetail = localDataSource.fetchMovieDetails(movieId)
            if (localMovieDetail != null) {
                Response.Success(localMovieDetail)
            } else {
                Response.Error("Failed to fetch details for movie ID: $movieId. ${networkError.message}")
            }
        } catch (e: Exception) {
            Timber.e("Failed to fetch details for movie ID: $movieId. ${e.message}")
            Response.Error("Failed to fetch details for movie ID: $movieId. ${e.message}")
        }
    }

    override suspend fun fetchTopPopularMovies(limit: Int): List<Movie> {
        return localDataSource.fetchTopPopularMovies(limit)
    }
}