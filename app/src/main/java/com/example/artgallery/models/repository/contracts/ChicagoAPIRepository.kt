package com.example.artgallery.models.repository.contracts

import com.example.artgallery.models.dto.ArtHolder.ArtBasicInformation
import com.example.artgallery.models.dto.ArtHolder.ArtFullInformation

interface ChicagoAPIRepository {
    suspend fun getArtWorksPage(): List<ArtBasicInformation>
    suspend fun getArtDetails(id: Int): ArtFullInformation
    val isAtLastPage: Boolean
}