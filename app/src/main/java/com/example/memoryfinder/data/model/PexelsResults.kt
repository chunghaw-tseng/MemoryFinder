package com.example.memoryfinder.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class PexelsResults(
    @SerializedName("next_page")
    val nextPage: String,
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val photos: List<Photo>,
    @SerializedName("total_results")
    val totalResults: Int
)