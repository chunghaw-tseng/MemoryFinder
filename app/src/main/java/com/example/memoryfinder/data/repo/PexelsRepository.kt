package com.example.memoryfinder.data.repo

import androidx.lifecycle.LiveData
import com.example.memoryfinder.data.model.PexelsResults
import com.example.memoryfinder.data.model.Photo


// This is where caching should go (Save the info to a DB)

interface PexelsRepository {
    suspend fun getCurrentImages(keyword:String, page: Int) : LiveData<List<Photo>>
}