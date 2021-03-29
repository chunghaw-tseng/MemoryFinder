package com.example.memoryfinder.modelviews

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.memoryfinder.data.repo.PexelsRepo

class MemoryViewModel(
    private val repository: PexelsRepo
): ViewModel() {
    private val currentSearch = MutableLiveData(DEFAULT)

    val currentPhotos = currentSearch.switchMap {queryString ->
        repository.getSearchPhoto(queryString).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String){
        currentSearch.value = query
    }

    companion object{
        private const val DEFAULT = "curated"
    }

}