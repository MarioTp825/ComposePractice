package com.example.artgallery.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.artgallery.R
import com.example.artgallery.models.dto.ArtHolder.ArtBasicInformation
import com.example.artgallery.models.dto.ArtHolder.ArtFullInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias OnClick = (Int) -> Unit
typealias OnFavoriteTap = (Boolean) -> Unit
typealias LikeFlow = (Int) -> Flow<Boolean>

@Composable
fun ArtCard(
    likeFlow: LikeFlow?,
    art: ArtFullInformation,
    showLikeButton: Boolean = true,
    onFavoriteTap: OnFavoriteTap,
    onClick: OnClick
) {
    Surface(
        color = Color.White,
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black, shape = RoundedCornerShape(17.dp))
            .clickable { onClick(art.id) },
        shape = RoundedCornerShape(
            dimensionResource(id = R.dimen.card_corner_radius)
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BuildCardContent(likeFlow, art, showLikeButton, onFavoriteTap)
        }

    }
}

@Composable
private fun RowScope.BuildCardContent(
    likeFlow: LikeFlow?,
    fullInformation: ArtFullInformation,
    showLikeButton: Boolean,
    onFavoriteTap: OnFavoriteTap
) {
    val basic = fullInformation.basic

    ArtImage(basic)
    Spacer(modifier = Modifier.width(16.dp))
    Column(
        modifier = Modifier
            .weight(1f)
    ) {
        ArtTitle(basic)
        AuthorName(basic)
    }
    if (showLikeButton && likeFlow != null)
        LikeButton(likeFlow, onFavoriteTap, art = basic)
}

@Composable
fun LikeButton(
    likeFlow: LikeFlow,
    onFavoriteTap: OnFavoriteTap,
    art: ArtBasicInformation
) {

    val isChecked by likeFlow(art.id).collectAsState(initial = false)
    var temp by remember { mutableStateOf(isChecked) }
    IconButton(
        onClick = {
            onFavoriteTap(isChecked)
            temp = !temp
        },
    ) {
        Icon(
            imageVector = if (temp || isChecked) Icons.Filled.Favorite
            else Icons.Filled.FavoriteBorder,
            contentDescription = "Add to favorites",
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
private fun ArtImage(artInformation: ArtBasicInformation) {
    Image(
        painter = ,
        contentDescription = artInformation.alt,
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun getPainterFromBasicInformation(art: ArtBasicInformation): Painter {
    if(art.isImageIdEmpty) {
        //art.getBitmap(LocalContext.current)?.pa
    } else {
        rememberImagePainter(art.imageUrl)
    }
}

@Composable
private fun AuthorName(artInformation: ArtBasicInformation) {
    Text(
        text = artInformation.author.ifBlank { "Unknown" },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
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
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
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
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            ArtCard(
                likeFlow = { flow { emit(true) } },
                art = ArtFullInformation(
                    id = 2,
                    title = "Art Title",
                    author = "Author",
                    imageId = "f6c3b597-39af-568c-f9d2-788d18e17c6e",
                    altText = "A work made of brass.",
                    desc = null,
                    lastUpdate = null,
                    chips = mapOf(),
                    additionalData = mapOf(),
                    favorite = true,
                ),
                showLikeButton = true,
                onFavoriteTap = { }
            ) { }
        }
    }
}