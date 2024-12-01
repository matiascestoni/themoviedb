package com.themoviedb.data.local.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.themoviedb.domain.model.MovieDetail

class MovieDetailConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromBelongsToCollection(collection: MovieDetail.BelongsToCollection?): String? {
        return collection?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toBelongsToCollection(json: String?): MovieDetail.BelongsToCollection? {
        return json?.let { gson.fromJson(it, MovieDetail.BelongsToCollection::class.java) }
    }

    @TypeConverter
    fun fromGenreList(genres: List<MovieDetail.Genre?>?): String? {
        return genres?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toGenreList(json: String?): List<MovieDetail.Genre?>? {
        return json?.let {
            val type = object : TypeToken<List<MovieDetail.Genre?>>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromProductionCompanyList(companies: List<MovieDetail.ProductionCompany?>?): String? {
        return companies?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toProductionCompanyList(json: String?): List<MovieDetail.ProductionCompany?>? {
        return json?.let {
            val type = object : TypeToken<List<MovieDetail.ProductionCompany?>>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromProductionCountryList(countries: List<MovieDetail.ProductionCountry?>?): String? {
        return countries?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toProductionCountryList(json: String?): List<MovieDetail.ProductionCountry?>? {
        return json?.let {
            val type = object : TypeToken<List<MovieDetail.ProductionCountry?>>() {}.type
            gson.fromJson(it, type)
        }
    }

    @TypeConverter
    fun fromSpokenLanguageList(languages: List<MovieDetail.SpokenLanguage?>?): String? {
        return languages?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toSpokenLanguageList(json: String?): List<MovieDetail.SpokenLanguage?>? {
        return json?.let {
            val type = object : TypeToken<List<MovieDetail.SpokenLanguage?>>() {}.type
            gson.fromJson(it, type)
        }
    }
}
