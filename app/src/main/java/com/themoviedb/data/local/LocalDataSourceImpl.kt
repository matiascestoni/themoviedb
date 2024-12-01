package com.themoviedb.data.local

import com.themoviedb.data.local.dao.AppStateDao
import com.themoviedb.data.local.dao.MovieDao
import com.themoviedb.data.local.dao.MovieDetailDao
import com.themoviedb.data.local.mapper.toEntity
import com.themoviedb.data.local.mapper.toModel
import com.themoviedb.data.local.mapper.toMovie
import com.themoviedb.data.local.model.AppStateEntity
import com.themoviedb.domain.model.Movie
import com.themoviedb.domain.model.MovieDetail
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val movieDetailsDao: MovieDetailDao,
    private val appStateDao: AppStateDao
) : LocalDataSource {

    override suspend fun fetchPopularMovies(page: Int): List<Movie> {
        return movieDao.fetchPopularMovies(page).map { it.toMovie() }
    }

    override suspend fun savePopularMovies(movies: List<Movie>, page: Int) {
        movieDao.saveMovies(movies.map { it.toEntity(page) })
    }

    override suspend fun getLastUpdateTime(): Long? {
        return appStateDao.getLastUpdateTime()
    }

    override suspend fun updateLastUpdateTime(timestamp: Long) {
        appStateDao.updateLastUpdateTime(AppStateEntity(0, timestamp))
    }

    override suspend fun fetchMoviesByGenre(genreId: Int): List<Movie> {
        val movieEntities = movieDao.fetchMoviesByGenre(genreId)
        return movieEntities.map { it.toMovie() }
    }

    override suspend fun fetchMovieDetails(movieId: Int): MovieDetail? {
        val movieDetailEntity = movieDetailsDao.getMovieDetails(movieId)
        return movieDetailEntity?.toModel()
    }

    override suspend fun saveMovieDetails(detail: MovieDetail) {
        movieDetailsDao.insertMovieDetail(detail.toEntity())
    }

    override suspend fun fetchTopPopularMovies(limit: Int): List<Movie> {
        val movieEntities = movieDao.fetchTopPopularMovies(limit)
        return movieEntities.map { it.toMovie() }
    }

    override suspend fun clearMovies() {
        movieDao.clear()
        movieDetailsDao.clear()
        appStateDao.clear()
    }
}