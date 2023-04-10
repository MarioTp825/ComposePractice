package com.example.artgallery.view.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artgallery.ui.theme.ArtGalleryTheme
import com.example.artgallery.view.composables.ContentOrientation.*

@Composable
fun ContentSection(
    orientation: ContentOrientation,
    title: String,
    modifier: Modifier = Modifier,
    space: Dp = 5.dp,
    style: TextStyle = createTextStyle(),
    content: @Composable (Modifier) -> Unit
) {
    when (orientation) {
        VERTICAL -> VerticalContent(title, space, modifier, style, content)
        HORIZONTAL -> HorizontalContent(title, space, modifier, style, content)
    }
}

@Composable
private fun HorizontalContent(
    title: String,
    space: Dp,
    modifier: Modifier,
    style: TextStyle,
    content: @Composable (Modifier) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            style = style,
            modifier = Modifier
                .weight(1f)
        )
        Spacer(modifier = Modifier.width(space))
        content(Modifier.weight(1f))
    }
}

@Composable
private fun VerticalContent(
    title: String,
    space: Dp,
    modifier: Modifier,
    style: TextStyle,
    content: @Composable (Modifier) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            textAlign = TextAlign.Start,
            style = style,
            modifier = Modifier
                .fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(space))
        content(Modifier)
    }
}

@Composable
private fun createTextStyle() = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.W500,
    fontStyle = FontStyle.Normal,
    color = Color.Black
)

@Preview(showBackground = true)
@Composable
fun ContentSectionVerticallyPreview() {
    ArtGalleryTheme {
        ContentSection(
            orientation = VERTICAL,
            title = "Title"
        ) {
            Text(text = "Some Text Here", modifier = it.fillMaxWidth())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentSectionHorizontallyPreview() {
    ArtGalleryTheme {
        ContentSection(
            orientation = HORIZONTAL,
            title = "Title",
            modifier = Modifier.padding(horizontal = 5.dp, vertical = 3.dp)
        ) {
            Text(text = "Some Text Here", modifier = it)
        }
    }
}

enum class ContentOrientation {
    VERTICAL,
    HORIZONTAL
}