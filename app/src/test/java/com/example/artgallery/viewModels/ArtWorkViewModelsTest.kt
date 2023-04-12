package com.example.artgallery.viewModels

import com.example.artgallery.artWork.viewModels.ArtWorkViewModel
import com.example.artgallery.artWork.models.dto.ArtHolder
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArtWorkViewModelsTest {

    @Before
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
        val realValue = baseArt(id = 3)
        viewModel.basicListWrapper.artData.addAll(
            listOf(
                realValue,
                baseArt(id = 5)
            )
        )
        val result = viewModel.findArtById(3)
        assertEquals(result, realValue)
    }

    @Test
    fun givenCachedWithData_whenSearchingIncorrectValue_ThenReturnNull() {
        val viewModel = ArtWorkViewModel()
        viewModel.basicListWrapper.artData.addAll(
            listOf(
                baseArt(id = 3),
                baseArt(id = 5)
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
                baseArt(id = 3),
                baseArt(id = 5)
            )
        )
        val result = viewModel.findArtById(null)
        assertEquals(result, null)
    }

    private fun baseArt(id: Int) = ArtHolder.ArtFullInformation(
        id = id,
        title = "Title",
        author = "Author",
        imageId = "",
        altText = null,
        desc = "Saepe quasi voluptate non similique enim assumenda magnam . Quia repellat officia earum quam . Quidem sit ex perspiciatis atque doloribus ex . Assumenda placeat reprehenderit dolores voluptates adipisci quaerat . Omnis sed provident .",
        additionalData = mapOf(),
        chips = mapOf(),
        lastUpdate = null
    )
}