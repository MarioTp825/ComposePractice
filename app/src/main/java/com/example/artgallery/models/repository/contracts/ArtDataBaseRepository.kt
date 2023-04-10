package com.example.artgallery.models.repository.contracts

import com.example.artgallery.models.dto.ArtHolder

interface ArtDataBaseRepository {
    fun addToFavorites(art: ArtHolder.ArtFullInformation)
    fun removeFromFavorites(art: ArtHolder.ArtFullInformation)
    fun getAllFavorites(): List<ArtHolder.ArtBasicInformation>
    fun getFavorite(id: Int): ArtHolder.ArtFullInformation?
}