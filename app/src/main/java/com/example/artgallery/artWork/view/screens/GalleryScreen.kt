package com.example.artgallery.artWork.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import com.example.artgallery.generics.view.composables.SearchBox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artgallery.artWork.models.dto.ArtHolder
import com.example.artgallery.artWork.models.dto.BasicInformationWrapper
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.artgallery.artWork.view.composables.ArtCard
import com.example.artgallery.generics.view.composables.SearchListener
import com.example.artgallery.artWork.view.contracts.ArtCardListContract
import com.example.artgallery.artWork.view.implementations.buildListener
import com.example.artgallery.generics.view.composables.loadingStates
import com.example.artgallery.artWork.viewModels.ArtWorkViewModel
import com.example.artgallery.fakes.FakeDTO
import com.example.artgallery.fakes.FakeInterfaces
import kotlinx.coroutines.Dispatchers

@Composable
fun GalleryScreen(viewModel: ArtWorkViewModel, onClick: (Int) -> Unit) {
    val query: String? by viewModel.queryFlow.collectAsState(initial = viewModel.query)
    val lazyArtWorks by viewModel
        .basicInformationState
        .collectAsState(ArtHolder.fromBasicList())
    GalleryLayout(
        query = query,
        lazyArtWorks = lazyArtWorks,
        artCardListener = buildListener(viewModel, onClick)
    ) {
        viewModel.loadSearchPage(it)
    }

}

@Composable
private fun GalleryLayout(
    query: String?,
    lazyArtWorks: BasicInformationWrapper,
    artCardListener: ArtCardListContract,
    searchListener: SearchListener,
) {
    Column {
        SearchBar(query, searchListener)
        ArtCardList(
            listener = artCardListener,
            lazyArtWorks = lazyArtWorks,
        )
    }
}

@Composable
private fun SearchBar(
    query: String?,
    searchListener: SearchListener
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.primaryVariant)
            .shadow(20.dp)
            .padding(10.dp)

    ) {
        SearchBox(
            startValue = query.orEmpty(),
            modifier = Modifier.fillMaxWidth(),
            search = searchListener
        )
    }
}

@Composable
fun ArtCardList(
    listener: ArtCardListContract,
    lazyArtWorks: BasicInformationWrapper
) {
    LoadPage { listener.loadPage() }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
    ) {

        itemsIndexed(
            items = lazyArtWorks.artData,
            key = { index, _ -> index }
        ) { index, item ->
            ArtItem(listener, item)
            if (listener.isAtLastIndex(index)) {
                LoadPage { listener.loadPage() }
            }
        }

        loadingStates(lazyArtWorks.state)
    }
}

@Composable
private fun ArtItem(
    listener: ArtCardListContract,
    item: ArtHolder.ArtFullInformation
) {
    ArtCard(
        likeFlow = { listener.favoriteFlow(it) },
        art = item,
        onClick = { listener.onCardClick(id = it) },
        showLikeButton = !listener.isSearch,
        onFavoriteTap = {
            listener.changeFavoriteState(
                isRemovingLike = it,
                art = item
            )
        }
    )
}

@Composable
private fun LoadPage(service: suspend () -> Unit) {
    LaunchedEffect(Unit, Dispatchers.IO) {
        service()
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryScreenPreview() {
    ArtGalleryTheme {
        GalleryLayout(
            query = "Search",
            lazyArtWorks = BasicInformationWrapper(
                artData = FakeDTO
                    .generateArtFullInfoList()
                    .toMutableList()
            ),
            artCardListener = FakeInterfaces.buildArtCardListContract(
                isSearch = false
            )
        ) {
            //Nothing
        }
    }
}