package com.themoviedb.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    val adult: Boolean? = null,
    val backdropPath: String? = null,
    val genreIds: String? = null,
    @PrimaryKey val id: Int,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
    val page: Int? = null, // Page number for pagination
    val timestamp: Long? = null // Timestamp for freshness
)
