package com.example.artgallery.artWork.models.repository.implementation

import com.example.artgallery.artWork.models.dto.ArtHolder
import com.example.artgallery.artWork.models.dto.database.ArtFullInformationEntity
import com.example.artgallery.artWork.models.repository.contracts.ArtDataBaseRepository
import io.objectbox.Box

class ArtDataBaseRepositoryImpl(
    private val artBox: Box<ArtFullInformationEntity>
) : ArtDataBaseRepository {
    override fun addToFavorites(art: ArtHolder.ArtFullInformation) {
        artBox.put(
            ArtFullInformationEntity(
                art = art
            )
        )
    }

    override fun getFavorite(id: Int): ArtHolder.ArtFullInformation? =
        findFavorite(id)?.art

    override fun removeFromFavorites(art: ArtHolder.ArtFullInformation) {
        findFavorite(art.id)?.let {
            artBox.remove(it.id)
        }?:throw Exception("Art Does Not exist")
    }

    override fun getAllFavorites() = artBox.all.map { it.art.basic }

    private fun findFavorite(id: Int) = artBox.all.firstOrNull { it.art.id == id }
}