package com.themoviedb.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.themoviedb.data.local.dao.MovieDao
import com.themoviedb.data.local.database.AppDatabase
import com.themoviedb.data.local.model.MovieEntity
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
class MovieDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: AppDatabase
    private lateinit var dao: MovieDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        dao = db.movieDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun testFetchPopularMovies() = runBlocking {
        // Arrange
        val movie1 = MovieEntity(id = 1, title = "Movie 1", popularity = 10.0, page = 1)
        val movie2 = MovieEntity(id = 2, title = "Movie 2", popularity = 20.0, page = 1)
        dao.saveMovies(listOf(movie1, movie2))

        // Act
        val movies = dao.fetchPopularMovies(page = 1)

        // Assert
        assertEquals(2, movies.size)
        assertEquals("Movie 2", movies[0].title)
        assertEquals("Movie 1", movies[1].title)
    }

    @Test
    fun testFetchMoviesByGenre() = runBlocking {
        // Arrange
        val movie1 = MovieEntity(id = 1, title = "Action Movie", popularity = 10.0, genreIds = "1")
        val movie2 = MovieEntity(id = 2, title = "Comedy Movie", popularity = 15.0, genreIds = "2")
        dao.saveMovies(listOf(movie1, movie2))

        // Act
        val actionMovies = dao.fetchMoviesByGenre(genreId = 1)

        // Assert
        assertEquals(1, actionMovies.size)
        assertEquals("Action Movie", actionMovies[0].title)
    }

    @Test
    fun testFetchMovieDetails() = runBlocking {
        // Arrange
        val movie = MovieEntity(id = 1, title = "Movie 1", popularity = 10.0, page = 1)
        dao.saveMovies(listOf(movie))

        // Act
        val fetchedMovie = dao.fetchMovieDetails(movieId = 1)

        // Assert
        assertNotNull(fetchedMovie)
        assertEquals("Movie 1", fetchedMovie?.title)
    }

    @Test
    fun testFetchTopPopularMovies() = runBlocking {
        // Arrange
        val movie1 = MovieEntity(id = 1, title = "Movie 1", popularity = 10.0)
        val movie2 = MovieEntity(id = 2, title = "Movie 2", popularity = 20.0)
        val movie3 = MovieEntity(id = 3, title = "Movie 3", popularity = 30.0)
        dao.saveMovies(listOf(movie1, movie2, movie3))

        // Act
        val topMovies = dao.fetchTopPopularMovies(limit = 2)

        // Assert
        assertEquals(2, topMovies.size)
        assertEquals(
            "Movie 3",
            topMovies[0].title
        )  // Assert that the movie with highest popularity is first
        assertEquals("Movie 2", topMovies[1].title)
    }

    @Test
    fun testClear() = runBlocking {
        // Arrange
        val movie = MovieEntity(id = 1, title = "Movie 1", popularity = 10.0)
        dao.saveMovies(listOf(movie))

        // Act
        dao.clear()

        // Assert
        val movies = dao.fetchPopularMovies(page = 1)
        assertTrue(movies.isEmpty())
    }
}