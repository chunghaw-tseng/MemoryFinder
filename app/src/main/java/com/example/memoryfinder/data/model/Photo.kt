package com.example.memoryfinder.data.model


import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("avg_color")
    val avgColor: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    @SerializedName("photographer_id")
    val photographerId: Int,
    @SerializedName("photographer_url")
    val photographerUrl: String,
    val src: SrcX,
    val url: String,
    val width: Int
)