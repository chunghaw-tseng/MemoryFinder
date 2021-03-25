package com.example.memoryfinder.data.network

import androidx.lifecycle.LiveData
import com.example.memoryfinder.data.model.PexelsResults

interface PexelNetworkDS {
    val downloadedCurrentMemories: LiveData<PexelsResults>

    suspend fun fetchImages(
        keyword: String,
        per_page: String = "50",
        locale: String = "en-US"
    )

}