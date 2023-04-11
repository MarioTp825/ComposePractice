package com.example.artgallery.models.repository.contracts

import com.example.artgallery.models.dto.ArtHolder.ArtBasicInformation
import com.example.artgallery.models.dto.ArtHolder.ArtFullInformation

interface ChicagoAPIRepository {
    suspend fun getArtWorksPage(): List<ArtFullInformation>
    suspend fun getArtDetails(id: Int): ArtFullInformation
    suspend fun search(query: String): List<ArtFullInformation>
    fun clear()

    val isAtLastPage: Boolean
}