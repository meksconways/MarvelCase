package com.meksconway.marvelcase.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.meksconway.marvelcase.data.apiservice.MarvelApiService
import com.meksconway.marvelcase.data.datasource.CharactersDataSource
import com.meksconway.marvelcase.data.model.CharactersResponseResults
import kotlinx.coroutines.flow.Flow

class CharactersViewModel
@ViewModelInject constructor(
    apiService: MarvelApiService
) : ViewModel() {

    val characters: Flow<PagingData<CharactersResponseResults>> =
        Pager(PagingConfig(pageSize = 10)) {
            CharactersDataSource(apiService)
        }.flow.cachedIn(viewModelScope)

}