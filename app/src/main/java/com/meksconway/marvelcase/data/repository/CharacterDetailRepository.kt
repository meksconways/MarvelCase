package com.meksconway.marvelcase.data.repository

import com.meksconway.marvelcase.data.datasource.CharacterDetailDataSource
import com.meksconway.marvelcase.data.model.filterByDate
import com.meksconway.marvelcase.util.Resource
import com.meksconway.marvelcase.util.Status.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

enum class CharacterDetailItemType {
    IMAGE_HEADER, TITLE_HEADER, DESC, COMIC
}

sealed class CharacterDetailUiModel(val type: CharacterDetailItemType) {
    data class ImageHeaderItem(val imageUrl: String?, val name: String?) : CharacterDetailUiModel(
        CharacterDetailItemType.IMAGE_HEADER
    )

    data class TitleHeaderItem(val title: String) :
        CharacterDetailUiModel(CharacterDetailItemType.TITLE_HEADER)

    data class DescriptionItem(val desc: String) :
        CharacterDetailUiModel(CharacterDetailItemType.DESC)

    data class ComicItem(val comicName: String?) :
        CharacterDetailUiModel(CharacterDetailItemType.COMIC)
}


interface CharacterDetailRepository {

    suspend fun getCharacterDetail(characterId: Int):
            Flow<Resource<List<CharacterDetailUiModel>>>

}

class CharacterDetailRepositoryImpl @Inject constructor(
    private val dataSource: CharacterDetailDataSource
) : CharacterDetailRepository {

    override suspend fun getCharacterDetail(characterId: Int): Flow<Resource<List<CharacterDetailUiModel>>> {
        return resultFlow { dataSource.getCharacterDetail(characterId) }.mapNotNull {
            when (it.status) {
                SUCCESS -> {
                    it.data?.data?.results?.get(0)?.let {

                        val characterData = mutableListOf<CharacterDetailUiModel>().apply {

                            add(
                                CharacterDetailUiModel.ImageHeaderItem(
                                    imageUrl = it.thumbnail?.getBigImage(),
                                    name = it.name
                                )
                            )

                            add(
                                CharacterDetailUiModel.TitleHeaderItem(
                                    title = "Description"
                                )
                            )

                            add(
                                CharacterDetailUiModel.DescriptionItem(
                                    desc = it.getDescriptionText()
                                )
                            )

                            add(
                                CharacterDetailUiModel.TitleHeaderItem(
                                    title = "Comics"
                                )
                            )

                            it.comics?.items?.filterByDate()?.map { name ->
                                add(
                                    CharacterDetailUiModel.ComicItem(
                                        comicName = name
                                    )
                                )
                            }


                        }

                        Resource.success(characterData)


                    } ?: Resource.fail(it.errorMessage)
                }
                FAIL -> {
                    Resource.fail(it.errorMessage)
                }
                LOADING -> {
                    Resource.loading()
                }
            }
        }
    }

}


fun <T> resultFlow(networkCall: suspend () -> Resource<T>): Flow<Resource<T>> {
    return flow {
        emit(networkCall.invoke())
    }.onStart {
        emit(Resource.loading())
    }.flowOn(Dispatchers.IO)
}