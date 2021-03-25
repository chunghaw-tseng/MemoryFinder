package com.example.memoryfinder.modelviews

import androidx.lifecycle.LiveData
import com.example.memoryfinder.model.MemoryImg

interface ViewModel {
    fun getAll(): LiveData<List<MemoryImg>>
    fun get(search : String) : LiveData<List<MemoryImg>>

}