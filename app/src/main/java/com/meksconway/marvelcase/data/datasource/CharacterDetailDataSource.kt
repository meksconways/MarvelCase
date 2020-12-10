package com.meksconway.marvelcase.data.datasource

import com.meksconway.marvelcase.data.apiservice.MarvelApiService
import com.meksconway.marvelcase.data.model.BaseResponse
import com.meksconway.marvelcase.data.model.CharacterDetailResponseResult
import com.meksconway.marvelcase.util.QueryParameter
import com.meksconway.marvelcase.util.Resource
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

interface CharacterDetailDataSource {

    suspend fun getCharacterDetail(characterId: Int): Resource<BaseResponse<CharacterDetailResponseResult>>

}

class CharacterDetailDataSourceImpl
@Inject constructor(private val apiService: MarvelApiService) : CharacterDetailDataSource {

    override suspend fun getCharacterDetail(characterId: Int): Resource<BaseResponse<CharacterDetailResponseResult>> {
        return getResult {
            apiService.getCharacterDetail(
                characterId,
                QueryParameter.getQueryMap()
            )
        }
    }

    private suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = call()
            val data = response.body()
            if (response.hasErrors()) {
                Resource.fail("No Fetch")
            } else {
                Resource.success(data)
            }

        } catch (e: HttpException) {
            return Resource.fail(e.message)
        }

    }
}

fun <T> Response<T>.hasErrors(): Boolean {
    return (!this.isSuccessful || this.code() in 400..500 || this.body() == null)
}
