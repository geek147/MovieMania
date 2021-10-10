package com.envious.data.remote

import com.envious.data.remote.response.MovieResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("/movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("/movie/top_rated")
    suspend fun getTopRatedMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieResponse>

    companion object {
        operator fun invoke(retrofit: Retrofit): MovieApiService = retrofit.create(MovieApiService::class.java)
    }
}
