package com.example.artgallery.models.repository

import com.example.artgallery.artWork.models.dto.ArtHolder
import com.example.artgallery.artWork.models.dto.database.ArtFullInformationEntity
import com.example.artgallery.artWork.models.repository.contracts.ArtDataBaseRepository
import com.example.artgallery.artWork.models.repository.implementation.ArtDataBaseRepositoryImpl
import io.mockk.every
import io.mockk.mockk
import io.objectbox.Box
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

internal class ArtDataBaseRepositoryImplTest {
    private lateinit var box: Box<ArtFullInformationEntity>
    private lateinit var repository: ArtDataBaseRepository

    @Before
    fun setUp() {
        box = mockk()
        every { box.all } returns getAllData()
        repository = ArtDataBaseRepositoryImpl(box)
    }

    private fun getAllData(): MutableList<ArtFullInformationEntity> {
        return mutableListOf(
            ArtFullInformationEntity(
                id = 1,
                art = baseArt(id = 1)
            ),
            ArtFullInformationEntity(
                id = 2,
                art = baseArt(id = 2)
            ),
            ArtFullInformationEntity(
                id = 3,
                art = baseArt(id = 3)
            ),
            ArtFullInformationEntity(
                id = 4,
                art = baseArt(id = 4)
            ),
        )
    }

    @Test
    fun givenCorrectId_whenGettingArt_thenReturnObject() {
        assertEquals(
            repository.getFavorite(1),
            baseArt(id = 1)
        )

    }

    @Test
    fun givenIncorrectId_whenGettingArt_thenReturnNull() {
        assertEquals(
            repository.getFavorite(5),
            null
        )
    }

    @Test
    fun givenCorrectId_whenRemovingArt_thenReturnNothing() {
        assertThrows(Exception::class.java) {
            repository.removeFromFavorites(baseArt(id = 2))
        }

    }

    @Test
    fun givenIncorrectId_whenRemovingArt_thenReturnThrowException() {
        assertThrows(Exception::class.java) {
            repository.removeFromFavorites(baseArt(id = 5))
        }

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