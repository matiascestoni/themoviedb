package com.themoviedb.data.local.model

import okhttp3.Interceptor
import okhttp3.Response

const val accessToken =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyYWEzNDE4NWI4MTExNWEwZTM0Y2I2ZWE2MzUxZmNiZiIsIm5iZiI6MTczMjgyNjAyNS4xMDM4NDUxLCJzdWIiOiI2NzQ4YjNiODA3OTBiZmZkNzc3NmE1NGYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.f6KFeRFDP1_ocbdLH1beJRUIqhtZ_AFKnOm8-SXnKv8"

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Accept", "application/json;charset=utf-8")
            .addHeader("Content-Type", "application/json;charset=utf-8")
            .addHeader("Authorization", "Bearer $accessToken")
            .addHeader("User-Agent", "CustomUserAgent")
            .build()
        return chain.proceed(request)
    }
}