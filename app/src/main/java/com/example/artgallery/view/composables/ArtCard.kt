package com.example.artgallery.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.artgallery.models.dto.ArtBasicInformation
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.artgallery.R

typealias OnClick = (Int) -> Unit

@Composable
fun ArtCard(artInformation: ArtBasicInformation, onClick: OnClick) {
    Surface(
        color = Color.White,
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black, shape = RoundedCornerShape(17.dp))
            .clickable {
                onClick(artInformation.id)
            },
        shape = RoundedCornerShape(
            dimensionResource(id = R.dimen.card_corner_radius)
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ArtImage(artInformation)
            Spacer(modifier = Modifier.width(16.dp))
            ArtInformation(artInformation)
        }

    }
}

@Composable
private fun ArtInformation(artInformation: ArtBasicInformation) {
    Column {
        ArtTitle(artInformation)
        AuthorName(artInformation)
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun ArtImage(artInformation: ArtBasicInformation) {
    Image(
        painter = rememberImagePainter(artInformation.imageUrl),
        contentDescription = artInformation.alt,
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun AuthorName(artInformation: ArtBasicInformation) {
    Text(
        text = artInformation.author,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            fontStyle = FontStyle.Normal,
            color = Color.Black
        )
    )
}

@Composable
private fun ArtTitle(artInformation: ArtBasicInformation) {
    Text(
        text = artInformation.title,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            letterSpacing = 0.15.sp,
            color = Color.Black
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ArtCardPreview() {
    ArtGalleryTheme {
        Box(modifier = Modifier.padding(10.dp)) {
            ArtCard(
                artInformation = ArtBasicInformation(
                    id = 2,
                    title = "Art Title",
                    author = "Author",
                    imageId = "f6c3b597-39af-568c-f9d2-788d18e17c6e",
                    alt = "A work made of brass."
                )
            ) {
                //Launch dialog
            }
        }
    }
}