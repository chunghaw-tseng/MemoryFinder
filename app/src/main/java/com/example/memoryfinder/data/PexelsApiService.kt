package com.example.memoryfinder.data

import retrofit2.http.GET

const val API_KEY = "563492ad6f91700001000001e2b665ee4c79434dad8bd39d6f4a6e88"

// This API KEY should be added in authorization header
// https://api.pexels.com/v1/search

interface PexelsApiService {

    @GET
    fun getAllPhotos()


}