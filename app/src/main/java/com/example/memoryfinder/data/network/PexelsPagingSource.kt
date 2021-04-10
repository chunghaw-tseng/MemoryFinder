package com.example.memoryfinder.data.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.memoryfinder.data.model.Photo
import com.example.memoryfinder.utils.NoConnectionException
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX : Int  = 1;

class PexelsPagingSource(
    private val pexelsApiService: PexelsApiService,
    private val query: String
) : PagingSource<Int, Photo> (){

    private val TAG : String = "PagingSource"

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: STARTING_PAGE_INDEX
        var photos = emptyList<Photo>()

        return try {
            if (query == "curated"){
                val response = pexelsApiService.getCuratedPhotos(page = position)
                photos = response.photos
            }else {
                val response = pexelsApiService.getPhotos(query, position, params.loadSize)
                photos = response.photos
            }

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: NoConnectionException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}