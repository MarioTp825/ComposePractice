package com.example.artgallery.artWork.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.artgallery.artWork.models.api.ChicagoAPIService
import com.example.artgallery.artWork.models.db.ArtObjectBox
import com.example.artgallery.artWork.models.dto.database.ArtFullInformationEntity
import com.example.artgallery.artWork.models.repository.implementation.ArtDataBaseRepositoryImpl
import com.example.artgallery.artWork.models.repository.implementation.ChicagoAPIRepositoryImpl
import com.example.artgallery.artWork.view.screens.AppScreen
import com.example.artgallery.artWork.view.screens.ArtDetailScreen
import com.example.artgallery.artWork.view.screens.GalleryScreen
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.artgallery.utils.NetworkConstants
import com.example.artgallery.artWork.viewModels.ArtWorkViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startDataBase()
        setContent {
            ArtGalleryTheme {
                MainScreen()
            }
        }
    }

    private fun startDataBase() {
        ArtObjectBox.init(this)
    }

    @Composable
    private fun MainScreen() {
        val viewModel = viewModel { buildViewModel() }
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

    private fun buildViewModel() = ArtWorkViewModel(
        repository = ChicagoAPIRepositoryImpl(
            api = buildChicagoService()
        ),
        dataBase = ArtDataBaseRepositoryImpl(
            artBox = ArtObjectBox.boxFor(ArtFullInformationEntity::class.java)
        )
    )

    private fun buildChicagoService() = Retrofit.Builder()
        .baseUrl(NetworkConstants.GET_ARTWORK_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ChicagoAPIService::class.java)

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
fun DefaultPreview() {
    ArtGalleryTheme {
        val nav = rememberNavController()
//        AppNavHost(ArtWorkViewModel(
//            repository = C
//        ), nav)
    }
}
