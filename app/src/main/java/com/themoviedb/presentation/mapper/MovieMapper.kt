package com.themoviedb.presentation.mapper

import com.themoviedb.domain.model.Movie
import com.themoviedb.presentation.model.MovieUIItem

fun Movie.toMovieUIItem(): MovieUIItem {
    return MovieUIItem(
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
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

fun MovieUIItem.toMovie(): Movie {
    return Movie(
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = this.genreIds,
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
