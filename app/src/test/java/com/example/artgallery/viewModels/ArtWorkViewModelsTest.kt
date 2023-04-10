package com.example.artgallery.viewModels

import com.example.artgallery.models.db.ArtObjectBox
import com.example.artgallery.models.dto.ArtHolder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ArtWorkViewModelsTest {

    @BeforeEach
    fun setUp() {
        //ArtObjectBox.init()
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
        val realValue = ArtHolder.ArtBasicInformation(
            3,
            "title",
            "author",
            "id",
            null,
        )
        viewModel.basicListWrapper.artData.addAll(
            listOf(
                realValue,
                ArtHolder.ArtBasicInformation(
                    5,
                    "title",
                    "author",
                    "id",
                    null,
                ),
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
                ArtHolder.ArtBasicInformation(
                    3,
                    "title",
                    "author",
                    "id",
                    null,
                ),
                ArtHolder.ArtBasicInformation(
                    5,
                    "title",
                    "author",
                    "id",
                    null,
                ),
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
                ArtHolder.ArtBasicInformation(
                    3,
                    "title",
                    "author",
                    "id",
                    null,
                ),
                ArtHolder.ArtBasicInformation(
                    5,
                    "title",
                    "author",
                    "id",
                    null,
                ),
            )
        )
        val result = viewModel.findArtById(null)
        assertEquals(result, null)
    }
}