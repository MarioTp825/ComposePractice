package com.example.artgallery.view.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artgallery.models.db.ArtObjectBox
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.artgallery.viewModels.ArtWorkViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ArtWorkViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startDataBase()
        setContent {
            ArtGalleryTheme {
                MainScreen(viewModel)
            }
        }
    }

    private fun startDataBase() {
        ArtObjectBox.init(this)
    }

    @Composable
    private fun MainScreen(viewModel: ArtWorkViewModel) {
        val navHostController = rememberNavController()
        Scaffold(
            topBar = { BuildTopBar(navHostController) }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AppNavHost(viewModel, navHostController)
            }
        }
    }

    @Composable
    private fun BuildTopBar(navHostController: NavHostController) {
        TopAppBar(
            title = { Text("ArtGallery") },
            navigationIcon = {
                IconButton(onClick = { navHostController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )
    }
}

@Composable
private fun AppNavHost(viewModel: ArtWorkViewModel, navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = AppScreen.ArtGalleryScreen.route
    ) {
        composable(AppScreen.ArtGalleryScreen.route) {
            GalleryScreen(
                viewModel = viewModel,
            ) { id ->
                navHostController.navigate(AppScreen.ArtDetailScreen.getRoute(id))
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
        //AppNavHost(viewModel, navHostController)
    }
}

class ArtWorkViewModelPreviewProvider : PreviewParameterProvider<ArtWorkViewModel> {

    override val values: Sequence<ArtWorkViewModel>
        get() = sequenceOf(ArtWorkViewModel())

}
