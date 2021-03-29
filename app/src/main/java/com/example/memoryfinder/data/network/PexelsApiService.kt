package com.example.memoryfinder.data.network

import android.util.Log
import com.example.memoryfinder.BuildConfig
import com.example.memoryfinder.data.model.PexelsResults
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


// This API KEY should be added in authorization header

interface PexelsApiService {

    companion object{
        const val BASE_URL = "https://api.pexels.com/v1/"
        val API_KEY = BuildConfig.PEXELS_API_KEY

        operator fun invoke(
            connectivityInterceptor: Connectivity
        ): PexelsApiService {
            val requestInterceptor = Interceptor{ chain ->
                val new_request = chain.request()
                    .newBuilder().addHeader("Authorization", API_KEY).build()
                return@Interceptor chain.proceed(new_request)
            }
            val httpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            Log.d("API", "Adding interceptor");
            return Retrofit.Builder().client(httpClient).baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build().create(PexelsApiService :: class.java)
        }
    }


    @GET(value = "search")
    suspend fun getPhotos(
        @Query(value = "query") keyword: String,
        @Query(value = "per_page") perPage: Int = 48,
        @Query(value = "page") page: Int = 1,
        @Query(value = "locale") locale: String = "en-US"
    ): PexelsResults

    @GET(value = "curated")
    suspend fun getCuratedPhotos(
        @Query(value = "per_page") perPage : Int = 48,
        @Query(value = "page") page : Int
    ) : PexelsResults


}