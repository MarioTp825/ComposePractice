package com.example.artgallery.artWork.view.implementations

import com.example.artgallery.artWork.models.dto.ArtHolder
import com.example.artgallery.artWork.view.contracts.ArtCardListContract
import com.example.artgallery.artWork.viewModels.ArtWorkViewModel
import kotlinx.coroutines.flow.Flow

fun buildListener(
    viewModel: ArtWorkViewModel,
    onClick: (Int) -> Unit
) = object : ArtCardListContract {
    override val isSearch: Boolean
        get() = viewModel.isSearch

    override fun favoriteFlow(id: Int): Flow<Boolean> = viewModel.favoriteState(id)

    override fun changeFavoriteState(
        isRemovingLike: Boolean,
        art: ArtHolder.ArtFullInformation?
    ) {
        viewModel.changeFavorites(
            remove = isRemovingLike,
            art = art
        )
    }

    override fun isAtLastIndex(index: Int) = viewModel.lastIndex() == index

    override fun onCardClick(id: Int) {
        onClick(id)
    }

    override suspend fun loadPage() {
        viewModel.loadPage()
    }

}

