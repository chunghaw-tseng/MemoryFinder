package com.example.memoryfinder.data.repo

import androidx.lifecycle.LiveData
import com.example.memoryfinder.data.PexelDao
import com.example.memoryfinder.data.model.PexelsResults
import com.example.memoryfinder.data.model.Photo
import com.example.memoryfinder.data.network.PexelNetworkDS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PexelsRepositoryImpl(
    private val pexelDao: PexelDao,
    private val pexelNetworkDS: PexelNetworkDS
) : PexelsRepository {

    private val currentPage : String  = "1"
    private val currentKeyword : String = ""

    init{
        pexelNetworkDS.downloadedCurrentMemories.observeForever{ newResults ->
            persistFetchImages(newResults)
        }
    }

    // Might need to save this somewhere else
    override suspend fun getCurrentImages(keyword: String, page: String): LiveData<List<Photo>> {
        // Returns a result depending on the stuff on top
        fetchLatestImages(keyword, page)
        return withContext(Dispatchers.IO){
            return@withContext pexelDao.getImages()
        }
    }

    private suspend fun fetchLatestImages(keyword: String, page: String){
        // Find a way to check if new data is needed for reload
        if (currentKeyword == ""){
            if(currentPage != page)
                pexelNetworkDS.fetchCuratedImages(page = page)
        }else{
            if(currentPage != page)
                pexelNetworkDS.fetchImages("computers", page = page)
        }
    }

    private fun persistFetchImages(fetchedImages : PexelsResults){
        // Save them in local storage
        GlobalScope.launch(Dispatchers.IO) {
            pexelDao.addImages(fetchedImages.photos)
        }
    }
}