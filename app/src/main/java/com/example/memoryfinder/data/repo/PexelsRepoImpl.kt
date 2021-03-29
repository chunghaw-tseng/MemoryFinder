package com.example.memoryfinder.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.memoryfinder.data.network.PexelsApiService
import com.example.memoryfinder.data.network.PexelsPagingSource

class PexelsRepo(
    private val pexelsApiService: PexelsApiService
) {
    fun getSearchPhoto(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {PexelsPagingSource(pexelsApiService, query)}
        ).liveData

}