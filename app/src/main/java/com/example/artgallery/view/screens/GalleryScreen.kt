package com.example.artgallery.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.artgallery.view.composables.ArtCard
import com.example.artgallery.viewModels.ArtWorkViewModel

@Composable
private fun BuildTopBar() =
    TopAppBar(
        title = { Text("ArtGallery") }
    )

@Composable
fun GalleryScreen(viewModel: ArtWorkViewModel, onClick: (Int) -> Unit) {
    Scaffold(
        topBar = { BuildTopBar() },
    ) { padding ->
        Box(modifier = Modifier.padding(paddingValues = padding)) {
            ArtCardList(viewModel, onClick)
        }
    }
}

@Composable
fun ArtCardList(viewModel: ArtWorkViewModel, onClick: (Int) -> Unit) {
    val lazyArtWorks = viewModel.pager.collectAsLazyPagingItems()
    LazyColumn {
        items(lazyArtWorks.itemCount) { index ->
            lazyArtWorks[index]?.let {
                Box(modifier = Modifier.padding(5.dp)) {
                    ArtCard(
                        artInformation = it.basic,
                        onClick = onClick
                    )
                }
            }
        }

        lazyArtWorks.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { Text("Loading...") }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }

                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = lazyArtWorks.loadState.refresh as LoadState.Error
                    item { Text("Error: ${e.error}") }
                }
                loadState.append is LoadState.Error -> {
                    val e = lazyArtWorks.loadState.append as LoadState.Error
                    item { Text("Error: ${e.error}") }
                }
            }
        }

    }
}