package com.themoviedb.data.repository

import com.themoviedb.common.Response
import com.themoviedb.data.local.LocalDataSource
import com.themoviedb.data.remote.api.MovieApiService
import com.themoviedb.domain.model.Movie
import com.themoviedb.domain.model.MovieDetail
import com.themoviedb.domain.model.MovieResponse
import com.themoviedb.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class MovieRepositoryImplTest {

    private lateinit var repository: MovieRepository
    private lateinit var apiService: MovieApiService
    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setup() {
        apiService = mockk()
        localDataSource = mockk()
        repository = MovieRepositoryImpl(apiService, localDataSource)
    }

    @Test
    fun `fetchPopularMovies should return local data if not stale`() = runTest {
        // Arrange
        val localMovies = listOf(Movie(id = 1, title = "Movie 1"))
        val lastUpdateTime = System.currentTimeMillis()
        coEvery { localDataSource.fetchPopularMovies(any()) } returns localMovies
        coEvery { localDataSource.getLastUpdateTime() } returns lastUpdateTime

        // Act
        val result = repository.fetchPopularMovies(1)

        // Assert
        assert(result is Response.Success)
        assertEquals(localMovies, (result as Response.Success).result)
    }

    @Test
    fun `fetchPopularMovies should return remote data if local data is stale`() = runTest {
        // Arrange
        val localMovies = listOf(Movie(id = 1, title = "Movie 1"))
        val remoteMovies = listOf(Movie(id = 1, title = "Movie 1"))
        val lastUpdateTime = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(2)
        coEvery { localDataSource.fetchPopularMovies(any()) } returns localMovies
        coEvery { localDataSource.getLastUpdateTime() } returns lastUpdateTime
        coEvery { apiService.getPopularMovies(any(), any()) } returns MovieResponse()

        // Act
        val result = repository.fetchPopularMovies(1)

        // Assert
        assert(result is Response.Success)
        assertEquals(remoteMovies, (result as Response.Success).result)
    }

    @Test
    fun `fetchPopularMovies should return local data if network fails`() = runTest {
        // Arrange
        val localMovies = listOf(Movie(id = 1, title = "Movie 1"))
        coEvery { localDataSource.fetchPopularMovies(any()) } returns localMovies
        coEvery { apiService.getPopularMovies(any(), any()) } throws Exception("Network error")

        // Act
        val result = repository.fetchPopularMovies(1)

        // Assert
        assert(result is Response.Success)
        assertEquals(localMovies, (result as Response.Success).result)
        assert(result.isOffline)
    }

    @Test
    fun `fetchMovieDetails should return local data if not stale`() = runTest {
        // Arrange
        val movieDetail = MovieDetail(id = 1, title = "Movie 1")
        val lastUpdateTime = System.currentTimeMillis()
        coEvery { localDataSource.fetchMovieDetails(any()) } returns movieDetail
        coEvery { localDataSource.getLastUpdateTime() } returns lastUpdateTime

        // Act
        val result = repository.fetchMovieDetails(1)

        // Assert
        assert(result is Response.Success)
        assertEquals(movieDetail, (result as Response.Success).result)
    }

    @Test
    fun `fetchMovieDetails should return remote data if local data is stale`() = runTest {
        // Arrange
        val localMovieDetail = MovieDetail(id = 1, title = "Movie 1")  // Mock local movie detail
        val remoteMovieDetail = MovieDetail(id = 1, title = "Movie 1")  // Mock remote movie detail
        val lastUpdateTime = System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(2)
        coEvery { localDataSource.fetchMovieDetails(any()) } returns localMovieDetail
        coEvery { localDataSource.getLastUpdateTime() } returns lastUpdateTime
        coEvery { apiService.getMovieDetails(any(), any()) } returns remoteMovieDetail

        // Act
        val result = repository.fetchMovieDetails(1)

        // Assert
        assert(result is Response.Success)
        assertEquals(remoteMovieDetail, (result as Response.Success).result)
    }

    @Test
    fun `fetchMovieDetails should return local data if network fails`() = runTest {
        // Arrange
        val localMovieDetail = MovieDetail(id = 1, title = "Movie 1")  // Mock local movie detail
        coEvery { localDataSource.fetchMovieDetails(any()) } returns localMovieDetail
        coEvery { apiService.getMovieDetails(any(), any()) } throws Exception("Network error")

        // Act
        val result = repository.fetchMovieDetails(1)

        // Assert
        assert(result is Response.Success)
        assertEquals(localMovieDetail, (result as Response.Success).result)
        assert(result.isOffline)
    }
}
