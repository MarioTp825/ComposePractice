package com.example.artgallery.artWork.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import coil.compose.rememberImagePainter
import com.example.artgallery.artWork.models.dto.ArtHolder
import com.example.artgallery.artWork.models.dto.ArtHolder.ArtFullInformation
import com.example.artgallery.artWork.models.dto.FullInformationWrapper
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.artgallery.generics.model.ContentOrientation
import com.example.artgallery.generics.view.composables.ContentSection
import com.example.artgallery.artWork.viewModels.ArtWorkViewModel
import kotlinx.coroutines.Dispatchers
import java.util.*

typealias OnBackClick = () -> Unit

@Composable
fun ArtDetailScreen(viewModel: ArtWorkViewModel, id: Int?, click: OnBackClick) {
    val artWork by viewModel.artDetailState.collectAsState(ArtHolder.fromFullInformation())

    LaunchedEffect(Unit, Dispatchers.IO) { viewModel.findArtById(id) }

    when (artWork.state) {
        is ArtHolder.ArtState.Done -> ShowArtWorkDetails(
            artWork = artWork,
            click = click
        ) {
            viewModel.changeFavorites(!it, artWork.artData)
            viewModel.updateListFavorite(!it, artWork.artData?.id)
        }
        is ArtHolder.ArtState.Error -> ErrorMessage(artWork.state.msg)
        is ArtHolder.ArtState.InitialLoading,
        is ArtHolder.ArtState.Loading -> LoadingDetails()
    }
}

@Composable
private fun ShowArtWorkDetails(
    artWork: FullInformationWrapper,
    click: OnBackClick,
    onFavoriteClick: (Boolean) -> Unit
) {
    if (artWork.artData == null) {
        ErrorMessage()
    } else {
        ArtDetails(artWork.artData!!, click, onFavoriteClick)
    }
}

@Composable
fun LoadingDetails() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ArtDetails(
    artWork: ArtFullInformation,
    click: OnBackClick,
    onFavoriteClick: (Boolean) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        ArtDetailHeader(artWork, onFavoriteClick)
        ArtDetailAdditionalData(artWork)
        ArtDetailFooter(click)
    }
}

@Composable
private fun ArtDetailFooter(click: OnBackClick) {
    Button(onClick = click, shape = RoundedCornerShape(25.dp)) {
        Text(text = "Done")
    }
}

@Composable
private fun ArtDetailAdditionalData(artWork: ArtFullInformation) {
    artWork.additionalData.entries
        .filter { it.value.isNotBlank() }
        .map { AdditionalDataEntry(it) }
}

@Composable
private fun ArtDetailHeader(artWork: ArtFullInformation, onClick: (Boolean) -> Unit) {
    ArtWorkImageHeader(artWork, onClick)
    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        Text(
            text = artWork.title,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.W500,
                fontStyle = FontStyle.Normal,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        artWork.desc?.let {
            ArtWorkDescription(it)
        }
        artWork.chips
            .takeIf { it.isNotEmpty() }
            ?.let { ArtWorkChipContent(it) }
    }
}

@Composable
fun ArtWorkChipContent(content: Map<String, List<String>>) {
    for (section in content) {
        ContentSection(
            orientation = ContentOrientation.VERTICAL,
            title = section.key,
            modifier = Modifier.padding(vertical = 5.dp)
        ) {
            ChipGroup(chips = section.value)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun ChipGroup(chips: List<String>) {
    FlowRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        for (chip in chips) {
            Chip(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(horizontal = 5.dp),
                colors = ChipDefaults.chipColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant
                )
            ) {
                Text(text = chip, color = Color.White)
            }
        }
    }
}

@Composable
fun ArtWorkDescription(desc: String) {
    ContentSection(
        orientation = ContentOrientation.VERTICAL,
        title = "Description"
    ) {
        Text(
            text = desc,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.body2,
            modifier = it
                .fillMaxWidth(),
        )
    }
}

@Composable
private fun ArtWorkImageHeader(artWork: ArtFullInformation, onClick: (Boolean) -> Unit) {
    var isChecked by rememberSaveable {
        mutableStateOf(artWork.favorite)
    }

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        ArtWorkImage(artWork)
        FloatingActionButton(
            onClick = {
                isChecked = !isChecked
                onClick(isChecked)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(top = 200.dp, end = 20.dp),
            backgroundColor = MaterialTheme.colors.primaryVariant
        ) {
            Icon(
                imageVector = if (isChecked) Icons.Filled.Favorite
                else Icons.Filled.FavoriteBorder,
                contentDescription = "Add to favorites"
            )
        }
    }
}

@Composable
private fun ArtWorkImage(artWork: ArtFullInformation) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberImagePainter(artWork.imageUrl),
            contentDescription = artWork.altText,
            modifier = Modifier
                .height(230.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Inside
        )
    }
}

@Composable
private fun AdditionalDataEntry(data: Map.Entry<String, String>) {
    ContentSection(
        orientation = ContentOrientation.HORIZONTAL,
        title = data.key,
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = data.value,
            textAlign = TextAlign.Start,
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                fontStyle = FontStyle.Normal,
                color = Color.Black
            ),
            modifier = it
        )
    }
}

@Composable
private fun ErrorMessage(message: String? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = message ?: "There was a problem finding the information for this work")
    }
}

@Preview(showBackground = true)
@Composable
fun ArtDetailsPreview() {
    ArtGalleryTheme {
        ArtDetails(
            artWork = ArtFullInformation(
                id = 4,
                title = "Art Title",
                author = "Author",
                altText = "Data",
                imageId = "wdqfw",
                lastUpdate = Calendar.getInstance(),
                desc = "Quia ex ipsa dolor et.Aliquam in reprehenderit ratione quam est qui omnis . Quod aliquid et fugit iste sit sint non.Voluptas atque id laborum rerum commodi .",
                chips = mapOf(
                    "Data One" to listOf(
                        "Value One",
                        "Value Two",
                        "Three",
                        "Four",
                        "Five",
                        "Six",
                        "Seven"
                    ),
                    "Data Two" to listOf("Value One", "Value Two"),
                ),
                additionalData = mapOf(
                    "data one" to "data two",
                    "data tasdf" to "data two",
                    "data osdfasdne" to "data two",
                    "data fadf" to "data two",
                )
            ),
            click = { },
            onFavoriteClick = { }
        )
    }
}
