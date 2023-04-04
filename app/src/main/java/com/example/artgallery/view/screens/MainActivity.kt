package com.example.artgallery.view.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.artgallery.view.composables.ArtCard
import com.example.artgallery.viewModels.ArtWorkViewModel

//import io.mockk.mockk

class MainActivity : ComponentActivity() {
    private val viewModel: ArtWorkViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtGalleryTheme {
                AppNavHost(viewModel)
            }
        }
    }
}

@Composable
private fun AppNavHost(viewModel: ArtWorkViewModel) {
    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        startDestination = AppScreen.ArtGalleryScreen.route
    ) {
        composable(AppScreen.ArtGalleryScreen.route) {
            GalleryScreen(viewModel = viewModel) {
                navHostController.navigate(AppScreen.ArtDetailScreen.getRoute(it))
            }
        }
        composable(AppScreen.ArtDetailScreen.route) {
            ArtDetailScreen(
                viewModel = viewModel,
                id = it.arguments?.getString("id")?.toIntOrNull()
            ) {
                navHostController.popBackStack()
            }
        }
    }
}

@Composable
private fun BuildTopBar() =
    TopAppBar(
        title = { Text("ArtGallery") }
    )

@Composable
private fun GalleryScreen(viewModel: ArtWorkViewModel, onClick: (Int) -> Unit) {
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview(@PreviewParameter(ArtWorkViewModelPreviewProvider::class) viewModel: ArtWorkViewModel) {
    ArtGalleryTheme {
        AppNavHost(viewModel)
    }
}

class ArtWorkViewModelPreviewProvider : PreviewParameterProvider<ArtWorkViewModel> {
//    private val viewModelMock = mockk<ArtWorkViewModel>()

    override val values: Sequence<ArtWorkViewModel>
        get() = sequenceOf(ArtWorkViewModel())

}