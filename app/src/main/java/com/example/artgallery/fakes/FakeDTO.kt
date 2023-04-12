package com.example.artgallery.fakes

import com.example.artgallery.artWork.models.dto.ArtHolder

object FakeDTO {
    fun generateArtFullInfoList(): List<ArtHolder.ArtFullInformation> = listOf(
        baseArt(id = 1),
        baseArt(id = 2),
        baseArt(id = 3),
        baseArt(id = 4),
        baseArt(id = 5),
        baseArt(id = 6),
        baseArt(id = 7),
        baseArt(id = 8),
        baseArt(id = 9),
        baseArt(id = 10),
    )

    private fun baseArt(id: Int = 1) = ArtHolder.ArtFullInformation(
        id = id,
        title = "Title",
        author = "Author",
        lastUpdate = null,
        desc = null,
        additionalData = mapOf(),
        chips = mapOf(),
        imageId = "",
        altText = null,
    )
}