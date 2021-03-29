package com.example.memoryfinder.data.network

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.memoryfinder.data.model.Photo
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX : Int  = 1;

class PexelsPagingSource(
    private val pexelsApiService: PexelsApiService,
    private val query: String
) : PagingSource<Int, Photo> (){


    private val TAG : String = "PagingSource"


    // TODO Error with network

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val position = params.key ?: STARTING_PAGE_INDEX
        var photos = emptyList<Photo>()

        return try {
            if (query == "curated"){
                Log.d(TAG, "Loading curated images $position")
                val response = pexelsApiService.getCuratedPhotos(page = position)
                photos = response.photos
            }else {
                // TODO this keeps getting called
                Log.d(TAG, "Loading images $position")
                val response = pexelsApiService.getPhotos(query, position, params.loadSize)
                photos = response.photos
            }

            LoadResult.Page(
                data = photos,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            Log.e(TAG, "IOERROR")
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.e(TAG, "HTTPERROR")
            LoadResult.Error(exception)
        }
    }
}