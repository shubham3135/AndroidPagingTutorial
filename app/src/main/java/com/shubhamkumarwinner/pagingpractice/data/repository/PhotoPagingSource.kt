package com.shubhamkumarwinner.pagingpractice.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shubhamkumarwinner.pagingpractice.data.Photo
import com.shubhamkumarwinner.pagingpractice.network.PhotoApi
import retrofit2.HttpException
import java.io.IOException

class PhotoPagingSource(private val photoApi: PhotoApi, private val query: String):
    PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = photoApi.searchPhotos(query, nextPageNumber, params.loadSize)
            LoadResult.Page(
                data = response.results,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1,
                nextKey = if (response.results.isEmpty()) null else nextPageNumber + 1
            )
        }catch (e: IOException) {
            // IOException for network failures.
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            LoadResult.Error(e)
        }
    }
}