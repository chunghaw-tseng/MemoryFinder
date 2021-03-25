package com.example.memoryfinder.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.memoryfinder.data.model.PexelsResults
import com.example.memoryfinder.data.model.Photo

class PexelDaoImpl : PexelDao {

    private val _pexelResults = mutableListOf<Photo>()
    private val results = MutableLiveData<List<Photo>>()

    init {
        results.value = _pexelResults
    }

    override fun addImages(photos: List<Photo>){
        _pexelResults += photos
        results.value = _pexelResults
    }

    override fun searchImages(photos: List<Photo>): LiveData<List<Photo>> {
        TODO("Not yet implemented")
    }

    override fun getImages() = results as LiveData<List<Photo>>

}