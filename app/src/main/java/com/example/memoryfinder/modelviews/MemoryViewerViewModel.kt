package com.example.memoryfinder.modelviews

import androidx.lifecycle.ViewModel
import com.example.memoryfinder.data.repo.PexelsRepository
import com.example.memoryfinder.utils.lazyDeferred

class MemoryViewerViewModel(
    private val pexelsRepository: PexelsRepository
) : ViewModel() {
    // Find a method to get the text input by user

    val images by lazyDeferred {
        pexelsRepository.getCurrentImages("", "1")
    }
}