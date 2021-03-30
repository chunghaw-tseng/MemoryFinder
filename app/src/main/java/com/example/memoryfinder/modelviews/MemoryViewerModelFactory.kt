package com.example.memoryfinder.modelviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.memoryfinder.data.repo.PexelsRepo

class MemoryViewerModelFactory(
    private val pexelsRepository: PexelsRepo,
) :  ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MemoryViewModel(pexelsRepository) as T
    }

}