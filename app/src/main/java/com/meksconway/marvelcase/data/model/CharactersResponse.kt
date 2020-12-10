package com.meksconway.marvelcase.data.model


data class CharactersResponseResults(
    val id: Int?,
    val name: String?,
    val thumbnail: CharactersResponseThumbnail?
)

data class CharactersResponseThumbnail(
    val path: String?,
    val extension: String?
) {

    fun getSmallImage() = "${path}/standard_medium.${extension}"
    fun getBigImage() = "${path}/landscape_incredible.${extension}"

}