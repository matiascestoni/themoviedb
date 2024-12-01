package com.themoviedb.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_details")
data class MovieDetailEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val belongsToCollection: String? = null,
    val budget: Int? = null,
    val genres: String? = null,
    val homepage: String? = null,
    val imdbId: String? = null,
    val originCountry: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val productionCompanies: String? = null,
    val productionCountries: String? = null,
    val releaseDate: String? = null,
    val revenue: Int? = null,
    val runtime: Int? = null,
    val spokenLanguages: String? = null,
    val status: String? = null,
    val tagline: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null
)
