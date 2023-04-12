package com.example.artgallery.artWork.view.contracts

import com.example.artgallery.artWork.models.dto.ArtHolder.ArtFullInformation
import kotlinx.coroutines.flow.Flow

interface ArtCardListContract {
    val isSearch: Boolean

    fun favoriteFlow(id: Int): Flow<Boolean>
    fun changeFavoriteState(isRemovingLike: Boolean, art: ArtFullInformation?)
    fun isAtLastIndex(index: Int): Boolean
    fun onCardClick(id: Int)

    suspend fun loadPage()

}