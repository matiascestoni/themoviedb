package com.themoviedb.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.themoviedb.data.local.dao.AppStateDao
import com.themoviedb.data.local.dao.MovieDao
import com.themoviedb.data.local.dao.MovieDetailDao
import com.themoviedb.data.local.database.AppDatabase
import com.themoviedb.data.local.model.AppStateEntity
import com.themoviedb.domain.model.Movie
import com.themoviedb.domain.model.MovieDetail
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalDataSourceImplTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var localDataSource: LocalDataSourceImpl
    private lateinit var movieDao: MovieDao
    private lateinit var movieDetailDao: MovieDetailDao
    private lateinit var appStateDao: AppStateDao

    @Before
    fun setup() {
        val db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        movieDao = db.movieDao()
        movieDetailDao = db.movieDetailsDao()
        appStateDao = db.appStateDao()
        localDataSource = LocalDataSourceImpl(movieDao, movieDetailDao, appStateDao)
    }

    @After
    fun teardown() = runBlocking {
        appStateDao.clear()
        movieDetailDao.clear()
        movieDao.clear()
    }

    @Test
    fun testFetchPopularMovies() = runBlocking {
        // Assert
        val movies = listOf(Movie(id = 1, title = "Movie 1"))
        localDataSource.savePopularMovies(movies, page = 1)

        // Act
        val fetchedMovies = localDataSource.fetchPopularMovies(page = 1)

        // Assert
        assertEquals(1, fetchedMovies.size)
        assertEquals("Movie 1", fetchedMovies.first().title)
    }

    @Test
    fun testSavePopularMovies() = runBlocking {
        // Assert
        val movies = listOf(Movie(id = 1, title = "Movie 1"))
        localDataSource.savePopularMovies(movies, page = 1)

        // Act
        val savedMovies = movieDao.fetchPopularMovies(page = 1)

        // Assert
        assertEquals(1, savedMovies.size)
        assertEquals("Movie 1", savedMovies.first().title)
    }

    @Test
    fun testGetLastUpdateTime() = runBlocking {
        // Assert
        appStateDao.updateLastUpdateTime(AppStateEntity(0, 123456789L))

        // Act
        val lastUpdateTime = localDataSource.getLastUpdateTime()

        // Assert
        assertEquals(123456789L, lastUpdateTime)
    }

    @Test
    fun testUpdateLastUpdateTime() = runBlocking {
        // Assert
        localDataSource.updateLastUpdateTime(123456789L)

        // Act
        val updatedTime = appStateDao.getLastUpdateTime()

        // Assert
        assertEquals(123456789L, updatedTime)
    }

    @Test
    fun testFetchMoviesByGenre() = runBlocking {
        // Assert
        val movies = listOf(Movie(id = 1, title = "Movie 1", genreIds = listOf(1)))
        localDataSource.savePopularMovies(movies, page = 1)

        // Act
        val fetchedMovies = localDataSource.fetchMoviesByGenre(1)

        // Assert
        assertEquals(1, fetchedMovies.size)
        assertEquals("Movie 1", fetchedMovies.first().title)
    }

    @Test
    fun testFetchMovieDetails() = runBlocking {
        // Assert
        val movieDetail = MovieDetail(id = 1, title = "Movie 1")
        localDataSource.saveMovieDetails(movieDetail)

        // Act
        val fetchedMovieDetail = localDataSource.fetchMovieDetails(movieId = 1)

        // Assert
        assertNotNull(fetchedMovieDetail)
        assertEquals("Movie 1", fetchedMovieDetail?.title)
    }

    @Test
    fun testSaveMovieDetails() = runBlocking {
        // Assert
        val movieDetail = MovieDetail(id = 1, title = "Movie 1")
        localDataSource.saveMovieDetails(movieDetail)

        // Act
        val savedMovieDetail = movieDetailDao.getMovieDetails(movieId = 1)

        // Assert
        assertNotNull(savedMovieDetail)
        assertEquals("Movie 1", savedMovieDetail?.title)
    }

    @Test
    fun testFetchTopPopularMovies() = runBlocking {
        // Assert
        val movies = listOf(Movie(id = 1, title = "Movie 1"))
        localDataSource.savePopularMovies(movies, page = 1)

        // Act
        val topMovies = localDataSource.fetchTopPopularMovies(limit = 1)

        // Assert
        assertEquals(1, topMovies.size)
        assertEquals("Movie 1", topMovies.first().title)
    }

    @Test
    fun testClearMovies() = runBlocking {
        // Assert
        val movies = listOf(Movie(id = 1, title = "Movie 1"))
        localDataSource.savePopularMovies(movies, page = 1)

        // Act
        localDataSource.clearMovies()

        // Assert
        val clearedMovies = movieDao.fetchPopularMovies(page = 1)
        assertTrue(clearedMovies.isEmpty())
    }
}
