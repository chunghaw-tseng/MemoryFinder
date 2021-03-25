package com.example.memoryfinder.data.network

import android.util.Log
import com.example.memoryfinder.data.model.PexelsResults
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "563492ad6f91700001000001e2b665ee4c79434dad8bd39d6f4a6e88"
const val BASE_URL = "https://api.pexels.com/v1/"
// This API KEY should be added in authorization header
// https://api.pexels.com/v1/search

interface PexelsApiService {

    @GET(value = "search")
    suspend fun getPhotos(
        @Query(value = "query") keyword : String,
        @Query(value = "per_page") perPage : String = "50",
        @Query(value = "locale") locale : String = "en-US"
    ): PexelsResults

    @GET(value = "curated")
    fun getCuratedPhotos(
        @Query(value = "per_page") perPage : String = "50",
        @Query(value = "page") page : String
    ) : PexelsResults

    companion object{
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

}