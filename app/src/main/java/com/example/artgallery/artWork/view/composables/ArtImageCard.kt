package com.example.artgallery.artWork.view.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.artgallery.R
import com.example.artgallery.artWork.models.dto.ArtHolder
import com.example.artgallery.ui.theme.ArtGalleryTheme

@Composable
internal fun ArtImageCard(art: ArtHolder.ArtBasicInformation) {
    if (art.isImageIdEmpty) {
        VariantImage(art)

    } else {
        OnlineImage(art)
    }
}

@Composable
fun ErrorImage() {
    Image(
        painter = painterResource(id = R.drawable.baseline_error_outline_24),
        contentDescription = "Error",
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .testTag("errorImage"),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun VariantImage(art: ArtHolder.ArtBasicInformation) {
    art.getBitmap(LocalContext.current)?.let {
        Base64Image(it, art.alt)
    } ?: ErrorImage()

}

@Composable
private fun Base64Image(
    bitmap: ImageBitmap,
    alt: String?
) {
    Image(
        bitmap = bitmap,
        contentDescription = alt,
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .testTag("bitmapImage"),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun OnlineImage(art: ArtHolder.ArtBasicInformation) {
    Image(
        painter = rememberImagePainter(art.imageUrl),
        contentDescription = art.alt,
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .testTag("onlineImage"),
        contentScale = ContentScale.Crop
    )
}

@Preview(showBackground = true)
@Composable
fun ArtImageCardPreview() {
    ArtGalleryTheme {
        ArtImageCard(
            art = ArtHolder.ArtBasicInformation(
                id = 1,
                title = "",
                author = "",
                imageId = ""
            )
        )
    }
}