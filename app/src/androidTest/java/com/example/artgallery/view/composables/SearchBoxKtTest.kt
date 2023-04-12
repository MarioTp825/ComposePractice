package com.example.artgallery.view.composables

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.artgallery.generics.view.composables.SearchBox
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchBoxKtTest{

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun givenSearchNotEmpty_WhenBuildingView_ThenShowCancelIcon() {
        composeTestRule.setContent {
            SearchBox(search = {}, startValue = "Not Empty")
        }

        composeTestRule.onNodeWithTag("cancelIcon").assertExists()
    }

    @Test
    fun givenEmptySearch_WhenBuildingView_ThenHideCancelIcon() {
        composeTestRule.setContent {
            SearchBox(search = {}, startValue = "")
        }

        composeTestRule.onNodeWithTag("cancelIcon").assertDoesNotExist()
    }

    @Test
    fun givenSearch_WhenSearching_thenCallback() {
        var data: String? = null
        composeTestRule.setContent {
            SearchBox(search = {
                data = it
            }, startValue = "data")
        }
        composeTestRule.onNodeWithTag("searchIcon").performClick()

        assertNotEquals(data, null)
    }

    @Test
    fun givenEmptySearch_WhenSearching_thenCallbackNull() {
        var data: String? = "data"
        composeTestRule.setContent {
            SearchBox(search = {
                data = it
            },)
        }
        composeTestRule.onNodeWithTag("searchIcon").performClick()

        assertEquals(data, null)
    }
}