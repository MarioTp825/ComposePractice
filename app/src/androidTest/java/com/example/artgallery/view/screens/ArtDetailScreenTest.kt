package com.example.artgallery.view.screens

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.artgallery.viewModels.ArtWorkViewModel
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ArtDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenIncorrectId_whenObjectDoesNotExist_thenShowError() {
        val viewModel= mockk<ArtWorkViewModel>()
        every { viewModel.findArtById(any()) } returns null

        composeTestRule.setContent {
            ArtDetailScreen(viewModel = viewModel, id = 5) {
            }
        }

        composeTestRule.onNodeWithText("There was a problem").assertExists()
    }
}