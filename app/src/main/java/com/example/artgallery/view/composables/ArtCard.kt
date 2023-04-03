package com.example.artgallery.view.composables

import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artgallery.models.dto.ArtBasicInformation
import com.example.artgallery.ui.theme.ArtGalleryTheme

@Composable
fun ArtCard(artInformation: ArtBasicInformation) {
    val context = LocalContext.current

    Surface(
        color = Color.White,
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(17.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                bitmap = artInformation.getImageFromBase64(context),
                contentDescription = artInformation.alt
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = artInformation.title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.W500,
                        letterSpacing = 0.15.sp,
                    )
                )
                Text(
                    text = artInformation.author,
                    style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        fontStyle = FontStyle.Normal
                    )
                )
            }
        }

    }
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
                    imageBase64 = "R0lGODlhAwAFAPMAAGFaWGVeXXlrY3dta39zcH5ycZZaObFrSKh0U4N1b5SBeZ6Rja2Xj6afoMG4uQAAACH5BAAAAAAALAAAAAADAAUAAAQLEIiQ0FCHMFPcahEAOw==",
                    size = Size(1808, 2250),
                    alt = "A work made of brass."
                )
            )
        }
    }
}