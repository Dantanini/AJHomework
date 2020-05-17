package com.dantanini.ajhomework.api

import com.dantanini.ajhomework.model.Weather
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val LOCATION_TAIPEI = "臺北市"
private const val ELEMENT_NAME = "MinT"
private const val FORMAT_JSON = "JSON"

class WeatherManager(private val apiKey: String) {
    private var apiService: WeatherApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://opendata.cwb.gov.tw/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getApiClient())
            .build()
        apiService = retrofit.create(WeatherApiService::class.java)
    }

    private fun getApiClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .retryOnConnectionFailure(true)
            .build()
    }

    suspend fun getTaipeiWeathers(): List<Weather> {
        val weatherResponse = apiService.getWeatherResponse(apiKey, LOCATION_TAIPEI, ELEMENT_NAME, FORMAT_JSON)

        if (weatherResponse.success) {
            return weatherResponse.records.location[0].weatherElement[0].time
        } else {
            throw Exception("Request fail")
        }
    }
}