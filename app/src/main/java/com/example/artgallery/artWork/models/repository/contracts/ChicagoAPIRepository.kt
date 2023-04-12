package com.example.artgallery.artWork.models.repository.contracts

import com.example.artgallery.artWork.models.dto.ArtHolder.ArtFullInformation

interface ChicagoAPIRepository {
    suspend fun getArtWorksPage(): List<ArtFullInformation>
    suspend fun getArtDetails(id: Int): ArtFullInformation
    suspend fun search(query: String): List<ArtFullInformation>
    fun clear()

    val isAtLastPage: Boolean
}