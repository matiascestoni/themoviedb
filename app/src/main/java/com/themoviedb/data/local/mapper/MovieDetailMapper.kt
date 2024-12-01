package com.themoviedb.data.local.mapper

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.themoviedb.data.local.model.MovieDetailEntity
import com.themoviedb.domain.model.MovieDetail

private val gson = Gson()

fun MovieDetailEntity.toModel(): MovieDetail {
    return MovieDetail(
        adult = this.adult,
        backdropPath = this.backdropPath,
        belongsToCollection = this.belongsToCollection?.let {
            Gson().fromJson(it, MovieDetail.BelongsToCollection::class.java)
        },
        budget = this.budget,
        genres = this.genres?.let {
            val type = object : TypeToken<List<MovieDetail.Genre>>() {}.type
            Gson().fromJson(it, type)
        },
        homepage = this.homepage,
        id = this.id,
        imdbId = this.imdbId,
        originCountry = this.originCountry?.split(",")?.map { it.trim() },
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        productionCompanies = this.productionCompanies?.let {
            val type = object : TypeToken<List<MovieDetail.ProductionCompany>>() {}.type
            Gson().fromJson(it, type)
        },
        productionCountries = this.productionCountries?.let {
            val type = object : TypeToken<List<MovieDetail.ProductionCountry>>() {}.type
            Gson().fromJson(it, type)
        },
        releaseDate = this.releaseDate,
        revenue = this.revenue,
        runtime = this.runtime,
        spokenLanguages = this.spokenLanguages?.let {
            val type = object : TypeToken<List<MovieDetail.SpokenLanguage>>() {}.type
            Gson().fromJson(it, type)
        },
        status = this.status,
        tagline = this.tagline,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

fun MovieDetail.toEntity(): MovieDetailEntity {
    return MovieDetailEntity(
        id = this.id ?: 0,
        adult = this.adult,
        backdropPath = this.backdropPath,
        belongsToCollection = this.belongsToCollection?.let { Gson().toJson(it) },
        budget = this.budget,
        genres = this.genres?.let { Gson().toJson(it) },
        homepage = this.homepage,
        imdbId = this.imdbId,
        originCountry = this.originCountry?.joinToString(","),
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        productionCompanies = this.productionCompanies?.let { Gson().toJson(it) },
        productionCountries = this.productionCountries?.let { Gson().toJson(it) },
        releaseDate = this.releaseDate,
        revenue = this.revenue,
        runtime = this.runtime,
        spokenLanguages = this.spokenLanguages?.let { Gson().toJson(it) },
        status = this.status,
        tagline = this.tagline,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}

