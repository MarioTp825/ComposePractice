package com.example.artgallery.view.composables

import android.util.Size
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.artgallery.models.dto.ArtHolder
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtCardKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun givenArtInformation_whenImageIdIsNotEmpty_thenShowOnlineImage() {
        composeTestRule.setContent {
            ArtCard(
                likeFlow = { flow { emit(true) } },
                art = buildArtFullInformation(id = 1),
                showLikeButton = true,
                onFavoriteTap = { }
            ) {
                //
            }
        }

        composeTestRule.onNodeWithTag(testTag = "onlineImage", useUnmergedTree = true).assertExists()
    }

    @Test
    fun givenEmptyImageId_whenSizeAndBase64Exist_thenShowThumbnail() {
        composeTestRule.setContent {
            ArtCard(
                likeFlow = { flow { emit(true) } },
                art = buildArtFullInformation(id = 1).copy(
                    imageId = "",
                    base64 = "R0lGODlhBQAFAPQAAEZcaFFfdVtqbk9ldFBlcVFocllrcFlrd11rdl9sdFZtf15wcWV0d2R2eGByfmd6eGl6e2t9elZxiGF4kWB4kmJ9kGJ8lWeCkWSAnQAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAAAAAAALAAAAAAFAAUAAAUVoJBADXI4TLRMWHU9hmRRCjAURBACADs=",
                    size = Size(500,500)
                ),
                showLikeButton = true,
                onFavoriteTap = { }
            ) {
                //
            }
        }

        composeTestRule.onNodeWithTag("bitmapImage", useUnmergedTree = true).assertExists()
    }

    @Test
    fun givenEmptyImageId_whenBase64IsIncorrect_thenShowError() {
        composeTestRule.setContent {
            ArtCard(
                likeFlow = { flow { emit(true) } },
                art = buildArtFullInformation(id = 1).copy(
                    imageId = "",
                    base64 = "error",
                    size = Size(500,500)
                ),
                showLikeButton = true,
                onFavoriteTap = { }
            ) {
                //
            }
        }

        composeTestRule.onNodeWithTag("errorImage", useUnmergedTree = true).assertExists()
    }

    @Test
    fun givenEmptyImageId_whenBase64IsNullButSizeNot_thenShowErrorIcon() {
        composeTestRule.setContent {
            ArtCard(
                likeFlow = { flow { emit(true) } },
                art = buildArtFullInformation(id = 1).copy(
                    imageId = "",
                    size = Size(500,500)
                ),
                showLikeButton = true,
                onFavoriteTap = { }
            ) {
                //
            }
        }

        composeTestRule.onNodeWithTag("errorImage", useUnmergedTree = true).assertExists()
    }

    @Test
    fun givenEmptyImageId_whenSizeIsNullButBase64Not_thenShowErrorIcon() {
        composeTestRule.setContent {
            ArtCard(
                likeFlow = { flow { emit(true) } },
                art = buildArtFullInformation(id = 1).copy(
                    imageId = "",
                    base64 = "R0lGODlhBQAFAPQAAEZcaFFfdVtqbk9ldFBlcVFocllrcFlrd11rdl9sdFZtf15wcWV0d2R2eGByfmd6eGl6e2t9elZxiGF4kWB4kmJ9kGJ8lWeCkWSAnQAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAAAAAAALAAAAAAFAAUAAAUVoJBADXI4TLRMWHU9hmRRCjAURBACADs="
                ),
                showLikeButton = true,
                onFavoriteTap = { }
            ) {
                //
            }
        }

        composeTestRule.onNodeWithTag("errorImage", useUnmergedTree = true).assertExists()
    }

    @Test
    fun givenShowLikeButton_whenTrue_thenShowLikeButton() {
        composeTestRule.setContent {
            ArtCard(
                likeFlow = { flow { emit(true) } },
                art = buildArtFullInformation(id = 3),
                showLikeButton = true,
                onFavoriteTap = { }
            ) {
                //
            }
        }

        composeTestRule.onNodeWithTag("favButton", useUnmergedTree = true).assertExists()
    }

    @Test
    fun givenShowLikeButton_whenTrueButFlowNull_thenHideLikeButton() {
        composeTestRule.setContent {
            ArtCard(
                likeFlow = null,
                art = buildArtFullInformation(id = 3),
                showLikeButton = true,
                onFavoriteTap = { }
            ) {
                //
            }
        }

        composeTestRule.onNodeWithTag("favButton", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun givenShowLikeButton_whenFalse_thenHideLikeButton() {
        composeTestRule.setContent {
            ArtCard(
                likeFlow = { flow { emit(true) } },
                art = buildArtFullInformation(id = 3),
                showLikeButton = false,
                onFavoriteTap = { }
            ) {
                //
            }
        }

        composeTestRule.onNodeWithTag("favButton", useUnmergedTree = true).assertDoesNotExist()
    }

    @Test
    fun givenArtInformation_whenAuthorIsEmpty_thenShowAsUnknown() {
        composeTestRule.setContent {
            ArtCard(
                likeFlow = { flow { emit(true) } },
                art = buildArtFullInformation(id = 3).copy(
                    author = ""
                ),
                showLikeButton = false,
                onFavoriteTap = { }
            ) {
                //
            }
        }

        composeTestRule.onNodeWithTag("authorArt", useUnmergedTree = true).assertTextContains("Unknown")
    }

    @Composable
    private fun buildArtFullInformation(id: Int) = ArtHolder.ArtFullInformation(
        id = id,
        title = "Art Title",
        author = "Author",
        imageId = "f6c3b597-39af-568c-f9d2-788d18e17c6e",
        altText = "A work made of brass.",
        desc = null,
        lastUpdate = null,
        chips = mapOf(),
        additionalData = mapOf(),
        favorite = true,
    )
}