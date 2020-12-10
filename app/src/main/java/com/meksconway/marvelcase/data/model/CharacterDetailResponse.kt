package com.meksconway.marvelcase.data.model

data class CharacterDetailResponseResult(
    val id: Int?,
    val name: String?,
    val description: String?,
    val thumbnail: CharactersResponseThumbnail?,
    val comics: Comics?
) {
    fun getDescriptionText(): String {
        return if (description?.trim().isNullOrBlank()) "No Description"
        else description.toString()
    }

}

data class Comics(
    val available: Int,
    val returned: Int,
    val items: List<ComicItem>
)

data class ComicItem(val name: String?)

data class ComicItemWithYear(
    val year: Int,
    val name: String?
)

fun List<ComicItem>.filterByDate(): List<String?> {
    //val pattern = """/\(([^)]+)\)/""".toRegex(RegexOption.LITERAL)
    val arr = mutableListOf<ComicItemWithYear?>()
    this.map {
        val firstIndex = it.name?.indexOf("(") ?: 0
        val lastIndex = it.name?.indexOf(")") ?: 0
        val content = it.name?.substring(firstIndex + 1, lastIndex)?.toIntOrNull()
        when {
            content == null -> {
                arr.add(
                    ComicItemWithYear(
                        year = 10000,
                        name = it.name
                    )
                )
            }
            content > 2005 -> {
                arr.add(
                    ComicItemWithYear(
                        year = content,
                        name = it.name
                    )
                )
            }
            else -> null
        }

    }
    arr.sortByDescending { it?.year }

    return arr.map { it?.name }

}

