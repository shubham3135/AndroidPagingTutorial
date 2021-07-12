package com.shubhamkumarwinner.pagingpractice.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.shubhamkumarwinner.pagingpractice.data.Photo
import com.shubhamkumarwinner.pagingpractice.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: PhotoRepository): ViewModel() {

    init {
        getPhoto(DEFAULT_QUERY)
    }

    fun getPhoto(query: String): Flow<PagingData<Photo>> {
        return repository.getSearchResult(query)
            .cachedIn(viewModelScope)
    }
    companion object{
        private const val DEFAULT_QUERY = "cats"
    }
}