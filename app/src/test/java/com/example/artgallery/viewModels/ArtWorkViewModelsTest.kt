package com.example.artgallery.viewModels

import com.example.artgallery.models.dto.ArtWrapper
import org.junit.Assert.assertEquals
import org.junit.Test

class ArtWorkViewModelsTest {

    @Test
    fun givenCacheEmpty_WhenSearchingAnId_ThenResultShouldBeNull() {
        val viewModel = ArtWorkViewModel()
        val result = viewModel.findArtById(4)
        assertEquals("No Art Found", result, null)
    }

    @Test
    fun givenCacheWithData_whenSearchingAValidId_ThenReturnValue() {
        val viewModel = ArtWorkViewModel()
        val realValue = ArtWrapper.ArtFullInformation(
            3,
            "title",
            "author",
            "id",
            null,
            null,
            mapOf()
        )
        viewModel.cache.addAll(
            listOf(
                realValue,
                ArtWrapper.ArtFullInformation(
                    5,
                    "title",
                    "author",
                    "id",
                    null,
                    null,
                    mapOf()
                ),
            )
        )
        val result = viewModel.findArtById(3)
        assertEquals("Art Found", result, realValue)
    }

    @Test
    fun givenCachedWithData_whenSearchingIncorrectValue_ThenRenurnNull() {
        val viewModel = ArtWorkViewModel()
        viewModel.cache.addAll(
            listOf(
                ArtWrapper.ArtFullInformation(
                    3,
                    "title",
                    "author",
                    "id",
                    null,
                    null,
                    mapOf()
                ),
                ArtWrapper.ArtFullInformation(
                    5,
                    "title",
                    "author",
                    "id",
                    null,
                    null,
                    mapOf()
                ),
            )
        )
        val result = viewModel.findArtById(10)
        assertEquals("Art Found", result, null)
    }

    fun givenIdNull_whenSearchingData_thenReturnNull() {
        val viewModel = ArtWorkViewModel()
        viewModel.cache.addAll(
            listOf(
                ArtWrapper.ArtFullInformation(
                    3,
                    "title",
                    "author",
                    "id",
                    null,
                    null,
                    mapOf()
                ),
                ArtWrapper.ArtFullInformation(
                    5,
                    "title",
                    "author",
                    "id",
                    null,
                    null,
                    mapOf()
                ),
            )
        )
        val result = viewModel.findArtById(null)
        assertEquals("Art Found", result, null)
    }
}