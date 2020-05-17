package com.dantanini.ajhomework.api

import com.dantanini.ajhomework.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherApiService {
    @GET("v1/rest/datastore/F-C0032-001")
    suspend fun getWeatherResponse(
        @Header("cache-control") refresh: Boolean,
        @Query("Authorization") apiKey: String,
        @Query("locationName") locationName: String,
        @Query("elementName") elementName: String,
        @Query("format") format: String
    ): WeatherResponse
}