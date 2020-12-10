package com.meksconway.marvelcase.data.datasource

import androidx.paging.PagingSource
import com.meksconway.marvelcase.data.apiservice.MarvelApiService
import com.meksconway.marvelcase.data.model.CharactersResponseResults
import com.meksconway.marvelcase.util.QueryParameter
import retrofit2.HttpException

class CharactersDataSource(
    private val apiService: MarvelApiService
) : PagingSource<Int, CharactersResponseResults>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersResponseResults> {

        try {

            val nextPage = params.key ?: 0
            val nextPageWithOffset = ((params.key ?: 0) * QueryParameter.LIMIT)
            val queryMap = QueryParameter.getQueryMap()
            queryMap["offset"] = nextPageWithOffset
            queryMap["limit"] = QueryParameter.LIMIT
            val response = apiService.getCharacters(queryMap)

            if (!response.isSuccessful || response.code() in 400..500) {
                return LoadResult.Error(HttpException(response))
            }

            val results = response.body()?.data?.results
            results?.let {
                return LoadResult.Page(
                    data = it,
                    prevKey = if (nextPage == 0) null else nextPage - 1,
                    nextKey = if (response.body()!!.data!!.count == 0) null else nextPage + 1
                )
            } ?: kotlin.run {
                return LoadResult.Error(HttpException(response))
            }


        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }

    }

}