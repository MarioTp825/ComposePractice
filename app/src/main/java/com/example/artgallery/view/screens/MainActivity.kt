package com.example.artgallery.view.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.artgallery.ui.theme.ArtGalleryTheme
import kotlinx.coroutines.MainScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtGalleryTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    Scaffold(
        topBar = { BuildTopBar() },
    ) { padding ->
        Greeting("Android", padding)
    }
}

@Composable
private fun BuildTopBar() =
    TopAppBar(
        title = { Text("ArtGallery") }
    )

@Composable
private fun Greeting(name: String, padding: PaddingValues) {
    Text(text = "Hello $name!", modifier = Modifier.padding(padding))
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArtGalleryTheme {
        MainScreen()
    }
}