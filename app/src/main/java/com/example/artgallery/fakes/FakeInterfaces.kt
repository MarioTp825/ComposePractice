package com.example.artgallery.fakes

import com.example.artgallery.artWork.models.dto.ArtHolder
import com.example.artgallery.artWork.view.contracts.ArtCardListContract
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.seconds

object FakeInterfaces {
    fun buildArtCardListContract(isSearch: Boolean): ArtCardListContract = object : ArtCardListContract {
        override val isSearch: Boolean
            get() = isSearch

        override fun favoriteFlow(id: Int): Flow<Boolean> = flow {
            emit(false)
        }

        override fun changeFavoriteState(
            isRemovingLike: Boolean,
            art: ArtHolder.ArtFullInformation?
        ) {
            //Nothing
        }

        override fun isAtLastIndex(index: Int): Boolean = true

        override fun onCardClick(id: Int) {
            //Nothing
        }

        override suspend fun loadPage() {
            delay(1.seconds)
        }

    }
}