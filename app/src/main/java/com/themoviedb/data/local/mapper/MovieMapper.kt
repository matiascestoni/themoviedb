package com.themoviedb.data.local.mapper

import com.google.gson.Gson
import com.themoviedb.data.local.model.MovieEntity
import com.themoviedb.domain.model.Movie

private val gson = Gson()

fun Movie.toEntity(page: Int): MovieEntity {
    return MovieEntity(
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds?.let { gson.toJson(it) }, // Convert list to JSON string
        id = this.id ?: 0,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        page = page
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds?.let { gson.fromJson(it, Array<Int>::class.java)?.toList() },
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount,
        page = this.page
    )
}