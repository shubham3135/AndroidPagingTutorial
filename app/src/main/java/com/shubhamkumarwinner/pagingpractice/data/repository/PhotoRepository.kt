package com.shubhamkumarwinner.pagingpractice.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.shubhamkumarwinner.pagingpractice.network.PhotoApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(private val photoApi: PhotoApi) {
    fun getSearchResult(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PhotoPagingSource(photoApi, query)}
        ).flow
}