package com.example.artgallery.view.screens

import androidx.activity.ComponentActivity
import androidx.compose.material.Text
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.artgallery.viewModels.ArtWorkViewModel
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.artgallery.models.api.ChicagoAPIService
import com.example.artgallery.models.poko.ChicagoAPIResponse
import io.mockk.coEvery

import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class ArtDetailScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun givenIncorrectId_whenObjectDoesNotExist_thenShowError() {
        val viewModel = mockk<ArtWorkViewModel>()
        //every { viewModel.findArtById(any()) } returns null

        composeTestRule.setContent {
            ArtDetailScreen(viewModel = viewModel, id = 5) {
            }
        }

        composeTestRule.onNodeWithText("There was a problem finding the information for this work").assertExists()
    }
}