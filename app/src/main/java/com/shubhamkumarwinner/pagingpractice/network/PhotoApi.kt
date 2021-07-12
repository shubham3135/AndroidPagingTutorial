package com.shubhamkumarwinner.pagingpractice.network

import com.shubhamkumarwinner.pagingpractice.BuildConfig
import com.shubhamkumarwinner.pagingpractice.data.Photo
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PhotoApi {
    companion object{
        const val CLIENT_ID = BuildConfig.UNSPLASH_ACCESS_KEY
        const val BASE_URL = "https://api.unsplash.com/"
    }
    @Headers("Accept-Version: v1", "Authorization: Client-ID $CLIENT_ID")
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query")query: String,
        @Query("page")page: Int,
        @Query("per_page")perPage: Int
    ): PhotoResponse
}