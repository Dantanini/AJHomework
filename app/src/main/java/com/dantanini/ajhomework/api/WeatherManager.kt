package com.dantanini.ajhomework.api

import android.content.Context
import com.dantanini.ajhomework.model.Weather
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

private const val CACHE_SIZE = (5 * 1024 * 1024).toLong() //5MB
private const val CACHE_TIME_SECOND = 60 * 60 * 24
private const val CACHE_ONLY_KEYWORD = "only-if-cached"
private const val LOCATION_TAIPEI = "臺北市"
private const val ELEMENT_NAME = "MinT"
private const val FORMAT_JSON = "JSON"

class WeatherManager private constructor(private val context: Context, private val apiKey: String) {
    private var apiService: WeatherApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("opendata.cwb.gov.tw/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(getApiClient())
            .build()
        apiService = retrofit.create(WeatherApiService::class.java)
    }

    private fun getApiClient(): OkHttpClient {
        val cache = Cache(context.cacheDir, CACHE_SIZE)

        val interceptor = Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val refresh = request.cacheControl.toString() == true.toString()
            val cacheInfo =
                if (refresh) "no-cache" else "public, $CACHE_ONLY_KEYWORD, max-stale=$CACHE_TIME_SECOND"
            val newRequest = request.newBuilder()
                .removeHeader("cache-control")
                .addHeader("cache-control", cacheInfo)
                .build()
            chain.proceed(newRequest)
        }

        return OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(interceptor)
            .retryOnConnectionFailure(true)
            .build()
    }

    suspend fun getTaipeiWeather(refresh: Boolean): List<Weather> {
        val weatherResponse =
            apiService.getWeather(refresh, apiKey, LOCATION_TAIPEI, ELEMENT_NAME, FORMAT_JSON)

        if (weatherResponse.success) {
            return weatherResponse.records.location[0].weatherElement[0].time
        } else {
            throw Exception("Request fail")
        }
    }
}