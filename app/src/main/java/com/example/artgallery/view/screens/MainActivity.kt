package com.example.artgallery.view.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artgallery.ui.theme.ArtGalleryTheme
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
