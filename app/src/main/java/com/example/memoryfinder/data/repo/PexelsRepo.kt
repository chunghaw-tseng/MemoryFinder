package com.example.memoryfinder.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.memoryfinder.data.model.PexelsResults
import com.example.memoryfinder.data.model.Photo


// This is where caching should go (Save the info to a DB)

interface PexelsRepo {
    fun getSearchPhoto(query: String) : LiveData<PagingData<Photo>>
}
