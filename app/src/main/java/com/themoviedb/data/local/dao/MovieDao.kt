package com.themoviedb.data.local.dao

import androidx.room.*
import com.themoviedb.data.local.model.MovieEntity

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE page = :page ORDER BY popularity DESC")
    suspend fun fetchPopularMovies(page: Int): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<MovieEntity>)

    @Query("SELECT * FROM movies WHERE genreIds LIKE '%' || :genreId || '%' ORDER BY popularity DESC")
    suspend fun fetchMoviesByGenre(genreId: Int): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE id = :movieId LIMIT 1")
    suspend fun fetchMovieDetails(movieId: Int): MovieEntity?

    @Query("SELECT * FROM movies ORDER BY popularity DESC LIMIT :limit")
    suspend fun fetchTopPopularMovies(limit: Int): List<MovieEntity>

    @Query("DELETE FROM movies")
    suspend fun clear()
}
