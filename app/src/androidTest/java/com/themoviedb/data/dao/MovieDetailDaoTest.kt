package com.themoviedb.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.themoviedb.data.local.dao.MovieDetailDao
import com.themoviedb.data.local.database.AppDatabase
import com.themoviedb.data.local.model.MovieDetailEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDetailDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: AppDatabase
    private lateinit var dao: MovieDetailDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        dao = db.movieDetailsDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun testInsertMovieDetail() = runBlocking {
        // Assert
        val movieDetail = MovieDetailEntity(id = 1, title = "Movie 1")
        dao.insertMovieDetail(movieDetail)

        // Act
        val fetchedMovie = dao.getMovieDetails(movieId = 1)

        // Assert
        assertNotNull(fetchedMovie)
        assertEquals("Movie 1", fetchedMovie?.title)
    }

    @Test
    fun testGetMovieDetails() = runBlocking {
        // Assert
        val movieDetail = MovieDetailEntity(id = 1, title = "Movie 1")
        dao.insertMovieDetail(movieDetail)

        // Act
        val fetchedMovie = dao.getMovieDetails(movieId = 1)

        // Assert
        assertNotNull(fetchedMovie)
        assertEquals("Movie 1", fetchedMovie?.title)
    }

    @Test
    fun testClear() = runBlocking {
        // Assert
        val movieDetail = MovieDetailEntity(id = 1, title = "Movie 1")
        dao.insertMovieDetail(movieDetail)

        // Act
        dao.clear()

        // Assert
        val fetchedMovie = dao.getMovieDetails(movieId = 1)
        assertNull(fetchedMovie)
    }
}
