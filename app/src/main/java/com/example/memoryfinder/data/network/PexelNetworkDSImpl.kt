package com.example.memoryfinder.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.memoryfinder.data.model.PexelsResults
import com.example.memoryfinder.utils.NoConnectionException


class PexelNetworkDSImpl(
    private val pexelsApiService: PexelsApiService,
) : PexelNetworkDS {

    private val TAG : String = "Network";
    private val _downloadedCurrentMemories = MutableLiveData<PexelsResults>()
    override val downloadedCurrentMemories: LiveData<PexelsResults>
        get() = _downloadedCurrentMemories



    override suspend fun fetchImages(keyword: String, per_page: Int, locale: String, page: Int) {
        try{
            val fetchImages = pexelsApiService.getPhotos(keyword, per_page, page, locale)
            _downloadedCurrentMemories.postValue(fetchImages)
        }catch (e: NoConnectionException){
            Log.e("Connectivity", "No connection found", e)
        }
    }

    override suspend fun fetchCuratedImages(per_page: Int, page: Int) {
        try{
            Log.d(TAG, "Trying to fetch the photos")
            val fetchImages = pexelsApiService.getCuratedPhotos(per_page, page)
            _downloadedCurrentMemories.postValue(fetchImages)
        }catch (e: NoConnectionException){
            Log.e("Connectivity", "No connection found", e)
        }
    }

}