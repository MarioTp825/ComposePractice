package com.example.artgallery.artWork.view.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.artgallery.R
import com.example.artgallery.artWork.models.dto.ArtHolder.ArtBasicInformation
import com.example.artgallery.artWork.models.dto.ArtHolder.ArtFullInformation
import com.example.artgallery.ui.theme.cardSubtitleTextStyle
import com.example.artgallery.ui.theme.cardTitleTextStyle
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
                .padding(horizontal = 10.dp, vertical = 15.dp)
                .testTag("ArtRow"),
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

    ArtImageCard(basic)
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
        modifier = Modifier.testTag("favButton"),
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

@Composable
private fun AuthorName(artInformation: ArtBasicInformation) {
    Text(
        text = artInformation.author.ifBlank { "Unknown" },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = cardSubtitleTextStyle,
        modifier = Modifier.testTag("authorArt")
    )
}

@Composable
private fun ArtTitle(artInformation: ArtBasicInformation) {
    Text(
        text = artInformation.title,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        style = cardTitleTextStyle
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
                    imageId = "",
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