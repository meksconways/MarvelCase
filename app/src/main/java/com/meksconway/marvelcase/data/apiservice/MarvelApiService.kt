package com.meksconway.marvelcase.data.apiservice

import com.meksconway.marvelcase.data.model.BaseResponse
import com.meksconway.marvelcase.data.model.CharacterDetailResponseResult
import com.meksconway.marvelcase.data.model.CharactersResponseResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface MarvelApiService {


    @GET("characters")
    suspend fun getCharacters(@QueryMap options: Map<String, @JvmSuppressWildcards Any>):
            Response<BaseResponse<CharactersResponseResults>>

    @GET("characters/{characterId}")
    suspend fun getCharacterDetail(
        @Path("characterId") characterId: Int,
        @QueryMap options: Map<String, @JvmSuppressWildcards Any>
    ): Response<BaseResponse<CharacterDetailResponseResult>>



}