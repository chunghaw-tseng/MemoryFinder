package com.example.memoryfinder.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.memoryfinder.data.model.PexelsResults
import com.example.memoryfinder.utils.NoConnectionException

class PexelNetworkDSImpl(
    private val pexelsApiService: PexelsApiService
) : PexelNetworkDS {

    private val _downloadedCurrentMemories = MutableLiveData<PexelsResults>()
    override val downloadedCurrentMemories: LiveData<PexelsResults>
        get() = _downloadedCurrentMemories

    override suspend fun fetchImages(keyword: String, per_page: String, locale: String) {
        try{
            val fetchImages = pexelsApiService.getPhotos(keyword, per_page, locale)
            _downloadedCurrentMemories.postValue(fetchImages)
        }catch (e: NoConnectionException){
            Log.e("Connectivity", "No connection found", e)
        }
    }
}