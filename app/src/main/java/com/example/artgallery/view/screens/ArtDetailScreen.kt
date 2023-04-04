package com.example.artgallery.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.artgallery.models.dto.ArtWrapper
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.artgallery.viewModels.ArtWorkViewModel
import java.util.*

typealias OnBackClick = () -> Unit

@Composable
fun ArtDetailScreen(viewModel: ArtWorkViewModel, id: Int?, click: OnBackClick) {
    val artWork = viewModel.findArtById(id)
    Scaffold(
        topBar = { BuildTopBar() }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (artWork == null) {
                ErrorMessage()
                return@Box
            }
            ArtDetails(artWork, click)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ArtDetails(artWork: ArtWrapper.ArtFullInformation, click: OnBackClick) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = rememberImagePainter(artWork.imageUrl),
            contentDescription = artWork.altText,
            modifier = Modifier
                .size(230.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = artWork.title,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                fontStyle = FontStyle.Normal,
                color = Color.Black
            )
        )
        artWork.additionalData.entries.filter { it.value.isNotBlank() }.map {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${it.key}:",
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W500,
                        fontStyle = FontStyle.Normal,
                        color = Color.Black
                    )
                )
                Text(
                    text = it.value,
                    textAlign = TextAlign.End,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        fontStyle = FontStyle.Normal,
                        color = Color.Black
                    )
                )
            }
        }

        Button(onClick = { click() }, shape = RoundedCornerShape(25.dp)) {
            Text(text = "Regresar")
        }

    }
}

@Composable
private fun ErrorMessage() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "There was a problem finding the information for this work")
    }
}

@Composable
private fun BuildTopBar() {
    TopAppBar(
        title = { Text("ArtGallery") }
    )
}

@Preview(showBackground = true)
@Composable
fun ArtDetailsPreview() {
    ArtGalleryTheme {
        ArtDetails(
            artWork = ArtWrapper.ArtFullInformation(
                id = 4,
                title = "Art Title",
                author = "Author",
                altText = "Data",
                imageId = "",
                lastUpdate = Calendar.getInstance(),
                additionalData = mapOf(
                    "data one" to "data two",
                    "data tasdf" to "data two",
                    "data osdfasdne" to "data two",
                    "data fadf" to "data two",
                )
            ),
            click = { }
        )
    }
}
