package com.example.artgallery.view.composables

import androidx.activity.ComponentActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.artgallery.models.dto.ArtHolder
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoadingStatesKtTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun givenInitialLoading_whenCallingLoadingStates_showInitialLoadingView() {
        composeTestRule.setContent {
            LazyColumn {
                loadingStates(ArtHolder.ArtState.INITIAL_LOADING)
            }
        }
        composeTestRule.onNodeWithTag("initialLoading").assertExists()
    }

    @Test
    fun givenLoading_whenCallingLoadingStates_showFetchLoadingView() {
        composeTestRule.setContent {
            LazyColumn {
                loadingStates(ArtHolder.ArtState.LOADING)
            }
        }
        composeTestRule.onNodeWithTag("fetchLoading").assertExists()
    }

    @Test
    fun givenError_whenCallingLoadingStates_showErrorLoading() {
        composeTestRule.setContent {
            LazyColumn {
                loadingStates(ArtHolder.ArtState.fromError("Error Loading"))
            }
        }
        composeTestRule.onNodeWithTag("errorLoading").assertExists()
    }

    @Test
    fun givenDoneLoading_whenCallingLoadingStates_showNothing() {
        composeTestRule.setContent {
            LazyColumn {
                loadingStates(ArtHolder.ArtState.DONE)
            }
        }
        composeTestRule.onNodeWithTag("initialLoading").assertDoesNotExist()
        composeTestRule.onNodeWithTag("fetchLoading").assertDoesNotExist()
        composeTestRule.onNodeWithTag("errorLoading").assertDoesNotExist()
    }
}