package com.example.artgallery.artWork.models.repository.contracts

import com.example.artgallery.artWork.models.dto.ArtHolder

interface ArtDataBaseRepository {
    fun addToFavorites(art: ArtHolder.ArtFullInformation)
    fun removeFromFavorites(art: ArtHolder.ArtFullInformation)
    fun getAllFavorites(): List<ArtHolder.ArtBasicInformation>
    fun getFavorite(id: Int): ArtHolder.ArtFullInformation?
}