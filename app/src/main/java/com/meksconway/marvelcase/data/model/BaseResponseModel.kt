package com.meksconway.marvelcase.data.model

data class BaseResponse<T>(
    val data: BaseResponseData<T>?
)

data class BaseResponseData<T>(
    val results: List<T>,
    val offset: Int,
    val limit: Int,
    val count: Int
)