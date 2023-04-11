package com.example.artgallery.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import com.example.artgallery.view.composables.SearchBox
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artgallery.models.db.ArtObjectBox
import com.example.artgallery.models.dto.ArtHolder
import com.example.artgallery.models.dto.BasicInformationWrapper
import com.example.artgallery.models.repository.implementation.ChicagoAPIRepositoryImpl
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.artgallery.view.composables.ArtCard
import com.example.artgallery.view.composables.loadingStates
import com.example.artgallery.viewModels.ArtWorkViewModel
import kotlinx.coroutines.Dispatchers

@Composable
fun GalleryScreen(viewModel: ArtWorkViewModel, onClick: (Int) -> Unit) {
    val query: String? by viewModel.queryFlow.collectAsState(initial = viewModel.query)
    val lazyArtWorks by viewModel
        .basicInformationState
        .collectAsState(ArtHolder.fromBasicList())
    Column {
        SearchBox(
            startValue = query.orEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            viewModel.loadSearchPage(it)
        }
        ArtCardList(viewModel, lazyArtWorks, onClick)
    }

}

@Composable
fun ArtCardList(
    viewModel: ArtWorkViewModel,
    lazyArtWorks: BasicInformationWrapper,
    onClick: (Int) -> Unit
) {
    LoadPage { viewModel.loadPage() }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
    ) {

        itemsIndexed(
            items = lazyArtWorks.artData,
            key = { index, _ -> index }
        ) { index, item ->
            ArtCard(
                likeFlow = { viewModel.favoriteState(it) },
                art = item,
                onClick = onClick,
                showLikeButton = !viewModel.isSearch,
                onFavoriteTap = { remove -> viewModel.changeFavorites(remove, item) }
            )
            if (index == viewModel.lastIndex()) {
                LoadPage { viewModel.loadPage() }
            }
        }

        loadingStates(lazyArtWorks.state)
    }
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
    ArtObjectBox.init(LocalContext.current)
    ArtGalleryTheme {
//        ArtCardList(
//            lazyArtWorks = BasicInformationWrapper(
//                artData = mutableListOf(
//                    ArtHolder.ArtFullInformation(
//                        id = 1,
//                        title = "Title",
//                        author = "Author",
//                        lastUpdate = null,
//                        desc = null,
//                        additionalData = mapOf(),
//                        chips = mapOf(),
//                        imageId = "das",
//                        altText = null,
//                    )
//                )
//            ),
//            onClick = {
//
//            }
//        )
    }
}
