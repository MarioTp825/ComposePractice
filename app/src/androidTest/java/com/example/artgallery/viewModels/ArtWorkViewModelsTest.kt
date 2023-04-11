package com.example.artgallery.viewModels

import androidx.test.core.app.ApplicationProvider
import com.example.artgallery.models.db.ArtObjectBox
import com.example.artgallery.models.dto.ArtHolder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArtWorkViewModelsTest {

    @BeforeEach
    fun setUp() {
        ArtObjectBox.init(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun givenCacheEmpty_WhenSearchingAnId_ThenResultShouldBeNull() {
        val viewModel = ArtWorkViewModel()
        val result = viewModel.findArtById(4)
        assertEquals(result, null)
    }

    @Test
    fun givenCacheWithData_whenSearchingAValidId_ThenReturnValue() {
        val viewModel = ArtWorkViewModel()
        val realValue = getBaseInformation(id = 3)
        viewModel.basicListWrapper.artData.addAll(
            listOf(
                realValue,
                getBaseInformation(id = 5),
            )
        )
        val result = viewModel.findArtById(3)
        assertEquals(result, realValue)
    }

    @Test
    fun givenCachedWithData_whenSearchingIncorrectValue_ThenRenurnNull() {
        val viewModel = ArtWorkViewModel()
        viewModel.basicListWrapper.artData.addAll(
            listOf(
                getBaseInformation(id = 3),
                getBaseInformation(id = 5)
            )
        )
        val result = viewModel.findArtById(10)
        assertEquals(result, null)
    }

    @Test
    fun givenIdNull_whenSearchingData_thenReturnNull() {
        val viewModel = ArtWorkViewModel()
        viewModel.basicListWrapper.artData.addAll(
            listOf(
                getBaseInformation(id = 3),
                getBaseInformation(id = 5),
            )
        )
        val result = viewModel.findArtById(null)
        assertEquals(result, null)
    }

    private fun getBaseInformation(id: Int) = ArtHolder.ArtFullInformation(
        id = id,
        title = "title",
        author = "author",
        imageId = "id",
        altText = null,
        desc = null,
        lastUpdate = null,
        chips = mapOf(),
        additionalData = mapOf()
    )
}