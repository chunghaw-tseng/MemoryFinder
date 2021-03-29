package com.example.memoryfinder.data.repo

import android.util.Log
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
    private val pexelNetworkDS: PexelNetworkDS)
//) : PexelsRepository {
{
    private val currentPage : Int  = 0
    private val currentKeyword : String = ""
    private val TAG:String = "Repository"

    init{
        pexelNetworkDS.downloadedCurrentMemories.observeForever{ newResults ->
            Log.d(TAG, newResults.toString())
            persistFetchImages(newResults)
        }
    }

    // TODO There needs a lot of changes
    // Might need to save this somewhere else
//    override suspend fun getCurrentImages(keyword: String, page: Int): LiveData<List<Photo>> {
//        // Returns a result depending on the stuff on top
//        Log.d(TAG, "Get latest photo")
//        fetchLatestImages(keyword, page)
//        return withContext(Dispatchers.IO){
//            return@withContext pexelDao.getImages()
//        }
//    }

    private suspend fun fetchLatestImages(keyword: String, page: Int){
        // Find a way to check if new data is needed for reload
        if (currentKeyword == ""){
            Log.d(TAG, "Curated Fetching")
            if(currentPage != page)
                pexelNetworkDS.fetchCuratedImages(page = page)
        }else{
            if(currentPage != page)
                pexelNetworkDS.fetchImages(keyword, page = page)
        }
    }

    private fun persistFetchImages(fetchedImages : PexelsResults){
        // Save them in local storage
        GlobalScope.launch(Dispatchers.IO) {
            pexelDao.addImages(fetchedImages.photos)
        }
    }
}