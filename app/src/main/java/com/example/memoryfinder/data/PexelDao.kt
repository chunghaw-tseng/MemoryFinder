package com.example.memoryfinder.data

import androidx.lifecycle.LiveData
import com.example.memoryfinder.data.model.PexelsResults
import com.example.memoryfinder.data.model.Photo

interface PexelDao {
    fun addImages(photos:List<Photo>)
    fun searchImages(photos:List<Photo>) : LiveData<List<Photo>>
    fun getImages() : LiveData<List<Photo>>
}