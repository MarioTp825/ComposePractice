package com.example.artgallery.view.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artgallery.ui.theme.ArtGalleryTheme

typealias  SearchListener = (String?) -> Unit

@Composable
fun SearchBox(
    modifier: Modifier = Modifier,
    startValue: String = "",
    search: SearchListener
) {
    var query by remember { mutableStateOf(startValue) }
    OutlinedTextField(
        value = query,
        modifier = modifier
            .border(
                border = BorderStroke(
                    1.dp, Color.Black
                ),
                shape = RoundedCornerShape(5.dp)
            ),
        onValueChange = {
            query = it
        },
        colors = textFieldColors(),
        singleLine = true,
        trailingIcon = {
            SearchButton(search, query)
        },
        leadingIcon = if (query.isEmpty()) null else cancelSearch(search)
    )
}


private fun cancelSearch(search: SearchListener): @Composable (() -> Unit) {
    return {
        IconButton(
            onClick = { search(null) }
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Cancel Search"
            )
        }
    }
}

@Composable
private fun textFieldColors() = TextFieldDefaults.textFieldColors(
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    focusedLabelColor = Color.Transparent,
    unfocusedLabelColor = Color.Transparent,
    backgroundColor = Color.White
)

@Composable
private fun SearchButton(search: SearchListener, query: String) {
    IconButton(
        onClick = {
            search(query.ifEmpty { null })
        }
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "Filter"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBoxPreview() {
    ArtGalleryTheme {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            SearchBox(
                startValue = "Search",
                search = {

            })
        }
    }
}
