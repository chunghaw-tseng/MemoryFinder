package com.example.memoryfinder.modelviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.memoryfinder.data.provider.SearchProvider
import com.example.memoryfinder.data.repo.PexelsRepository

class MemoryViewerModelFactory(
    private val pexelsRepository: PexelsRepository,
) :  ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MemoryViewerViewModel(pexelsRepository) as T
    }

}