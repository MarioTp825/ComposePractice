package com.example.artgallery.view.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.artgallery.models.dto.ArtHolder

fun LazyListScope.loadingStates(
    state: ArtHolder.ArtState,
) {
    if (state is ArtHolder.ArtState.Error)
        item { Text("Error: ${state.msg}") }

    if (state is ArtHolder.ArtState.InitialLoading)
        item { InitialLoadingView() }

    if (state is ArtHolder.ArtState.Loading)
        item { FetchLoadingView() }
}

@Composable
private fun FetchLoadingView() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun LazyItemScope.InitialLoadingView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillParentMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(10.dp))
        Text("Loading...")
    }
}