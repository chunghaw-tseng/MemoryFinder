package com.example.memoryfinder.data.provider

import com.example.memoryfinder.data.model.Photo

interface SearchProvider {
    fun setSearchPhotos(keyword : String)
    fun setStartPage()
    fun setNextPage()
    fun getSearchKeyword() : String
    fun getLastPage() : Int
}