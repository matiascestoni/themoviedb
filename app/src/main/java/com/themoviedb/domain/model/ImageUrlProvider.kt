package com.themoviedb.domain.model

object ImageUrlProvider {
    private const val BASE_URL = "https://image.tmdb.org/t/p/"

    fun getPosterImageUrl(posterPath: String, size: String = "w500"): String {
        return "$BASE_URL$size$posterPath"
    }

    fun getThumbnailImageUrl(posterPath: String, size: String = "w185"): String {
        return "$BASE_URL$size$posterPath"
    }
}