package com.example.artgallery.view.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.artgallery.models.dto.ArtHolder
import com.example.artgallery.view.composables.ArtCard
import com.example.artgallery.viewModels.ArtWorkViewModel
import kotlinx.coroutines.Dispatchers

@Composable
fun GalleryScreen(viewModel: ArtWorkViewModel, onClick: (Int) -> Unit) {
    ArtCardList(viewModel, onClick)
}

@Composable
fun ArtCardList(viewModel: ArtWorkViewModel, onClick: (Int) -> Unit) {
    val lazyArtWorks by viewModel.basicInformationState.collectAsState(ArtHolder.fromBasicList())
    LaunchedEffect(Unit, Dispatchers.IO) {
        viewModel.loadPage()
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
    ) {

        itemsIndexed(
            items =lazyArtWorks.artData,
            key = { index, item -> index }
        ) { index, item ->
            ArtCard(
                artInformation = item,
                onClick = onClick
            )
            if(index == viewModel.lastIndex()) {
                LaunchedEffect(Unit, Dispatchers.IO) {
                    viewModel.loadPage()
                }
            }
        }

        lazyArtWorks.apply {
            if (state is ArtHolder.ArtState.Error)
                item { Text("Error: ${state.msg}") }

            if (state is ArtHolder.ArtState.InitialLoading)
                item { Text("Loading...") }

            if (state is ArtHolder.ArtState.Loading)
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                }
        }

    }
}

fun currentTime(): String = System.currentTimeMillis().toString()
