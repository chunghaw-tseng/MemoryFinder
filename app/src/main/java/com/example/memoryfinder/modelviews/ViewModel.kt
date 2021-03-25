package com.example.memoryfinder.modelviews

import androidx.lifecycle.LiveData
import com.example.memoryfinder.data.model.PexelsResults

interface ViewModel {
    fun getAll(): LiveData<List<PexelsResults>>
    fun get(search : String) : LiveData<List<PexelsResults>>

}