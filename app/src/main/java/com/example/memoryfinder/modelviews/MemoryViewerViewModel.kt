package com.example.memoryfinder.modelviews

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.memoryfinder.data.provider.SearchProvider
import com.example.memoryfinder.data.repo.PexelsRepository
import com.example.memoryfinder.utils.lazyDeferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MemoryViewerViewModel(
    private val pexelsRepository: PexelsRepository,
) : ViewModel() {

    private var searchWord : String = ""
    private var page : Int = 1
    private var TAG : String = "ViewModel"


//    fun nextPage(){
//        page += 1
//        Log.d(TAG, "Next Page")
//        GlobalScope.launch {
//            pexelsRepository.getCurrentImages(searchWord, page)
//        }
//    }
//
//    fun searchPhotos(keyword : String){
//        searchWord = keyword
//        page = 1
//        GlobalScope.launch {
//            pexelsRepository.getCurrentImages(searchWord, page)
//        }
//    }
//
//    val images by lazyDeferred {
//        pexelsRepository.getCurrentImages(searchWord, page)
//    }



}